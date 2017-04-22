package com.flight.core.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.flight.util.StringUtil;

public class LogFilterAction {
	ArrayList<FildeEntity> entityList=new ArrayList<FildeEntity>();
	private FilterType ft;
	public LogFilterAction(Map<String,String> map,FilterType ty){
		switch(ty){
		case Grok:ft=FilterType.Grok;break;
		default :ft=FilterType.Grok;break;
	}
		 for(String key:map.keySet()){
			 entityList.add(new FildeEntity(key,FiedType.getFiledTypeByString(map.get(key))));
		 }
		
	}

	public static enum  FilterType{
		Grok("Grok");
		private String filter;
		FilterType(String filter){
			this.filter=filter;
		}
		public String getType(){
			return filter;
		}
	}
	public static enum  FiedType{
	    STRING(""),INT("int"),FLOAT("float");
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
			case "int":t=FiedType.INT;break;
			case "float":t=FiedType.FLOAT;break;
			default :t=FiedType.STRING;break;
		}
			return t;
		}
	}
	public class FildeEntity{
		String en="%{DATA##}";
		private String name;
		private FiedType type;
		FildeEntity(String name,FiedType type){
			this.name=name;
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
		LogFilterAction l=new LogFilterAction(map,FilterType.Grok);
		System.out.print(l.getFilterContent());
	}
}
