package ru.gur.archorder.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
@Slf4j
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplateString;

    public void sendString(String key, String data) {
        log.info("Start send");
        kafkaTemplateString.send("Orders", key, data)
                .addCallback(
                        result -> log.info("send complete {}", "Orders"),
                        fail -> log.error("fail send", fail.getCause()));
        log.info("complete send");
    }

    public void sendEvent(final String topic, final String key, final KafkaEvent event) {
        Assert.hasText(topic, "topic must not be blank");
        Assert.hasText(key, "key must not be blank");
        Assert.notNull(event, "KafkaEvent must not be null");

        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String data = objectMapper.writeValueAsString(event);

            kafkaTemplateString.send(topic, key, data)
                    .addCallback(
                            result -> log.info("Kafka send complete"),
                            fail -> log.error("Kafka fail send"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
