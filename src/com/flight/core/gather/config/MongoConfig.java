package com.flight.core.gather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;



@Configuration
public class MongoConfig  {
	  public @Bean  
	  MongoDbFactory mongoDbFactory() throws Exception {  
	    return new SimpleMongoDbFactory(new Mongo(), "Flight");  
	  }  
	   
	  public @Bean  
	  MongoTemplate mongoTemplate() throws Exception {  
	   
	      
	    MappingMongoConverter converter =   
	        new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext());  
	    converter.setTypeMapper(new DefaultMongoTypeMapper(null));  
	   
	    MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), converter);  
	   
	    return mongoTemplate;  
	   
	  }  
}
