package ru.gur.archorder.service;

import ru.gur.archorder.service.data.GetOrderData;
import ru.gur.archorder.service.immutable.ImmutableCreateOrderRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    UUID create(ImmutableCreateOrderRequest immutableCreateOrderRequest, String key);

    GetOrderData read(UUID id, UUID userProfileId);

    List<GetOrderData> getOrders(UUID userProfileId);
}