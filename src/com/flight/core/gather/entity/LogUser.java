package com.flight.core.gather.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection="LogUser")
public class LogUser {
	@Id
	  private String id; 
	 @Field
	  private String logname; 
	  @Field
	  private String userid;
	 public LogUser(String logname, String userid) {
		// TODO Auto-generated constructor stub
		 this.logname=logname;
		 this.userid=userid;
	}
	public String getId() {
		return id;
	}
	public String getLogname() {
		return logname;
	}
	public String getUserid() {
		return userid;
	}


}
