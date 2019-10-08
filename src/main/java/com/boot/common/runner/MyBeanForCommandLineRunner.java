package com.boot.common.runner;

import org.springframework.boot.CommandLineRunner;

public class MyBeanForCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... arg0) throws Exception {
		System.err.println(arg0);
	}

}
