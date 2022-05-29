package com.example.springstatemachine.domain;

public enum OrdersEvent {

    OrderAcceptEvent,
    OrderDeniedEvent,
    DeliveryStartEvent,
    DeliveryEndEvent,
    CompleteEvent
}
