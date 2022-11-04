package ru.gur.archorder.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Consumer {

    @KafkaListener(topics = "topic1", containerFactory = "kafkaListenerContainerFactoryString")
    public void listenGroupTopic1(String message) {
        log.info("Receive message {}", message);
    }
}
