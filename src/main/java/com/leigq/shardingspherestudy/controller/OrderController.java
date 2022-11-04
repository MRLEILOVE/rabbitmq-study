package com.leigq.shardingspherestudy.controller;

import com.leigq.shardingspherestudy.domain.entity.Order;
import com.leigq.shardingspherestudy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/test")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/order")
    public void saveOrder() {
        final Order order = Order.builder()
                //.orderId(1L)
                .msgId("11111")
                .name("订单name")
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        orderService.saveTest(order);
    }
}
