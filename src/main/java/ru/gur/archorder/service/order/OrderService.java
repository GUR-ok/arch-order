package ru.gur.archorder.service.order;

import ru.gur.archorder.service.order.data.GetOrderData;
import ru.gur.archorder.service.order.immutable.ImmutableCreateOrderRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    UUID create(ImmutableCreateOrderRequest immutableCreateOrderRequest, String key);

    GetOrderData read(UUID id, UUID userProfileId);

    List<GetOrderData> getOrders(UUID userProfileId);

    UUID delete(UUID id, UUID userProfileId);
}