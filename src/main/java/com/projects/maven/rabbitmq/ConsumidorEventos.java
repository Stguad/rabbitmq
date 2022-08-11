package com.projects.maven.rabbitmq;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConsumidorEventos {

	private static final String EVENTOS = "eventos";

	public static void main(String[] args) throws IOException, TimeoutException {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		
		//Abrir la conexion
		Connection connection = connectionFactory.newConnection();
		
		//Crear canal
		Channel channel = connection.createChannel();
		
		//Declar el exchange de eventos
		channel.exchangeDeclare(EVENTOS, BuiltinExchangeType.FANOUT);

		//Declar la cola asociada al exchange eventos
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EVENTOS, "");
		
		//Crear subscripcion a una cola asociada al exchange eventos
		// Consumidor
				channel.basicConsume(queueName, true, (consumerTag, message) -> {
					String messageBody = new String(message.getBody(), Charset.defaultCharset());
					System.out.println("Mensaje recibido: " + messageBody);
				}, (consumerTag) -> {
					System.out.println("Consumidor " + consumerTag + "cancelado");
				});
		
		
	}
}
