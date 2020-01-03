package com.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.boot.framework.rabbitmq.RabbitMQSender;

@RunWith(SpringRunner.class) 
@SpringBootTest
public class RabbitMQTest {
	@Autowired
	private RabbitMQSender rabbitMQSender;
	
	@Test
	public void test1(){
		rabbitMQSender.sendStr();
	}
	
	@Test
	public void test2(){
		rabbitMQSender.sendObject();
	}
	
	@Test
	public void test3(){
		rabbitMQSender.sendStrToTopicExchange();
	}
	
	@Test
	public void test4(){
		rabbitMQSender.sendStrToFanoutExchange();
	}
}
