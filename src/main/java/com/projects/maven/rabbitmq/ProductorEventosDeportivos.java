package com.projects.maven.rabbitmq;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProductorEventosDeportivos {

	
	private static final String EXCHANGE = "Eventos Deportivos";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		
		try(Connection connection = connectionFactory.newConnection();
			Channel channel = connection.createChannel())
		{
		
			//Crear Exchange de tipo Topic
			channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);
			
			//Lista de paise es, fr , usa
			List<String> countrys = Arrays.asList("es", "fr", "us");
			
			//Lista de Deportes futbol, tennis, basket
			List<String>  sports = Arrays.asList("futbol", "tennis", "basket");
			
			//List tipos de eventos 'live' , 'noticias'
			List<String>  eventTypes = Arrays.asList("live","noticias");
			

			//Enviar mensaje
			int count = 1;
			while (true) {
				shuffle(countrys, sports, eventTypes);

				String country = countrys.get(0);
				String sport = sports.get(0);
				String eventType = eventTypes.get(0);
				
				//Generar routing-key
				// routingKey -> country.sport.eventType
				String routingKey = country.concat(".").concat(sport).concat(".").concat(eventType);
				
				String message = "Evento " + count;
				System.out.println("Produciendo mensaje : " + routingKey + " " + message);
				channel.basicPublish(EXCHANGE, routingKey, null, message.getBytes());	
				Thread.sleep(1000);
				count ++;
			}
			
			
		}
	}

	private static void shuffle(List<String> countrys, List<String> sports, List<String> eventTypes) {
		Collections.shuffle(countrys);
		Collections.shuffle(sports);
		Collections.shuffle(eventTypes);
	}
	
}
