package com.flight.core.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.flight.util.StringUtil;
@Controller
@Scope("prototype")
public class LogFilterAction {
	ArrayList<FildeEntity> entityList=new ArrayList<FildeEntity>();
	private FilterType ft;


	public void setFt(FilterType ft) {
		this.ft = ft;
	}	
	LogFilterAction(){
		
	}
	public LogFilterAction(Map<FiedName,String> map,FilterType ty){
		switch(ty){
		case Grok:ft=FilterType.Grok;break;
		default :ft=FilterType.Grok;break;
	}
		 for(FiedName key:map.keySet()){
			 entityList.add(new FildeEntity(key,FiedType.getFiledTypeByString(map.get(key))));
		 }
		
	}

	public static enum  FilterType{
		Grok("grok");
		private String filter;
		FilterType(String filter){
			this.filter=filter;
		}
		public String getType(){
			return filter;
		}
	}
	public static enum  FiedName{
		EMPTY(""),FILEDNAME();
		private String filedname;
		FiedName(String filedname){
			this.filedname=filedname;
		}
		FiedName(){
		}
		public String getName(){
			return filedname;
		}
		public void setName(String filedname){
			this.filedname=filedname;
		}
		
	}
	public static enum  FiedType{
	    STRING(""),INT("Int"),FLOAT("Float");
		private String filedtype;
		FiedType(String filedtype){
			this.filedtype=filedtype;
		}
		public String getType(){
			return filedtype;
		}		
		public static FiedType getFiledTypeByString(String s){
			FiedType t;
			switch(s){
			case "Int":t=FiedType.INT;break;
			case "Float":t=FiedType.FLOAT;break;
			default :t=FiedType.STRING;break;
		}
			return t;
		}
	}
	public class FildeEntity{
		String en="%{DATA##}";
		private String name;
		private FiedType type;
		FildeEntity(FiedName name,FiedType type){
			this.name=name.getName();
			this.type=type;
		}
		public String getFiledEntityString(){
		    if(name!=""){
		    	en=en.replaceFirst("#", ":"+name);
		    }
		    else{
		    	en=en.replaceFirst("#", "");
		    }
		    
		    if(type!=FiedType.STRING){
		    	en=en.replaceFirst("#", ":"+type.getType());
		    }
		    else{
		    	en=en.replaceFirst("#", "");
		    }
		    return en;
		
	}
	}	
	public String getFilterContent(){
		String begin="filter {"+ft.getType() +"{match => {";
		String message="\"message\" =>\"";
		for(FildeEntity en:entityList){
			message=message.concat(en.getFiledEntityString()+",");
		}
		message=StringUtil.removeLastComma(message);
		return begin.concat(message+"\"").concat("}}}");
		
	}
	public FilterType getFt() {
		return ft;
	}
	public static void main(String[] args){
		Map<String,String> map =new HashMap<String,String>();
		map.put("zcc", "");
//		LogFilterAction l=new LogFilterAction(map,FilterType.Grok);
//		System.out.print(l.getFilterContent());
	}
	public void parseJsonToMap(JSONObject parseObject) {
		FiedName fn;
		for(int i=0;i<parseObject.keySet().size();i++){
			String contoent[] = parseObject.getString(i+"").split(",");
			if(contoent[2].equals("不分析")){
				fn=FiedName.EMPTY;
			}
			else{
				fn=FiedName.FILEDNAME;
				fn.setName(contoent[2]);
			}
			entityList.add(new FildeEntity(fn,FiedType.getFiledTypeByString(contoent[3])));

		}
	}

}
