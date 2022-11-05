package com.leigq.shardingspherestudy.task;

import com.alibaba.druid.pool.DruidDataSource;
import com.leigq.shardingspherestudy.propreties.ShardingTableTemplateProperties;
import com.leigq.shardingspherestudy.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.yaml.config.sharding.YamlShardingRuleConfiguration;
import org.apache.shardingsphere.core.yaml.config.sharding.YamlTableRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.connection.ShardingConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

import static com.leigq.shardingspherestudy.cons.ShardingCons.ORDER_TABLE_SHARD_NODE_NUM;

/**
 * The type Sharding task.
 *
 * @author leiguoqing
 */
@Slf4j
@Component
public class ShardingTask {

    /**
     * The Jdbc template.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * The Sharding data source.
     */
    @Autowired
    private DataSource shardingDataSource;

    /**
     * The Yaml sharding rule configuration.
     */
    @Autowired
    private YamlShardingRuleConfiguration yamlShardingRuleConfiguration;

    /**
     * The Sharding table template properties.
     */
    @Autowired
    private ShardingTableTemplateProperties shardingTableTemplateProperties;

    /**
     * Auto create order shard table.
     * <br/>
     * 每个月最后一天23:00:00自动创建下个月的订单分片表
     */
    @Scheduled(cron = "0 0 23 L * ?")
    public void autoCreateOrderShardTable() {
        try {
            final ShardingConnection connection = (ShardingConnection) shardingDataSource.getConnection();

            // 获取多个数据源
            final Map<String, DataSource> dataSourceMap = connection.getDataSourceMap();

            dataSourceMap.forEach((k, ds) -> {
                final DruidDataSource druidDataSource = (DruidDataSource) ds;

                final String url = druidDataSource.getUrl();

                // 获取真实数据库名称
                final String dbName = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));

                // 为每个数据源创建下个月的分表
                final Map<String, YamlTableRuleConfiguration> tables = yamlShardingRuleConfiguration.getTables();

                final Map<String, String> tableTemplates = shardingTableTemplateProperties.getTableTemplates();

                tables.forEach((a, b) -> {

                    final String logicTable = b.getLogicTable();
                    final String tableTemplateName = tableTemplates.get(b.getLogicTable());

                    for (int i = 0; i < ORDER_TABLE_SHARD_NODE_NUM; i++) {
                        try {
                            final String createTableDdl = this.getCreateTableDdl(dbName, this.getOrderShardTableName(logicTable, i), tableTemplateName);
                            jdbcTemplate.execute(createTableDdl);
                        } catch (DataAccessException e) {
                            log.error("创建订单分片表失败", e);
                        }
                    }

                });

            });
        } catch (SQLException e) {
            log.error("获取数据库连接异常", e);
        }
    }

    /**
     * Gets create table ddl.
     *
     * @param dbName                 the db name
     * @param shardTableName         the shard table name
     * @param shardTableTemplateName the shard table template name
     * @return the create table ddl
     */
    private String getCreateTableDdl(String dbName, String shardTableName, String shardTableTemplateName) {
        final String format = "CREATE TABLE IF NOT EXISTS `%s`.%s LIKE `%s`.`%s`";
        return String.format(format, dbName, shardTableName, dbName, shardTableTemplateName);
    }

    /**
     * Gets shard table name.
     *
     * @param logicTableName the logic table name
     * @param nodesNo        the nodes no
     * @return the shard table name
     */
    private String getOrderShardTableName(String logicTableName, int nodesNo) {
        LocalDateTime nextMonthDate = LocalDateTime.now().plusMonths(1);
        // 类似这样：t_order_0_2022_09
        return logicTableName + "_" + nodesNo + "_" + nextMonthDate.getYear() + "_" + DateUtils.getMonth(nextMonthDate);
    }
}
