package ru.gur.archorder.web.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gur.archorder.service.kafka.OrderCreatedEventData;
import ru.gur.archorder.service.kafka.Producer;
import ru.gur.archorder.service.order.OrderService;
import ru.gur.archorder.service.order.data.GetOrderData;
import ru.gur.archorder.service.order.immutable.ImmutableCreateOrderRequest;
import ru.gur.archorder.web.order.request.CreateOrderRequest;
import ru.gur.archorder.web.order.response.GetOrderResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Primary
@Slf4j
@RestController("OrderControllerLocalImpl")
@RequiredArgsConstructor
@Profile("hw09")
@RequestMapping("/orders")
public class OrderControllerHW9Impl implements OrderController {

    private final OrderService orderService;
    private final ConversionService conversionService;

    @Override
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UUID create(@Valid @NotNull @RequestBody final CreateOrderRequest createOrderRequest,
                       @RequestParam(name = "profileId") final String token,
                       @RequestHeader(name = "idempotency-key") String key) {
        log.info("Idempotency key: {}", key);

        final UUID orderId = orderService.create(ImmutableCreateOrderRequest.builder()
                        .productQuantity(createOrderRequest.getProductQuantity())
                        .profileId(UUID.fromString(token))
                        .build(),
                key);

        return orderId;
    }

    @Override
    @GetMapping(path = "/{id}")
    public GetOrderResponse read(@PathVariable(name = "id") final UUID id,
                                 @RequestParam(name = "profileId") final String token) {
        return conversionService.convert(orderService.read(id, UUID.fromString(token)), GetOrderResponse.class);
    }

    @Override
    @GetMapping(path = "/")
    public List<GetOrderData> getOrders(@RequestParam(name = "profileId") final String token) {
        return orderService.getOrders(UUID.fromString(token));
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public UUID deleteOrder(@PathVariable(name = "id") final UUID id,
                            @RequestParam(name = "profileId") final String token) {
        return orderService.delete(id, (UUID.fromString(token)));
    }
}
