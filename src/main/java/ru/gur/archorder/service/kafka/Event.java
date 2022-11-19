package ru.gur.archorder.service.kafka;

public enum Event {

    ORDER_CREATED_EVENT,
    ORDER_APPROVE,
    ORDER_CANCEL,
    PAYMENT_FAIL,
    ORDER_PAID,
    DEPOSIT_ACCEPTED
}
