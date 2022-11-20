package ru.gur.archorder.service.kafka;

public enum Event {

    ORDER_CREATED,
    ORDER_APPROVE,
    ORDER_CANCEL,
    PAYMENT_FAIL,
    ORDER_PAID,
    DEPOSIT_ACCEPTED
}
