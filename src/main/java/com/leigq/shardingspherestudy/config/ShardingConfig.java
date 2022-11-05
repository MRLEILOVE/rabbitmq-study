package com.leigq.shardingspherestudy.config;

import com.leigq.shardingspherestudy.propreties.ShardingTableTemplateProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The type Sharding config.
 * @author leiguoqing
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ShardingTableTemplateProperties.class)
@ConditionalOnProperty(prefix = "spring.shardingsphere", name = "enabled", havingValue = "true")
public class ShardingConfig implements InitializingBean {

    @Autowired
    private ShardingTableTemplateProperties properties;


    @Override
    public void afterPropertiesSet() {
        log.info("ShardingTableTemplateProperties = {}", properties);
    }
}
