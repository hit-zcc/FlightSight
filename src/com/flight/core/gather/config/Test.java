package com.flight.core.gather.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

public class Test {

	public static void main(String[] args){
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);  
	    MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
	    mongoOperation.toString();
	}
}
