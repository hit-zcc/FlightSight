package com.flight.core.gather.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;

import com.flight.core.gather.config.MongoConfig;
@Controller
@Scope("prototype")
public class UserRespository {
	@Autowired
	MongoConfig mongo;
	MongoOperations mongoOperation;
	public User validateUser(String name,String password) throws Exception{
		 mongoOperation =mongo.mongoTemplate();
		Query q=new Query();
	    q.addCriteria(Criteria.where("name").is(name));
	    User o=mongoOperation.findOne(q, User.class);
	    if(o==null||!o.getPassword().equals(password)){
		return null;
	    }
	  return o;
	}
	
	public User register(String name,String password) throws Exception{
		 mongoOperation =mongo.mongoTemplate();
		User u=new User(name,password);
	    mongoOperation.save(u);
	  return u;
	}

}
