package ru.gur.archorder.web.order.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GetOrderResponse {

    private UUID id;

    private UUID profileId;

    private long orderNumber;

    private long productQuantity;
}
