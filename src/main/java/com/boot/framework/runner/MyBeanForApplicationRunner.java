package com.boot.framework.runner;

import com.boot.framework.properties.PersonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 如果想在Spring Boot启动的时候运行一些特定的代码（如删除临时文件，清除缓存信息，读取配置文件信息，数据库连接）
 * 可以实现接口：ApplicationRunner 或者 CommandLineRunner
 * 这两个接口实现方式一样，它们都只提供了一个run方法
 * 他们的参数可以解析读取Program arguments里面配置的参数如：
 * 		 --name=lwd --age=29 --animal=maque
 * 如果有多个Runner类，@Order可以定义启动顺序
 */
@Component
@Order(1)
public class MyBeanForApplicationRunner implements ApplicationRunner{
	@Autowired
	private PersonProperties personProperties;

	@Override
	public void run(ApplicationArguments param) throws Exception {
		System.err.println("通过实现ApplicationRunner接口，在spring boot项目启动后打印参数");
		System.err.println("ApplicationRunner："+ Arrays.asList(param.getSourceArgs()));
		System.err.println("getOptionNames："+param.getOptionNames());
		System.err.println("getOptionValues："+param.getOptionValues("name"));
		System.err.println("getOptionValues："+param.getOptionValues("age"));
		System.err.println("getOptionValues："+param.getOptionValues("animal"));
	}

}
