package ru.gur.archorder.service.kafka.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.gur.archorder.service.kafka.Event;
import ru.gur.archorder.service.kafka.EventSource;
import ru.gur.archorder.service.kafka.PaymentFailEventData;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFailHandler implements EventHandler<PaymentFailEventData> {

    @Override
    public boolean canHandle(final EventSource eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

        return Event.PAYMENT_FAIL.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(final PaymentFailEventData eventSource) {
        Assert.notNull(eventSource, "EventSource must not be null");

//        service.call
        System.out.printf("!!! NOT ENOUGH MONEY on account %s for order %s\n",
                eventSource.getAccountId(), eventSource.getOrderId());

        log.info("Event handled: {}", eventSource);

        return eventSource.getEvent().name();
    }
}
