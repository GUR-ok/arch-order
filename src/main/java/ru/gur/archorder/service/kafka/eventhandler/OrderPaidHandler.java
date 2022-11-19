package ru.gur.archorder.service.kafka.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.gur.archorder.service.kafka.Event;
import ru.gur.archorder.service.kafka.EventSource;
import ru.gur.archorder.service.kafka.OrderPaidEventData;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaidHandler implements EventHandler<OrderPaidEventData> {

    @Override
    public boolean canHandle(final EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.ORDER_PAID.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(final OrderPaidEventData eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

//        service.call
        System.out.printf("!!! ORDER %s PAID SUCCESSFULLY\n", eventSource.getOrderId());

        log.info("Event handled: {}", eventSource);

        return eventSource.getEvent().name();
    }
}
