/*******************************************************************************
 * @(#)RedissonProperties.java 2019年09月27日 11:34
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.framework.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class RedissonProperties {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private int database;

    private int timeout = 30000;
    private int connectionPoolSize = 64;
    private int connectionMinimumIdleSize=10;
    private int slaveConnectionPoolSize = 250;
    private int masterConnectionPoolSize = 250;
   /* private String[] sentinelAddresses;
    private String masterName;*/
}