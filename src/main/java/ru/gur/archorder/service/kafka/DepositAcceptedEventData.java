package ru.gur.archorder.service.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepositAcceptedEventData implements KafkaEvent {

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Prevents duplication when serializing to JSON (subtype discriminator property)
    public Event getEvent() {
        return Event.DEPOSIT_ACCEPTED;
    }
}
