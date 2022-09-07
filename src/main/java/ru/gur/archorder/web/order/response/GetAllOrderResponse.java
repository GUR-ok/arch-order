package ru.gur.archorder.web.order.response;

import lombok.Builder;
import lombok.Data;
import ru.gur.archorder.service.data.GetOrderData;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GetAllOrderResponse {

    List<GetOrderData> orders;
}
