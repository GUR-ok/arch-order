package ru.gur.archorder.service.kafka;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class OrderApproveEventData implements KafkaEvent {

    UUID orderId;

    @Override
    public Event getEvent() {
        return Event.ORDER_APPROVE;
    }
}
