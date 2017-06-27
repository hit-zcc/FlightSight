package com.flight.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
@Component
public class ParseResultUtil {
	public ParseResultUtil(){
	}
	public Set<Object> PareseTermAggs(JSONObject object,String name){
		JSONArray array=object.getJSONObject("aggregations").getJSONObject(name).getJSONArray("buckets");
		LinkedHashSet<Object> result=new LinkedHashSet<Object>();
		for(int i=0;i<array.size();i++){
			result.add(array.get(i));
		}
		return result;
	}
	public List<JSONObject> PareseSettingsQuery(JSONObject object){
		List<JSONObject> list=new ArrayList<JSONObject>();
		JSONObject shared=new JSONObject();
		JSONObject replicas=new JSONObject();
		Iterator<String> iterator=object.keySet().iterator();
		while(iterator.hasNext()){
			
			String key=iterator.next();
			shared.put(key,
					object.getJSONObject(key).getJSONObject("settings").getJSONObject("index").getString("number_of_shards"));
			replicas.put(key,
					object.getJSONObject(key).getJSONObject("settings").getJSONObject("index").getString("number_of_replicas"));
		}
		list.add(shared);
		list.add(replicas);
		return list;
	}
	public JSONObject PareseIndexInfoQuery(JSONObject object){
		
		JSONObject info=new JSONObject();
		Iterator<String> iterator=object.keySet().iterator();
		while(iterator.hasNext()){
			
			String key=iterator.next();
			info.put(key,
					object.getJSONObject(key).getJSONObject("settings").getJSONObject("index"));
			}
		return info;
	}
	public JSONObject PareseMappingQuery(JSONObject re, String index) {
		// TODO Auto-generated method stub
		JSONObject type=re.getJSONObject(index).getJSONObject("mappings");
		Iterator<String> iterator=type.keySet().iterator();
		//暂时使用
//		String key=iterator.next();

		return type.getJSONObject("logs").getJSONObject("properties");
	}
	public JSONArray ParseTerm(JSONObject re){
		
		return re.getJSONObject("hits").getJSONArray("hits");
		
	}

}
