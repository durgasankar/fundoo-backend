package com.bridgeLabz.fundooNotes.utility;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgeLabz.fundooNotes.response.MailObject;

/**
 * RabbitMQ sender class configuration allows us to send a mail by using
 * exchange routing key and mail information.
 * 
 * @author Durgasankar Mishra
 * @created 2020-02-09
 * @version 1.0
 *
 */
@Component
public class RabbitMQSender {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("rmq.rube.exchange")
	private String exchange;

	@Value("rube.key")
	private String routingkey;

	public boolean send(MailObject message) {
		rabbitTemplate.convertAndSend(exchange, routingkey, message);
		return true;
	}

}