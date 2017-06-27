package com.flight.core.gather.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.bson.types.ObjectId;

import com.flight.core.gather.config.MongoConfig;

@Controller
@Scope("prototype")
public class LogUserRepository {
	@Autowired
	MongoConfig mongo;
	MongoOperations mongoOperation;
	public List validateLogUser(String name) throws Exception{
		 List<LogUser> lg=new ArrayList<LogUser>();
		 mongoOperation =mongo.mongoTemplate();
		Query q=new Query();
	    q.addCriteria(Criteria.where("name").is(name));
	    User o=mongoOperation.findOne(q, User.class);
	    if(o==null){
		return null;
	    }
	    else{
	    	String id=o.getId();
	    	q=new Query();
	    	 q.addCriteria(Criteria.where("userid").is(id));
	    	 lg=mongoOperation.find(q, LogUser.class);
	    }
	  return lg;
	}
	public void saveLogUser(String index, String name) throws Exception {
		// TODO Auto-generated method stub
		 mongoOperation =mongo.mongoTemplate();
			Query q=new Query();
		    q.addCriteria(Criteria.where("name").is(name));
		    User o=mongoOperation.findOne(q, User.class);
		    String id=o.getId();
		    LogUser lu=new LogUser(index,id);
		    mongoOperation.save(lu);
	}
	
	public void deleteLogUser(String name) throws Exception {
		// TODO Auto-generated method stub
		 mongoOperation =mongo.mongoTemplate();
			Query q=new Query();
		    q.addCriteria(Criteria.where("name").is(name));
		    mongoOperation.remove(q,LogUser.class);

	}
	public static void main(String[] args) throws Exception{
		MongoConfig mongo =new MongoConfig();
		MongoOperations mongoOperation =mongo.mongoTemplate();
		Query q=new Query();
	    q.addCriteria(Criteria.where("logname").is("oo"));
	    mongoOperation.remove(q,LogUser.class);
	}
	

}
