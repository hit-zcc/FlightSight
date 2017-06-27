package com.flight.util;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public  class ResponseEntity<T>{
	T t;
	HttpStatus status;
	public JSONObject fill(HttpStatus status,T t){
		this.t=t;
		this.status=status;
		JSONObject en=new JSONObject();
		en.put("content", t);
		en.put("status", status);
		return en;
	}
	
}