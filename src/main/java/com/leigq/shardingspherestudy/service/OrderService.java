package com.leigq.shardingspherestudy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leigq.shardingspherestudy.domain.entity.Order;
import com.leigq.shardingspherestudy.domain.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
 * The type Order service.
 *
 * @author leiguoqing
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    public void saveTest(Order order) {
        super.save(order);
    }

    public void updateTest(Order order) {
        super.updateById(order);
    }

    public Order selectTest(Long id) {
        return super.getById(id);
    }

    public boolean deleteTest(Order order) {
        return super.lambdaUpdate()
                .eq(Order::getOrderId, order.getOrderId())
                .eq(Order::getCreateTime, order.getCreateTime())
                .remove();
    }

}
