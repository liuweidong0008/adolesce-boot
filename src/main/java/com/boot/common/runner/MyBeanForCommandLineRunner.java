package com.boot.common.runner;

import com.boot.common.properties.PersonProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
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
@Order(2)
public class MyBeanForCommandLineRunner implements CommandLineRunner {
	@Resource
	private PersonProperties personProperties;

	@Override
	public void run(String... arg0){
		System.err.println("通过实现CommandLineRunner接口，在spring boot项目启动后打印参数");
		Arrays.stream(arg0).forEach(System.err::println);
	}

}
