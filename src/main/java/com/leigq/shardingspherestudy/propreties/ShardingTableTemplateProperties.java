package com.leigq.shardingspherestudy.propreties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Sharding table template properties.
 * @author leiguoqing
 */
@ToString
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.shardingsphere.sharding")
public class ShardingTableTemplateProperties {

    /**
     * The Table templates.
     */
    private Map<String, String> tableTemplates = new LinkedHashMap<>();

}
