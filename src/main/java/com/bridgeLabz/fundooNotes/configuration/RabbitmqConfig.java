package com.bridgeLabz.fundooNotes.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for rabbit MQ. which contains the Template for rabbitMQ
 * 
 * @author Durgasankar Mishra
 * @created 2020-02-09
 * @version 1.0
 *
 */
@Configuration
public class RabbitmqConfig {

	@Autowired
	private ConnectionFactory rabbitConnectionFactory;

	/**
	 * Creates the object by taking exchange name, durability and auto delete option
	 * as input parameter.
	 * 
	 * @return {@link DirectExchange}
	 */
	@Bean
	public DirectExchange rubeExchange() {
		return new DirectExchange("rmq.rube.exchange", true, false);
	}

	/**
	 * Creates the object by taking exchange name, durability as input parameter.
	 * 
	 * @return {@link Queue}
	 */
	@Bean
	public Queue rubeQueue() {
		return new Queue("rmq.rube.queue", true);
	}

	@Bean
	public Binding rubeExchangeBinding(DirectExchange rubeExchange, Queue rubeQueue) {
		return BindingBuilder.bind(rubeQueue).to(rubeExchange).with("rube.key");
	}

	/**
	 * RabbitMq Template which contains {@link ConnectionFactory},
	 * {@link DirectExchange} and routing key.
	 * 
	 * @return {@link RabbitTemplate}
	 */
	@Bean
	public RabbitTemplate rubeExchangeTemplate() {
		RabbitTemplate rabbitTemplet = new RabbitTemplate(rabbitConnectionFactory);
		rabbitTemplet.setConnectionFactory(rabbitConnectionFactory);
		rabbitTemplet.setExchange("rmq.rube.exchange");
		rabbitTemplet.setRoutingKey("rube.key");
		return rabbitTemplet;
	}

}