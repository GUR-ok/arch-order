package ru.gur.archorder.web.order.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateOrderRequest {

    @NotNull
    private Long productQuantity;
}
