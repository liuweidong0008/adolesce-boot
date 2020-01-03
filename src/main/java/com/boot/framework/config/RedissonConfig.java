/*******************************************************************************
 * @(#)RedissonConfig.java 2019年09月27日 13:20
 * Copyright 2019 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.boot.framework.config;

import com.boot.framework.properties.RedissonProperties;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <b>Application name：</b> RedissonConfig.java <br>
 * <b>Application describing： </b> <br>
 * <b>@Date：</b> 2019年09月27日 13:20 <br>
 * <b>@author：</b> <a href="mailto:lwd@miyzh.com"> liuWeidong </a> <br>
 * <b>@version：</b>V2.0.0 <br>
 */
@Configuration
@ConditionalOnClass(Config.class)
public class RedissonConfig {
    @Autowired
    private RedissonProperties redssionProperties;

    /**
     * 单机模式自动装配
     * @return
     */
    @Bean
    RedissonClient redissonSingle() {
        Config config = new Config();
        StringBuilder addressBuilder = new StringBuilder("redis://");
        addressBuilder.append(redssionProperties.getHost())
                      .append(":")
                      .append(redssionProperties.getPort());

        SingleServerConfig serverConfig = config.useSingleServer();
        serverConfig.setAddress(addressBuilder.toString());
        serverConfig.setTimeout(redssionProperties.getTimeout());
        serverConfig.setConnectionPoolSize(redssionProperties.getConnectionPoolSize());
        serverConfig.setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize());
        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }
        return Redisson.create(config);
    }

}