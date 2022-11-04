package com.leigq.shardingspherestudy.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leigq.shardingspherestudy.domain.entity.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * The interface Test mapper.
 * @author leiguoqing
 */
@Mapper
public interface TestMapper extends BaseMapper<Test> {

    Integer countAge();

    List<Test> listTest();
}
