package com.flight.core.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@Scope("prototype")
public class ESAggsAction {
	private String aggsName;
	private aggsType type;
	private String termsAim;
	private JSONObject Agg = new JSONObject();

	public static enum aggsType {
		common(""), terms("terms");
		private String input;

		aggsType(String input) {
			this.input = input;
		}
		public aggsType setType(String type) {
			this.input=type;
			return this;
		}
		public String getType() {
			return input;
		}
	}

	public ESAggsAction() {

	}

	public void setAggsName(String name) {
		this.aggsName = name;
	}

	public JSONObject getJSONObject() {
		JSONObject aggs = new JSONObject();
		JSONObject object = new JSONObject();
		object.put("field", this.termsAim);
		aggs.put(type.getType(), object);
		Agg.put(this.aggsName, aggs);
		return Agg;
	}

	public void setTermsAim(String name) {
		termsAim = name;
	}

	public JSONObject getAggsBody() {
		JSONObject object = new JSONObject();
		object.put("aggs", getJSONObject());
		return object;
	}

	public aggsType getAggsType() {
		return type;
	}

	public void setAggsType(aggsType aggsType) {
		this.type = aggsType;
	}

	public static void main(String[] args){
    	ESAggsAction es=new ESAggsAction();
    	
    	es.setTermsAim("_index");
    	es.setAggsName("main");
    	es.setAggsType(es.type.terms);
    	System.out.print(es.getAggsBody().toJSONString());
    }

	public void parseQueryAggs(JSONArray jsonArray) {
		// TODO Auto-generated method stub
		JSONObject object;
		for(int i=0;i<jsonArray.size();i++){
			object=jsonArray.getJSONObject(i);
			this.setAggsName("main");
			this.setTermsAim(object.getString("name"));
			if(object.getString("type").equals("count")){
				this.setAggsType(aggsType.terms);
			}
			else{
				this.setAggsType(aggsType.common.setType(object.getString("type")));
			}
		}
		
	}
}
