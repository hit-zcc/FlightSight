package com.flight.core.gather.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
@Document
public class Order {
  @Id
  private String id; 
  
}
