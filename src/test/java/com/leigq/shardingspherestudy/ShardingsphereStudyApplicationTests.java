package com.leigq.shardingspherestudy;

import com.leigq.shardingspherestudy.domain.entity.Order;
import com.leigq.shardingspherestudy.domain.mapper.OrderMapper;
import com.leigq.shardingspherestudy.domain.mapper.TestMapper;
import com.leigq.shardingspherestudy.service.OrderService;
import com.leigq.shardingspherestudy.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ShardingsphereStudyApplicationTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TestService testService;

    @Autowired
    private DataSource shardingDataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void orderServiceTest() {

        for (int i = 1; i <= 1000; i++) {

            final Order order = Order.builder()
                    .msgId(i + "")
                    .name("订单name" + i)
                    .build();

            if (i > 500) {
                final LocalDateTime localDateTime = LocalDateTime.now().minusMonths(1);
                final Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
                order.setCreateTime(Date.from(instant));
                order.setUpdateTime(Date.from(instant));
            } else {
                final Date date = new Date();
                order.setCreateTime(date);
                order.setUpdateTime(date);
            }

            orderService.saveTest(order);
        }
    }

    @Test
    void deleteOrderTest() {
        for (long i = 1; i <= 100; i++) {
            final Order order = Order.builder()
                    .orderId(i)
                    .build();

            if (i > 50) {
                final LocalDateTime localDateTime = LocalDateTime.now().minusMonths(1);
                final Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
                order.setCreateTime(Date.from(instant));
            } else {
                final Date date = new Date();
                order.setCreateTime(date);
            }

            orderService.deleteTest(order);
        }
    }

    @Test
    void listOrderTest() {
        final List<Order> orders = orderMapper.listOrder("2022-10-01 00:00:00", "2022-11-30 23:00:00");
        orders.forEach(o -> {
            System.out.println("o = " + o);
        });
    }

    @Test
    void saveTest() throws SQLException {
        /*final ShardingConnection connection = (ShardingConnection) shardingDataSource.getConnection();
        final Map<String, DataSource> dataSourceMap = connection.getDataSourceMap();
        dataSourceMap.forEach((k, ds) -> {

            System.out.println("k = " + k);
            System.out.println("ds = " + ds);
        });*/
        final com.leigq.shardingspherestudy.domain.entity.Test test = com.leigq.shardingspherestudy.domain.entity.Test.builder()
                .age(1)
                .build();
        testService.saveTest(test);

        testService.updateTest(test);
    }

    @Test
    void updateTest() {
        final com.leigq.shardingspherestudy.domain.entity.Test test = com.leigq.shardingspherestudy.domain.entity.Test.builder()
                .id(1587477980473131009L)
                .age(10)
                .build();
        testService.updateTest(test);
    }

    @Test
    void deleteTest() {
        testService.deleteTest(1587477980473131009L);
    }

    @Test
    void countAgeTest() {
        final Integer countAge = testMapper.countAge();
        System.out.println("countAge = " + countAge);
    }

    @Test
    void listTest() {
        final List<com.leigq.shardingspherestudy.domain.entity.Test> listTest = testMapper.listTest();
        listTest.forEach(test -> System.out.println(listTest));
    }
}
