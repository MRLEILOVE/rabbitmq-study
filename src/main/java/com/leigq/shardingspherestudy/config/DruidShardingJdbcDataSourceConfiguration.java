package com.leigq.shardingspherestudy.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidFilterConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidSpringAopConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidStatViewServletConfiguration;
import com.alibaba.druid.spring.boot.autoconfigure.stat.DruidWebStatFilterConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Druid监控配置
 * <pre>
 * 因为我们分表之后的数据源都交给了sharding管理，故我们排除了Druid数据源的自动装配，这也导致Druid监控相关配置也不会自动装配了
 * 所以，需要在这里手动配置下
 * </pre>
 * <br/>
 * 参考：<a href='https://blog.csdn.net/zwjzone/article/details/124387026'>ShardingSphere-JDBC整合druid配置数据源</a>
 * @author leiguoqing
 */
@Configuration
@ConditionalOnClass(DruidDataSourceAutoConfigure.class)
@EnableConfigurationProperties({DruidStatProperties.class})
@Import({
        DruidSpringAopConfiguration.class,
        DruidStatViewServletConfiguration.class,
        DruidWebStatFilterConfiguration.class,
        DruidFilterConfiguration.class})
public class DruidShardingJdbcDataSourceConfiguration {
}
