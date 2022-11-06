package ru.gur.archorder.service.kafka.eventhandler;

import ru.gur.archorder.service.kafka.EventSource;

public interface EventHandler<T extends EventSource> {

    boolean canHandle(EventSource eventSource);

    String handleEvent(T eventSource);
}
