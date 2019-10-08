package com.boot.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit
public class RabbitMQConfig implements RabbitListenerConfigurer {
	@Bean
	public Queue queueOne() {
		return new Queue("queueOne");
	}

	@Bean
	public Queue queueTwo() {
		return new Queue("queueTwo");
	}

	//topic模式交换机
	//topic模式交换机
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange("topicExchange");
	}

	@Bean
	public Queue queueThree() {
		return new Queue("queueThree");
	}

	@Bean
	public Queue queueFour() {
		return new Queue("queueFour");
	}

	/**
	 *  *表示一个词.
	 *  #表示零个或多个词.
	 */
	@Bean
	public Binding bindingExchangeAndQueueThree(TopicExchange topicExchange, Queue queueThree) {
		return BindingBuilder.bind(queueThree).to(topicExchange).with("queue.three");
	}

	@Bean
	public Binding bindingExchangeAndQueueFour(TopicExchange topicExchange, Queue queueFour) {
		return BindingBuilder.bind(queueFour).to(topicExchange).with("queue.*");
	}

	//fanout交换机
	@Bean
	public FanoutExchange fanoutExchange() {
		return new FanoutExchange("fanoutExchange");
	}

	@Bean
	public Queue queueFive() {
		return new Queue("queueFive");
	}

	@Bean
	public Queue queueSix() {
		return new Queue("queueSix");
	}

	//这里使用了queueFive、queueSix三个队列绑定到fanoutExchange交换机上面，发送端的routing_key写任何字符都会被忽略：
	@Bean
	public Binding bindingExchangeAndQueueFive(FanoutExchange fanoutExchange, Queue queueFive) {
		return BindingBuilder.bind(queueFive).to(fanoutExchange);
	}

	@Bean
	public Binding bindingExchangeAndQueueSix(FanoutExchange fanoutExchange, Queue queueSix) {
		return BindingBuilder.bind(queueSix).to(fanoutExchange);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
			Jackson2JsonMessageConverter producerJackson2MessageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter);
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}
}