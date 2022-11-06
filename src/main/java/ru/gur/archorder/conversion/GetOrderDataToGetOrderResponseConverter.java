package ru.gur.archorder.conversion;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.gur.archorder.service.order.data.GetOrderData;
import ru.gur.archorder.web.order.response.GetOrderResponse;

@Component
public class GetOrderDataToGetOrderResponseConverter implements Converter<GetOrderData, GetOrderResponse> {

    @Override
    public GetOrderResponse convert(final GetOrderData source) {
        return GetOrderResponse.builder()
                .id(source.getId())
                .orderNumber(source.getOrderNumber())
                .profileId(source.getProfileId())
                .productQuantity(source.getProductQuantity())
                .state(source.getState())
                .build();
    }
}
