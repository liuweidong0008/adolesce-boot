package com.boot.adolesce.framework.rabbitmq;

import com.boot.adolesce.module.user.entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class RabbitMQListener {
	@RabbitListener(queues = { "queueOne" })
	public void receiveStr(String message) throws UnsupportedEncodingException {
		System.err.println("queueOne队列接收到消息：" + message);
	}

	@RabbitListener(queues = { "queueTwo" })
	public void receiveObject(User user) throws UnsupportedEncodingException {
		System.err.println("queueTwo队列接收到消息：" + user);
	}

	@RabbitListener(queues = { "queueThree" })
	public void receiveFromQueueThree(String message) {
		System.err.println("queueThree队列接收到消息：" + message);
	}

	@RabbitListener(queues = { "queueFour" })
	public void receiveFromQueueFour(String message) {
		System.err.println("queueFour队列接收到消息：" + message);
	}

	@RabbitListener(queues = { "queueFive" })
	public void receiveFromQueueFive(String message) {
		System.err.println("queueFive队列接收到消息：" + message);
	}

	@RabbitListener(queues = { "queueSix" })
	public void receiveFromQueueSix(String message) {
		System.err.println("queueSix队列接收到消息：" + message);
	}
}