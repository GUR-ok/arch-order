package ru.gur.archorder.service.order.immutable;

import lombok.Builder;
import lombok.Value;
import org.springframework.util.Assert;

import java.util.UUID;

@Value
@Builder
public class ImmutableCreateOrderRequest {

    UUID profileId;

    long productQuantity;

    public static ImmutableCreateOrderRequestBuilder builder() {
        return new ImmutableCreateOrderRequestBuilder() {
            @Override
            public ImmutableCreateOrderRequest build() {
                Assert.notNull(super.profileId, "profileId must not be null");
                Assert.state(super.productQuantity > 0, "productQuantity must be positive");

                return new ImmutableCreateOrderRequest(super.profileId,
                        super.productQuantity);
            }
        };
    }
}
