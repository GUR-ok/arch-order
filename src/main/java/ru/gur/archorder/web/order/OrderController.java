package ru.gur.archorder.web.order;

import ru.gur.archorder.service.data.GetOrderData;
import ru.gur.archorder.web.order.request.CreateOrderRequest;
import ru.gur.archorder.web.order.response.GetOrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderController {

    UUID create(CreateOrderRequest createOrderRequest, String token, String key);

    GetOrderResponse read(UUID id, String token);

    List<GetOrderData> getOrders(String token);
}
