package com.flight.core.gather.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection="User")
public class User {
	  @Id
	  private String id; 
	  @Field
	  private String name; 
	  @Field
	  private String password;
	  public User(String name, String password) {
		// TODO Auto-generated constructor stub
		  this.name=name;
		  this.password=password;
	}
	public String getId(){
		  return id;
	  }
	  public String getName(){
		  return name;
	  }
	  public String getPassword(){
		  return password;
	  }
}
