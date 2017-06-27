package com.flight.core.gather.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;

import com.flight.core.gather.config.MongoConfig;

@Controller
@Scope("prototype")
public class ChartUserRepository {
	@Autowired
	MongoConfig mongo;
	MongoOperations mongoOperation;
	public void saveChartUser(String usernme,String xdata,String ydata,String chartname) throws Exception{
		 List<LogUser> lg=new ArrayList<LogUser>();
		 mongoOperation =mongo.mongoTemplate();
		Query q=new Query();
	    q.addCriteria(Criteria.where("name").is(usernme));
	    User o=mongoOperation.findOne(q, User.class);
	    if(o==null){
		return ;
	    }
	    else{
	    	String id=o.getId();
	    	ChartUser cu=new ChartUser( xdata, ydata,chartname,id);
	    	mongoOperation.save(cu);
	    }
	}
	public List<ChartUser> validateChartUser(String name) throws Exception{
		List<ChartUser> list=new ArrayList<ChartUser>();
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
		    	 list=mongoOperation.find(q, ChartUser.class);
		    }
		  return list;
		
	}

}
