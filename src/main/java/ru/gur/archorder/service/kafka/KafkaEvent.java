package ru.gur.archorder.service.kafka;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderApproveEventData.class, name = "ORDER_APPROVE"),
        @JsonSubTypes.Type(value = OrderCancelEventData.class, name = "ORDER_CANCEL"),
        @JsonSubTypes.Type(value = OrderCreatedEventData.class, name = "ORDER_CREATED"),
        @JsonSubTypes.Type(value = PaymentFailEventData.class, name = "PAYMENT_FAIL"),
        @JsonSubTypes.Type(value = OrderPaidEventData.class, name = "ORDER_PAID"),
        @JsonSubTypes.Type(value = DepositAcceptedEventData.class, name = "DEPOSIT_ACCEPTED")
})
public interface KafkaEvent extends EventSource {
}