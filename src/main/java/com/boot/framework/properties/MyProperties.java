package com.boot.framework.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取自定义配置文件
 * 方式一：@ConfigurationProperties + @PropertySource
 * 		1、加上@Configuration或@Component注解将配置类加入到spring容器中
 * 		2、@PropertySource({ "classpath:my.properties" }) 指定文件
 * 	    3、@ConfigurationProperties(prefix = "my") 读取
 *
 * 方式二：@Value + @PropertySource
 *		1、加上@Configuration或@Component注解将配置类加入到spring容器中
 *  	2、@PropertySource({ "classpath:my.properties" }) 指定文件
 *  	3、@Value 进行读取
 */
@Data
@Component
@PropertySource({ "classpath:my.properties" })
public class MyProperties {
	@Value("${my.name}")
	private String name;
	@Value("${my.age}")
	private int age;
	@Value("${my.address}")
	private String address;
}