package com.projects.maven.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.maven.rabbitmq.pojos.Book;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.tools.json.JSONWriter;

public class ProductorSimple {

	public static void main(String[] args) throws IOException, TimeoutException {
		
		Book book = new Book();
		book.setId(1L);
		book.setDescription("Los cuentos de mi tia Panchita");
		book.setAuthor("Carmen Lira");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String orderJson = objectMapper.writeValueAsString(book);
				
		//Abir conexion AMQ
		ConnectionFactory connectionFactory = new ConnectionFactory();
		
		try(
			Connection connection = connectionFactory.newConnection();
			Channel channel = connection.createChannel())
		{
			
			//Crear la cola
			String queue = "primera-cola";
			channel.queueDeclare(queue, false, false, false, null);
			channel.basicPublish("", queue, null, orderJson.getBytes());
			
		}
		
		
		
	
	}
}


