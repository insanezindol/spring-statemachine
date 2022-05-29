package com.example.springstatemachine.domain;

public enum OrdersState {
    NEW_ORDER,
    ORDER_APPROVED,
    ORDER_CANCEL,
    DELIVERY_START,
    DELIVERY_END,
    COMPLETE
}
