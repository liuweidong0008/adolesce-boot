package com.boot.common.rabbitmq;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boot.user.entity.User;

@Component
public class RabbitMQSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendStr() {
		String context = "hello " + new Date();
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("queueOne", context);
	}

	public void sendObject() {
		User user = new User();
		user.setName("刘威东");
		user.setEmail("liuweidong0008@163.com");
		user.setPhone("18301327332");
		this.rabbitTemplate.convertAndSend("queueTwo", user);
	}

	public void sendStrToTopicExchange() {
		String context = "hello " + new Date();
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("topicExchange", "queue.123", context);
	}

	public void sendStrToFanoutExchange() {
		String context = "hello " + new Date();
		System.out.println("Sender : " + context);
		this.rabbitTemplate.convertAndSend("fanoutExchange", "", context);
	}
}