package com.projects.maven.rabbitmq;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConsumidorEventosDeportivos {

	private static final String EXCHANGE = "Eventos Deportivos";

	public static void main(String[] args) throws IOException, TimeoutException {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		
		//Abrir la conexion
		Connection connection = connectionFactory.newConnection();
		
		//Crear canal
		Channel channel = connection.createChannel();
		
		//Declar el exchange de eventos tipo TOPIC
		channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);

		//Declar la cola asociada al exchange eventos
		String queueName = channel.queueDeclare().getQueue();
		
		//Patron del routing-key -> country.sport.eventType
		// * -> identifica una palabra
		// # -> identifica varias palabras delimitadas por '.'
		// Por ejemplo
		// eventos tennis -> *.tennis.*
		// eventos en Espana -> es.# / es.*.*
		// todos los eventos -> #
		
		System.out.println("Ingrese el routing-kek");
		
		Scanner scanner = new Scanner(System.in);
		String routingKey = scanner.nextLine();
		channel.queueBind(queueName, EXCHANGE, routingKey);
		
		//Crear subscripcion a una cola asociada al exchange eventos
		// Consumidor
				channel.basicConsume(queueName, true, (consumerTag, message) -> {
					String messageBody = new String(message.getBody(), Charset.defaultCharset());
					System.out.println("Mensaje recibido: " + messageBody);
					System.out.println("Routing-key : " + message.getEnvelope().getRoutingKey());
				}, (consumerTag) -> {
					System.out.println("Consumidor " + consumerTag + "cancelado");
				});
		
		
	}
}
