package com.flight.core.gather.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="ChartUser")
public class ChartUser {
	@Id
	  private String id; 
	 @Field
	  private String xdata; 
	 @Field
	  private String ydata; 
	 @Field
	  private String chartname; 

	@Field
	  private String userid;
	public ChartUser(String xdata,String ydata,String chartname,String userid){
		this.xdata=xdata;
		this.ydata=ydata;
		this.chartname=chartname;
		this.userid=userid;
	}
	  public  String getChartname() {
		return chartname;
	}
	public String getId() {
		return id;
	}
	public String getYdata() {
		return ydata;
	}
	public String getXdata() {
		return xdata;
	}
	public String getUserid() {
		return userid;
	}

}
