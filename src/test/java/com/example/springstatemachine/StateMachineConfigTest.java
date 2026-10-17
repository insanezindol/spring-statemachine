package com.example.springstatemachine;

import com.example.springstatemachine.domain.Orders;
import com.example.springstatemachine.domain.OrdersState;
import com.example.springstatemachine.repository.OrdersRepository;
import com.example.springstatemachine.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class StateMachineConfigTest {

    @Autowired
    OrdersService ordersService;

    @Autowired
    OrdersRepository ordersRepository;

    Orders orders;

    @BeforeEach
    void setup() {
        orders = Orders.builder()
                .name("chicken")
                .amount(5)
                .build();
    }

    @Transactional
    @Test
    void orderFlowTest() {
        // create new order
        Orders savedOrders = ordersService.newOrders(orders);
        long orderId = savedOrders.getId();

        Orders checkedOrders = ordersRepository.findById(orderId).orElseThrow();
        assertThat(checkedOrders.getState()).isEqualTo(OrdersState.NEW_ORDER);

        // accept order
        ordersService.orderAccept(orderId);
        checkedOrders = ordersRepository.findById(orderId).orElseThrow();
        assertThat(checkedOrders.getState()).isEqualTo(OrdersState.ORDER_APPROVED);

        // start delivery
        ordersService.deliveryStart(orderId);
        checkedOrders = ordersRepository.findById(orderId).orElseThrow();
        assertThat(checkedOrders.getState()).isEqualTo(OrdersState.DELIVERY_START);

        // end delivery
        ordersService.deliveryEnd(orderId);
        checkedOrders = ordersRepository.findById(orderId).orElseThrow();
        assertThat(checkedOrders.getState()).isEqualTo(OrdersState.DELIVERY_END);

        // complete order
        ordersService.complete(orderId);
        checkedOrders = ordersRepository.findById(orderId).orElseThrow();
        assertThat(checkedOrders.getState()).isEqualTo(OrdersState.COMPLETE);
    }

}
