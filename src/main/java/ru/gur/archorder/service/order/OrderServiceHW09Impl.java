package ru.gur.archorder.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.gur.archorder.entity.Order;
import ru.gur.archorder.entity.State;
import ru.gur.archorder.exception.NotAuthorizedException;
import ru.gur.archorder.exception.OrderNotFoundException;
import ru.gur.archorder.persistance.OrderRepository;
import ru.gur.archorder.service.kafka.OrderCreatedEventData;
import ru.gur.archorder.service.kafka.Producer;
import ru.gur.archorder.service.order.data.GetOrderData;
import ru.gur.archorder.service.order.immutable.ImmutableCreateOrderRequest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("hw09")
public class OrderServiceHW09Impl implements OrderService {

    private static final String TOPIC = "billing";

    private final OrderRepository orderRepository;
    private final Producer producer;

    @Override
    @Transactional
    public UUID create(final ImmutableCreateOrderRequest request, final String key) {
        Assert.notNull(request, "request must not be null");

        return createOrder(request);
    }

    @Override
    public GetOrderData read(final UUID id, final UUID userProfileId) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(userProfileId, "userProfileId must not be null");

        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found!"));

        if (!order.getProfileId().equals(userProfileId)) {
            throw new NotAuthorizedException("Недостаточно прав!");
        }

        log.info("Order read with id: {}", id);
        return GetOrderData.builder()
                .id(order.getId())
                .profileId(order.getProfileId())
                .orderNumber(order.getOrderNumber())
                .productQuantity(order.getProductQuantity())
                .state(order.getState())
                .build();
    }

    @Override
    public List<GetOrderData> getOrders(final UUID userProfileId) {
        Assert.notNull(userProfileId, "userProfileId must not be null");

        return orderRepository.findAllByProfileId(userProfileId).stream()
                .map(order -> GetOrderData.builder()
                        .id(order.getId())
                        .orderNumber(order.getOrderNumber())
                        .productQuantity(order.getProductQuantity())
                        .profileId(order.getProfileId())
                        .state(order.getState())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UUID delete(final UUID id, final UUID userProfileId) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(userProfileId, "userProfileId must not be null");

        final Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!Objects.equals(optionalOrder.map(Order::getProfileId).orElse(null), userProfileId)) {
            throw new NotAuthorizedException("Недостаточно прав!");
        }

        orderRepository.deleteById(id);
        log.info("Order delete with id: {}", id);
        return id;
    }

    @Override
    @Transactional
    public void updateStatus(final UUID id, final State state) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(state, "state must not be null");

        final Optional<Order> order = orderRepository.findById(id);

        order.ifPresent(o -> {
            o.setState(state);
            orderRepository.save(o);
        });
    }

    private UUID createOrder(final ImmutableCreateOrderRequest request) {
        final Order order = new Order();
        order.setProductQuantity(request.getProductQuantity());
        order.setProfileId(request.getProfileId());

        orderRepository.save(order);

        final UUID orderId = order.getId();
        producer.sendEvent(TOPIC, request.getProfileId().toString(), OrderCreatedEventData.builder()
                .orderId(orderId)
                .accountId(request.getProfileId())
                .price((double) (request.getProductQuantity() * 65))
                .build());

        log.info("Order created with id: {}", orderId);
        return order.getId();
    }
}
