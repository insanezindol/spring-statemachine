package com.example.springstatemachine.service;

import com.example.springstatemachine.domain.Orders;
import com.example.springstatemachine.domain.OrdersEvent;
import com.example.springstatemachine.domain.OrdersState;
import com.example.springstatemachine.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService {

    public static final String _ORDER_ID = "ORDER_ID";
    private final OrdersRepository ordersRepository;

    private final StateMachineFactory<OrdersState, OrdersEvent> stateMachineFactory;

    private final OrdersStateChangeInterceptor ordersStateChangeInterceptor;

    public List<Orders> getOrders() {
        return ordersRepository.findAll();
    }

    @Transactional
    public Orders newOrders(Orders orders) {
        log.info("newOrders : {}", orders);
        orders.setState(OrdersState.NEW_ORDER);
        return ordersRepository.save(orders);
    }

    @Transactional
    public void orderAccept(Long orderId) {
        log.info("orderAccept : {}", orderId);
        StateMachine<OrdersState, OrdersEvent> sm = build(orderId);
        sendEvent(orderId, sm, OrdersEvent.OrderAcceptEvent);
        log.info("output : {}", sm);
    }

    @Transactional
    public void orderDenied(Long orderId) {
        log.info("orderDenied : {}", orderId);
        StateMachine<OrdersState, OrdersEvent> sm = build(orderId);
        sendEvent(orderId, sm, OrdersEvent.OrderDeniedEvent);
        log.info("output : {}", sm);
    }

    @Transactional
    public void deliveryStart(Long orderId) {
        log.info("deliveryStart : {}", orderId);
        StateMachine<OrdersState, OrdersEvent> sm = build(orderId);
        sendEvent(orderId, sm, OrdersEvent.DeliveryStartEvent);
        log.info("output : {}", sm);
    }

    @Transactional
    public void deliveryEnd(Long orderId) {
        log.info("deliveryEnd : {}", orderId);
        StateMachine<OrdersState, OrdersEvent> sm = build(orderId);
        sendEvent(orderId, sm, OrdersEvent.DeliveryEndEvent);
        log.info("output : {}", sm);
    }

    @Transactional
    public void complete(Long orderId) {
        log.info("complete : {}", orderId);
        StateMachine<OrdersState, OrdersEvent> sm = build(orderId);
        sendEvent(orderId, sm, OrdersEvent.CompleteEvent);
        log.info("output : {}", sm);
    }

    private StateMachine<OrdersState, OrdersEvent> build(Long orderId) {
        log.info("build : {}", orderId);
        Orders orders = ordersRepository.getOne(orderId);
        StateMachine<OrdersState, OrdersEvent> sm = stateMachineFactory.getStateMachine(Long.toString(orders.getId()));
        sm.stop();
        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(ordersStateChangeInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(orders.getState(), null, null, null));
                });
        sm.start();
        return sm;
    }

    private void sendEvent(Long orderId, StateMachine<OrdersState, OrdersEvent> sm, OrdersEvent event) {
        log.info("sendEvent : {} , {} , {}", orderId, sm, event);
        Message msg = MessageBuilder.withPayload(event)
                .setHeader(_ORDER_ID, orderId)
                .build();
        sm.sendEvent(msg);
    }

}
