package com.projects.maven.rabbitmq.pojos;

import java.io.Serializable;

public class Book implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String description;
	private String author;	
	
	public Book() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
