package ru.gur.archorder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gur.archorder.entity.IdempKey;
import ru.gur.archorder.entity.Order;
import ru.gur.archorder.exception.NotAuthorizedException;
import ru.gur.archorder.exception.OrderNotFoundException;
import ru.gur.archorder.persistance.OrderRepository;
import ru.gur.archorder.persistance.RedisRepository;
import ru.gur.archorder.service.data.GetOrderData;
import ru.gur.archorder.service.immutable.ImmutableCreateOrderRequest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RedisRepository redisRepository;

    @Override
    @Transactional
    public UUID create(final ImmutableCreateOrderRequest request, final String key) {
        return redisRepository.findById(key)
                .map(IdempKey::getOrderId)
                .orElseGet(() -> {
                            final UUID orderUid = createOrder(request);
                            redisRepository.save(new IdempKey(key, orderUid, 36000L));
                            return orderUid;
                        }
                );
    }

    @Override
    public GetOrderData read(final UUID id, final UUID userProfileId) {
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
                .build();
    }

    @Override
    public List<GetOrderData> getOrders(final UUID userProfileId) {
        return orderRepository.findAllByProfileId(userProfileId).stream()
                .map(order -> GetOrderData.builder()
                        .id(order.getId())
                        .orderNumber(order.getOrderNumber())
                        .productQuantity(order.getProductQuantity())
                        .profileId(order.getProfileId())
                        .build())
                .collect(Collectors.toList());
    }

    private UUID createOrder(final ImmutableCreateOrderRequest request) {
        final Order order = new Order();
        order.setProductQuantity(request.getProductQuantity());
        order.setProfileId(request.getProfileId());

        orderRepository.save(order);

        log.info("Order created with id: {}", order.getId());
        return order.getId();
    }
}
