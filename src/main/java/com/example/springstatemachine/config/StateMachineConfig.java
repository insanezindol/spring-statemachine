package com.example.springstatemachine.config;

import com.example.springstatemachine.domain.OrdersEvent;
import com.example.springstatemachine.domain.OrdersState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Slf4j
@EnableStateMachineFactory
@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<OrdersState, OrdersEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<OrdersState, OrdersEvent> states) throws Exception {
        states.withStates()
                .initial(OrdersState.NEW_ORDER)
                .states(EnumSet.allOf(OrdersState.class))
                .end(OrdersState.ORDER_CANCEL)
                .end(OrdersState.COMPLETE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrdersState, OrdersEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(OrdersState.NEW_ORDER)
                .target(OrdersState.ORDER_APPROVED)
                .event(OrdersEvent.OrderAcceptEvent)
                .and()
                .withExternal()
                .source(OrdersState.NEW_ORDER)
                .target(OrdersState.ORDER_CANCEL)
                .event(OrdersEvent.OrderDeniedEvent)
                .and()
                .withExternal()
                .source(OrdersState.ORDER_APPROVED)
                .target(OrdersState.DELIVERY_START)
                .event(OrdersEvent.DeliveryStartEvent)
                .and()
                .withExternal()
                .source(OrdersState.DELIVERY_START)
                .target(OrdersState.DELIVERY_END)
                .event(OrdersEvent.DeliveryEndEvent)
                .and()
                .withExternal()
                .source(OrdersState.DELIVERY_END)
                .target(OrdersState.COMPLETE)
                .event(OrdersEvent.CompleteEvent);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrdersState, OrdersEvent> config) throws Exception {
        StateMachineListenerAdapter<OrdersState, OrdersEvent> adapter = new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<OrdersState, OrdersEvent> from, State<OrdersState, OrdersEvent> to) {
                log.info("State change from [{}] to [{}]", from, to);
            }
        };

        config.withConfiguration().listener(adapter);
    }

}
