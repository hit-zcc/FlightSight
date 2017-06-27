package com.flight.core.gather.config;
import java.util.List;
import java.util.Set;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import com.flight.core.gather.entity.User;

public class Test {

	public static void main(String[] args){
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);  
	    MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
	    Set s=mongoOperation.getCollectionNames();
	    Query q=new Query();
	    q.addCriteria(Criteria.where("name").is("zcc"));
	    User o=mongoOperation.findOne(q, User.class);
	    System.out.print(o.getId());
	}
}
