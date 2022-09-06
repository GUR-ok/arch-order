package ru.gur.archorder.web.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.gur.archorder.service.OrderService;
import ru.gur.archorder.service.data.GetOrderData;
import ru.gur.archorder.service.immutable.ImmutableCreateOrderRequest;
import ru.gur.archorder.web.order.request.CreateOrderRequest;
import ru.gur.archorder.web.order.response.GetOrderResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static ru.gur.archorder.utils.TokenUtils.getProfileIdFromPayload;

@Slf4j
@RestController("OrderControllerImpl")
@RequiredArgsConstructor
@Profile("prod")
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;
    private final ConversionService conversionService;

    @Override
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UUID create(@Valid @NotNull @RequestBody final CreateOrderRequest createOrderRequest,
                       @RequestHeader(name = "x-jwt-token") final String token,
                       @RequestHeader(name = "idempotency-key") String key) {
        log.info("Idempotency key: {}", key);

        return orderService.create(ImmutableCreateOrderRequest.builder()
                        .productQuantity(createOrderRequest.getProductQuantity())
                        .profileId(getProfileIdFromPayload(token))
                        .build(),
                key);
    }

    @Override
    @GetMapping(path = "/{id}")
    public GetOrderResponse read(@PathVariable(name = "id") final UUID id,
                                 @RequestHeader(name = "x-jwt-token") final String token) {
        final UUID userProfileId = getProfileIdFromPayload(token);
        log.info("Extracted profileId: " + userProfileId);

        return conversionService.convert(orderService.read(id, userProfileId), GetOrderResponse.class);
    }

    @Override
    @GetMapping(path = "/")
    public List<GetOrderData> getOrders(@RequestHeader(name = "x-jwt-token") final String token) {
        final UUID userProfileId = getProfileIdFromPayload(token);
        log.info("Extracted profileId: " + userProfileId);

        return orderService.getOrders(userProfileId);
    }
}
