package com.projects.maven.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProductorEventos {

	
	private static final String EVENTOS = "eventos";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		
		try(Connection connection = connectionFactory.newConnection();
			Channel channel = connection.createChannel())
		{
		
			//Crear Fanout
			channel.exchangeDeclare(EVENTOS, BuiltinExchangeType.FANOUT);
			
			//Enviar mensaje
			int count = 1;
			while (true) {
				String message = "Evento " + count;
				System.out.println("Produciendo mensaje : " + message);
				channel.basicPublish(EVENTOS, "", null, message.getBytes());	
				Thread.sleep(1000);
				count ++;
			}
			
			
		}
	}
	
}
