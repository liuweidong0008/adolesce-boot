package com.boot.common.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

//如果你想在Spring Boot启动的时候运行一些特定的代码，你可以实现接口 ApplicationRunner或者 CommandLineRunner，这两个接口实现方式一样，它们都只提供了一个run方法。
public class MyBeanForApplicationRunner implements ApplicationRunner{

	@Override
	public void run(ApplicationArguments param) throws Exception {
	}

	
}
