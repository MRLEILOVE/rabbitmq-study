package com.leigq.shardingspherestudy.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 数据库标准-精准分片算法
 * @author leiguoqing
 */
@SuppressWarnings(value = "unused")
public class MyDataBasePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     * Do sharding string.
     *
     * @param databaseNames 所有分片库的集合
     * @param shardingValue 为分片属性，其中 logicTableName 为逻辑表，columnName 分片健（字段），value 为从 SQL 中解析出的分片健的值
     * @return 分片库的集合
     */
    @Override
    public String doSharding(Collection<String> databaseNames, PreciseShardingValue<Long> shardingValue) {
        for (String databaseName : databaseNames) {
            String value = (shardingValue.getValue() % databaseNames.size()) + 1 + "";
            if (databaseName.endsWith(value)) {
                return databaseName;
            }
        }
        throw new IllegalArgumentException("找不到分片数据库");
    }
}
