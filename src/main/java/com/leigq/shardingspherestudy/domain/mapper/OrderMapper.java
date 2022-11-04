package com.leigq.shardingspherestudy.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leigq.shardingspherestudy.domain.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * The interface Order mapper.
 *
 * @author leiguoqing
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> listOrder(@Param("startTime") String startTime, @Param("endTime") String endTime);

}
