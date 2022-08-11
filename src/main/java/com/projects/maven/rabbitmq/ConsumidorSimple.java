package com.projects.maven.rabbitmq;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConsumidorSimple {

	public static void main(String[] args) throws IOException, TimeoutException {

		// Abrir la conexion
		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();

		// Crear la cola
		String queue = "primera-cola";
		channel.queueDeclare(queue, false, false, false, null);

		// Consumidor
		channel.basicConsume(queue, true, (consumerTag, message) -> {
			String messageBody = new String(message.getBody(), Charset.defaultCharset());
			System.out.println("Mensaje : " + messageBody);
			System.out.println("Exchange: " + message.getEnvelope().getExchange());
			System.out.println("Routing-Key: " + message.getEnvelope().getRoutingKey());
			System.out.println("Delivery : " + message.getEnvelope().getDeliveryTag());

		}, (consumerTag) -> {
			System.out.println("Consumidor " + consumerTag + "cancelado");
		});

	}
}