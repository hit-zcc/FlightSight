package com.flight.core.gather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;


@Configuration
public class MongoConfig  {
public @Bean Mongo mongo() throws Exception {
    return new Mongo("localhost");
}
public @Bean MongoTemplate mongoTemplate() throws Exception {
    return new MongoTemplate(mongo(), "order");
}

}
