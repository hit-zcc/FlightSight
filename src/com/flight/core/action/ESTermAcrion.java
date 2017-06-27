package com.flight.core.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@Scope("prototype")
public class ESTermAcrion extends AbstractESAction {
	private JSONArray term=new JSONArray();
	private Map<String,String> relationMap=new LinkedHashMap<String,String>();
	public static String[] NUMBERTYPE={"gte","gt","lte","lt"};
	
	
	public Map<String,JSONArray> splitGroup(){
		Map<String,JSONArray> map=new HashMap<String,JSONArray>();
		JSONObject b;
		JSONArray a;
		for(int i=0;i<term.size();i++){
			b=term.getJSONObject(i);
			if(map.containsKey(b.getString("group"))){
				a=map.get(b.getString("group"));
				a.add(b);
				map.put(b.getString("group"), a);
				relationMap.put(b.getString("group"), b.getString("relation"));
			}
			else{
				JSONArray array=new JSONArray();
				array.add(b);
				map.put(b.getString("group"), array);
				relationMap.put(b.getString("group"), b.getString("relation"));
			}
			
		}
		return map;
		
	}
	
	public JSONObject combine(List<String> relation,JSONArray array){
		if(array.size()==0){
			return new JSONObject();
		}
		JSONObject enbody=new JSONObject();
		JSONObject bool=new JSONObject();
		
		JSONObject tmpbefore=new JSONObject();
		JSONObject tmpafter=new JSONObject();
		tmpbefore=array.getJSONObject(0);
		String re=relation.get(0);
		if(array.size()==1){
			bool.put("should", tmpbefore);
			enbody.put("bool", bool);
			return enbody;
		}
	
		for(int i=1;i<array.size();i++){
			JSONArray jsonarray=new JSONArray();
			tmpafter=array.getJSONObject(i);
			jsonarray.add(tmpbefore);
			jsonarray.add(tmpafter);
			bool.put(re, jsonarray);
			enbody.put("bool", bool);
			tmpbefore=enbody;
		}
		return tmpbefore;
		
	}
	public JSONObject parseTermEntity(JSONArray jarray){
		JSONObject enbody=new JSONObject();
		JSONObject bool=new JSONObject();
		JSONArray relation=new JSONArray();
		List<String> relationKey=new ArrayList<String>();
		JSONObject tmp;
		for(int i=0;i<jarray.size();i++){
			JSONObject type=new JSONObject();
			JSONObject en=new JSONObject();
			
			
			JSONObject range=new JSONObject();
			JSONObject should=new JSONObject();
			tmp=jarray.getJSONObject(i);
		
			relationKey.add(tmp.getString("relation"));
		    if(isNumberType(tmp.getString("type")))	{
		    	type.put(tmp.getString("type"),tmp.getDouble("_value"));
		    	en.put(tmp.getString("name"), type);
		    	range.put("range", en);
		    	relation.add(range);
		    }
		    else{
			type.put(tmp.getString("name"), tmp.getString("_value"));
			en.put(tmp.getString("type"), type);
			relation.add(en);
		    }
		}
		
		
		return combine(relationKey,relation);
		
	}
	public boolean isNumberType(String type){
	    for(String s:NUMBERTYPE){
	    	if(s.equals(type)){
	    		return true;
	    	}
	    }
	    return false;
		
	}
	
	public JSONObject parseTermString(){
		JSONArray arry=new JSONArray();
		List<String> list=new ArrayList<String>();
		Map<String,JSONArray> map=splitGroup();
		JSONObject enbody=new JSONObject();
		String relationKey;
		for(String key:map.keySet()){
			enbody=parseTermEntity(map.get(key));
			arry.add(enbody);
			list.add(relationMap.get(key));
		}
		return combine(list,arry);
		
	}
	
//	{
//		  "query": {
//		    "bool": {
//		      "filter": {
//		        "bool": {
//		          "should": [
//		            {
//		              "bool": {
//		                "should": [
//		                  {
//		                    "match": {
//		                      "FREIGHT": "140"
//		                    }
//		                  },
//		                  {
//		                    "match": {
//		                      "FREIGHT": "141"
//		                    }
//		                  }
//		                ]
//		              }
//		            },
//		            {
//		              "bool": {
//		                "should": [
//		                  {
//		                    "match": {
//		                      "FREIGHT": "140"
//		                    }
//		                  },
//		                  {
//		                    "match": {
//		                      "FREIGHT": "141"
//		                    }
//		                  }
//		                ]
//		              }
//		            }
//		          ]
//		        }
//		      }
//		    }
//		  }
//		}

	public JSONArray getTerm() {
		return term;
	}

	public void setTerm(JSONArray term) {
		this.term = term;
	}
	
	
	public static void main(String[] args){
		JSONArray a= JSONArray.parseArray("[{\"name\":\"UNIQUE_CARRIER_ENTITY\",\"type\":\"gt\",\"_value\":\"111\",\"relation\":\"should\",\"group\":\"0\"},{\"name\":\"UNIQUE_CARRIER_ENTITY\",\"type\":\"match\",\"_value\":\"dfdf\",\"relation\":\"should\",\"group\":\"0\"}]");
		ESTermAcrion e=new ESTermAcrion();
		e.setTerm(a);
		JSONObject s=e.parseTermString();
		System.out.print(s);
		
	}
//	{
//		  "query": {
//		    "bool": {
//		      "filter": 
	@Override
	public JSONObject getJSONObject() {
		// TODO Auto-generated method stub
		JSONObject body=new JSONObject();
		JSONObject bool=new JSONObject();
		JSONObject query=new JSONObject();
		
		JSONObject object=this.parseTermString();
		System.out.println(object);
		bool.put("filter", object);
		 query.put("bool", bool);
		 body.put("query", query);
		return body;
	}
	
	

}
