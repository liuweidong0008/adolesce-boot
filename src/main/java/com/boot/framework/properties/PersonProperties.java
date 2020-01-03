package com.boot.framework.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Value注解读取application.yml或application.propertis
 * 		1、加上@Configuration或@Component注解将配置类加入到spring容器中
 * 		2、使用@Value注解进行读取
 */
@Data
@Configuration
public class PersonProperties {
	@Value("${person.name}")
	private String name;
	@Value("${person.age}")
	private int age;
	@Value("${person.address}")
	private String address;
}