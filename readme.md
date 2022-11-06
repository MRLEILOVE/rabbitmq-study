## Sharding JDBC使用教程

实现功能：

1. 分库（2个分库1个默认库）
2. 分表（按id取模、按时间年月分）
3. 整合Druid数据源
4. 定时任务，每月最后一天23:00:00创建下个月的表

详细说明：

1. 分库规则采用标准-精准分片、范围分片算法
2. 分表采用复合分片，根据order_id、create_time两个字段进行分表

其它注意事项：

使用规范：https://shardingsphere.apache.org/document/4.1.1/cn/features/sharding/use-norms/sql/

