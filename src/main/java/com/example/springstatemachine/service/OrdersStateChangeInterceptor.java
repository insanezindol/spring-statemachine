package com.example.springstatemachine.service;

import com.example.springstatemachine.domain.OrdersEvent;
import com.example.springstatemachine.domain.OrdersState;
import com.example.springstatemachine.domain.Orders;
import com.example.springstatemachine.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OrdersStateChangeInterceptor extends StateMachineInterceptorAdapter<OrdersState, OrdersEvent> {

    private final OrdersRepository ordersRepository;

    @Override
    public void preStateChange(State<OrdersState, OrdersEvent> state, Message<OrdersEvent> message, Transition<OrdersState, OrdersEvent> transition, StateMachine<OrdersState, OrdersEvent> stateMachine, StateMachine<OrdersState, OrdersEvent> rootStateMachine) {
        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(OrdersService._ORDER_ID, -1L)))
                    .ifPresent(orderId -> {
                        Orders orders = ordersRepository.getOne(orderId);
                        orders.setState(state.getId());
                        ordersRepository.save(orders);
                    });
        });
    }

}
