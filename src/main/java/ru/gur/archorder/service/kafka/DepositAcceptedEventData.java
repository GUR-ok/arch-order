package ru.gur.archorder.service.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepositAcceptedEventData implements KafkaEvent {

    @Override
    public Event getEvent() {
        return Event.DEPOSIT_ACCEPTED;
    }
}
