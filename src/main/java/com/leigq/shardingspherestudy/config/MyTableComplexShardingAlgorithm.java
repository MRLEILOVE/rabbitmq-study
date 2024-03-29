package com.leigq.shardingspherestudy.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Range;
import com.leigq.shardingspherestudy.domain.entity.Order;
import com.leigq.shardingspherestudy.utils.DateUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.time.LocalDateTime;
import java.util.*;

import static com.leigq.shardingspherestudy.cons.ShardingCons.ORDER_TABLE_SHARD_NODE_NUM;

/**
 * 表的复杂分片
 *
 * @author leiguoqing
 */
@SuppressWarnings(value = "unused")
public class MyTableComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    /**
     * 分片表的集合
     *
     * @param tableNames               所有分片表的集合
     * @param complexKeysShardingValue 分片健集合
     * @return 分片目标表的集合
     */
    @Override
    public Collection<String> doSharding(Collection<String> tableNames, ComplexKeysShardingValue<String> complexKeysShardingValue) {

        // 得到每个分片健对应的值
        Collection<Long> idValues = this.getOrderIdShardingValue(complexKeysShardingValue, StringUtils.camelToUnderline(Order.Fields.orderId));
        Collection<LocalDateTime> createTimeValues = this.getCreateTimeShardingValue(complexKeysShardingValue, StringUtils.camelToUnderline(Order.Fields.createTime));

        // 目标表
        List<String> targetTableNames = new ArrayList<>();

        // 对两个分片健进行分库计算 `order_0_2022_11`
        for (Long id : idValues) {
            for (LocalDateTime date : createTimeValues) {
                // id取模，时间取年、月
                String suffix = id % ORDER_TABLE_SHARD_NODE_NUM + "_" + date.getYear() + "_" + DateUtils.getMonth(date);

                for (String tableName : tableNames) {
                    if (tableName.endsWith(suffix)) {
                        targetTableNames.add(tableName);
                    }
                }
            }
        }

        return targetTableNames;
    }


    /**
     * 订单号的分片目标表的集合
     *
     * @param shardingValues the sharding values
     * @param key            order_id
     * @return 分片表的集合
     */
    private Collection<Long> getOrderIdShardingValue(ComplexKeysShardingValue<String> shardingValues, final String key) {

        // 分片表的集合
        Set<Long> targetOrderIds = new LinkedHashSet<>();

        // 精准
        final Map<String, Collection<String>> columnNameAndShardingValuesMap = shardingValues.getColumnNameAndShardingValuesMap();
        if (columnNameAndShardingValuesMap.containsKey(key)) {
            final List<String> orderIdsStrings = (List<String>) columnNameAndShardingValuesMap.get(key);
            for (int i = 0; i < orderIdsStrings.size(); i++) {
                // 多一层转换，因为当真正运行时，orderIdsStrings会被赋值为Long类型
                final String s = String.valueOf(orderIdsStrings.get(i));
                targetOrderIds.add(Long.valueOf(s));
            }
        }

        // 范围
        final Map<String, Range<String>> columnNameAndRangeValuesMap = shardingValues.getColumnNameAndRangeValuesMap();

        if (columnNameAndRangeValuesMap.containsKey(key)) {
            final Range<String> orderIdsRange = columnNameAndRangeValuesMap.get(key);
            // between and 的起始值 多一层转换，因为当真正运行时，orderIdsStrings会被赋值为Long类型
            long lower = Long.parseLong(String.valueOf(orderIdsRange.lowerEndpoint()));
            long upper = Long.parseLong(String.valueOf(orderIdsRange.upperEndpoint()));

            // 循环范围计算分库逻辑
            for (long i = lower; i <= upper; i++) {
                targetOrderIds.add(i);
            }
        }
        return targetOrderIds;
    }

    /**
     * 创建时间的分片目标表的集合
     *
     * @param shardingValues the sharding values
     * @param key            create_time
     * @return 分片表的集合
     */
    private Collection<LocalDateTime> getCreateTimeShardingValue(ComplexKeysShardingValue<String> shardingValues, final String key) {

        // 分片表的集合
        final Set<LocalDateTime> targetCreateTimes = new LinkedHashSet<>();

        // 精准
        final Map<String, Collection<String>> columnNameAndShardingValuesMap = shardingValues.getColumnNameAndShardingValuesMap();
        if (columnNameAndShardingValuesMap.containsKey(key)) {
            final List<String> createTimeStrings = (List<String>) columnNameAndShardingValuesMap.get(key);
            for (int i = 0; i < createTimeStrings.size(); i++) {
                // 多一层转换，因为当真正运行时，createTimeStrings会被赋值为Timestamp类型
                final String s = String.valueOf(createTimeStrings.get(i));
                targetCreateTimes.add(DateUtils.toLocalDateTime(s));
            }
        }

        // 范围
        final Map<String, Range<String>> columnNameAndRangeValuesMap = shardingValues.getColumnNameAndRangeValuesMap();

        if (columnNameAndRangeValuesMap.containsKey(key)) {
            final Range<String> createTimesRange = columnNameAndRangeValuesMap.get(key);
            // between and 的起始值 多一层转换，因为当真正运行时，createTimesRange会被赋值为Timestamp类型
            LocalDateTime lower = DateUtils.toLocalDateTime(String.valueOf(createTimesRange.lowerEndpoint()));
            LocalDateTime upper = DateUtils.toLocalDateTime(String.valueOf(createTimesRange.upperEndpoint()));

            //年差
            final int years = lower.getYear() - upper.getYear();

            //月差
            final int months = years * 12 + (upper.getMonthValue() - lower.getMonthValue());

            final Set<LocalDateTime> createTimes = new LinkedHashSet<>();

            // 计算目标表时间分片逻辑
            for (int i = 0; i <= months; i++) {
                createTimes.add(lower.plusMonths(i));
            }

            targetCreateTimes.addAll(createTimes);
        }

        return targetCreateTimes;
    }
}
