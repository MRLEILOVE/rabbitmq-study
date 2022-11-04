package com.leigq.shardingspherestudy.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leigq.shardingspherestudy.domain.entity.Test;
import com.leigq.shardingspherestudy.domain.mapper.TestMapper;
import org.springframework.stereotype.Service;

/**
 * The type Test service.
 *
 * @author leiguoqing
 */
@Service
public class TestService extends ServiceImpl<TestMapper, Test> {

    public void saveTest(Test test) {
        super.save(test);
    }

    public void updateTest(Test test) {
        super.updateById(test);
    }

    public Test selectTest(Long id) {
        return super.getById(id);
    }

    public boolean deleteTest(Long id) {
        return super.removeById(id);
    }

}
