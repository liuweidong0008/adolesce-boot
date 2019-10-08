package com.boot.common.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler1Task {
	private int count = 0;

	@Scheduled(cron = "0/3 * * * * ?")
	private void process() {
		System.err.println("this is scheduler task runing  " + this.count++);
	}
}