package ru.gur.archorder.web.order.response;

import lombok.Builder;
import lombok.Data;
import ru.gur.archorder.service.order.data.GetOrderData;

import java.util.List;

@Data
@Builder
public class GetAllOrderResponse {

    List<GetOrderData> orders;
}
