package com.boot.adolesce.framework.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ConfigurationProperties注解读取application.yml或application.propertis
 * 		1、加上@Configuration 或 @Component注解
 * 		   或者在 使用类上 如Controller上加上 @EnableConfigurationProperties({DemoProperties.class})注解将配置类加入到spring容器中
 * 		2、加上@ConfigurationProperties注解读取application.yml或application.propertis
 */
@Data
@Component
@ConfigurationProperties(prefix = "demo")	//装配属性
public class DemoProperties {
	private String one;
	private String two;
	private String three;

}