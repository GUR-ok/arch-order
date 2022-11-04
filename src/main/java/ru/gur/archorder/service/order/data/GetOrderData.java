package ru.gur.archorder.service.order.data;

import lombok.Builder;
import lombok.Value;
import org.springframework.util.Assert;

import java.util.UUID;

@Value
@Builder
public class GetOrderData {

    UUID id;

    UUID profileId;

    Long orderNumber;

    Long productQuantity;

    public static GetOrderData.GetOrderDataBuilder builder() {
        return new GetOrderData.GetOrderDataBuilder() {
            @Override
            public GetOrderData build() {
                Assert.notNull(super.id, "id must not be null");
                Assert.notNull(super.profileId, "profileId must not be null");
                Assert.notNull(super.productQuantity, "productQuantity must not be null");
                Assert.notNull(super.orderNumber, "orderNumber must not be null");
                Assert.state(super.productQuantity > 0, "productQuantity must be positive");

                return new GetOrderData(super.id,
                        super.profileId,
                        super.orderNumber,
                        super.productQuantity);
            }
        };
    }
}
