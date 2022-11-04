package ru.gur.archorder.web.order;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gur.archorder.service.data.GetOrderData;
import ru.gur.archorder.web.order.request.CreateOrderRequest;
import ru.gur.archorder.web.order.response.GetOrderResponse;

import java.util.List;
import java.util.UUID;

import static ru.gur.archorder.web.order.OrderController.ROOT_PATH;

@Validated
@RequestMapping(ROOT_PATH)
public interface OrderController {

    String ROOT_PATH = "/orders";

    @PostMapping
    UUID create(CreateOrderRequest createOrderRequest, String token, String key);

    @GetMapping(path = "/{id}")
    GetOrderResponse read(UUID id, String token);

    @GetMapping(path = "/")
    List<GetOrderData> getOrders(String token);

    @DeleteMapping(path = "/{id}")
    UUID deleteOrder(UUID id, String token);
}
