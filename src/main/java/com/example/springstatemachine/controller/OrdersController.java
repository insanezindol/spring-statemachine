package com.example.springstatemachine.controller;

import com.example.springstatemachine.domain.Orders;
import com.example.springstatemachine.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/get-orders")
    public ResponseEntity getOrders() {
        log.info("/order/get-orders");
        return ResponseEntity.ok(ordersService.getOrders());
    }

    @PostMapping(value = "/new-orders", produces = "application/json", consumes = "application/json")
    public ResponseEntity newOrders(@RequestBody Orders orders) {
        log.info("/order/new-orders");
        return ResponseEntity.ok(ordersService.newOrders(orders));
    }

    @PostMapping(value = "/order-accept/{orderId}", produces = "application/json")
    public ResponseEntity orderAccept(@PathVariable Long orderId) {
        log.info("/order/order-accept");
        try {
            ordersService.orderAccept(orderId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("{ \"status\": 200, \"msg\" :\"ok\" }");
    }

    @PostMapping(value = "/order-denied/{orderId}", produces = "application/json")
    public ResponseEntity orderDenied(@PathVariable Long orderId) {
        log.info("/order/order-denied");
        try {
            ordersService.orderDenied(orderId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("{ \"status\": 200, \"msg\" :\"ok\" }");
    }

    @PostMapping(value = "/delivery-start/{orderId}", produces = "application/json")
    public ResponseEntity deliveryStart(@PathVariable Long orderId) {
        log.info("/order/delivery-start");
        try {
            ordersService.deliveryStart(orderId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("{ \"status\": 200, \"msg\" :\"ok\" }");
    }

    @PostMapping(value = "/delivery-end/{orderId}", produces = "application/json")
    public ResponseEntity deliveryEnd(@PathVariable Long orderId) {
        log.info("/order/delivery-end");
        try {
            ordersService.deliveryEnd(orderId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("{ \"status\": 200, \"msg\" :\"ok\" }");
    }

    @PostMapping(value = "/complete/{orderId}", produces = "application/json")
    public ResponseEntity complete(@PathVariable Long orderId) {
        log.info("/order/complete");
        try {
            ordersService.complete(orderId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("{ \"status\": 200, \"msg\" :\"ok\" }");
    }

}
