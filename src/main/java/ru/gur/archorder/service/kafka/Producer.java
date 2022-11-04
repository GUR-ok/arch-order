package ru.gur.archorder.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Producer {
    private final KafkaTemplate<String, String> kafkaTemplateString;

    public void sendString(String topic, int keyFrom, int keyTo, String data, int count) {
        log.info("Start send");

        for (int i = keyFrom; i < keyTo; ++i) {
            var no = i;
            for (int j = 0; j < count; ++j) {
                var no2 = j;
                kafkaTemplateString.send(topic, Integer.toString(i), "[" + data + "] " + i + "-" + j)
                        .addCallback(
                                result -> log.info("send complete {}-{}", no, no2),
                                fail -> log.error("fail send", fail.getCause()));
            }
        }

        log.info("complete send");
    }
}
