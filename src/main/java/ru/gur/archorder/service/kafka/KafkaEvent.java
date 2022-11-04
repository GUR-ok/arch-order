package ru.gur.archorder.service.kafka;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderApproveEventData.class, name = "ORDER_APPROVE"),
        @JsonSubTypes.Type(value = OrderCancelEventData.class, name = "ORDER_CANCEL")
})
public interface KafkaEvent extends EventSource{
}