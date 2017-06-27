package com.flight.core.gather.entity;

import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.data.annotation.*;
@Document
public class Order {
  @Id
  private String id; 
  @Field
  private String name;
  public String getName(){
	  return name;
  }
  public String getId(){
	  return id;
  }
  
}
