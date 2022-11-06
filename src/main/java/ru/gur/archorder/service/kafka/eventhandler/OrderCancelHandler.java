package ru.gur.archorder.service.kafka.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.gur.archorder.entity.State;
import ru.gur.archorder.service.kafka.Event;
import ru.gur.archorder.service.kafka.EventSource;
import ru.gur.archorder.service.kafka.OrderCancelEventData;
import ru.gur.archorder.service.order.OrderService;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCancelHandler implements EventHandler<OrderCancelEventData> {

    private final OrderService orderService;

    @Override
    public boolean canHandle(final EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.ORDER_CANCEL.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(final OrderCancelEventData eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        orderService.updateStatus(eventSource.getOrderId(), State.CANCELED);

        log.info("Event handled: {}", eventSource);

        return eventSource.getEvent().name();
    }
}
