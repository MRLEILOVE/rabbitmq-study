package com.leigq.shardingspherestudy.config;


import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 数据库标准-范围分片算法
 *
 * @author leiguoqing
 */
@SuppressWarnings(value = "unused")
public class MyDataBaseRangeShardingAlgorithm implements RangeShardingAlgorithm<Long> {
    /**
     * Do sharding collection.
     *
     * @param databaseNames 所有分片库的集合
     * @param rangeShardingValue 分片值范围
     * @return 分片库的集合
     */
    @Override
    public Collection<String> doSharding(Collection<String> databaseNames, RangeShardingValue<Long> rangeShardingValue) {
        Set<String> result = new LinkedHashSet<>();
        // between and 的起始值
        long lower = rangeShardingValue.getValueRange().lowerEndpoint();
        long upper = rangeShardingValue.getValueRange().upperEndpoint();
        // 循环范围计算分库逻辑
        for (long i = lower; i <= upper; i++) {
            for (String databaseName : databaseNames) {
                if (databaseName.endsWith((i % databaseNames.size()) + 1 + "")) {
                    result.add(databaseName);
                }
            }
        }
        return result;
    }
}
