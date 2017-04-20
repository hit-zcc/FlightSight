package com.flight.core.action;

import com.flight.util.StringUtil;

public class LogInputAction {
	private InputType Input=null;
	private String path;
	private String fileds;
	private String tags;
	public static enum  InputType{
		File("file");
		private String input;
		InputType(String input){
			this.input=input;
		}
		public String getType(){
			return input;
		}
	}
	LogInputAction(InputType e){
		switch(e){
			case File:Input=InputType.File;break;
			default :Input=InputType.File;break;
		}
	}
	public String path(String... path){
		String begin="path =>[";
		String after="]";
		String patent="";
		for(String pathname:path){
			patent+="\""+pathname+"\",";
		}
		patent=StringUtil.removeLastComma(patent);
		this.path=begin.concat(patent).concat(after);
		return this.path;
		
	}
	public String addFileds(String filedsname,String filed){
		String begin=" add_field => {";
		String after="}";
		String patent="\""+filedsname+"\"=>\""+filed+"\"";
		this.fileds=begin.concat(patent).concat(after);
		return this.fileds;
	}
	public String addtags(String... tags){
		String begin=" tags => ";
		String patent="";
		for(String tag:tags){
			patent+="\""+tag+"\",";
		}
		patent=StringUtil.removeLastComma(patent);
		this.tags=begin.concat(patent);
		return this.tags;
		
	}
	public String getInputContent(){
		String begin="input"+" {"+Input.getType()+" {";		
		return addStaticString(begin).concat(this.getPath()).concat(this.getFileds()).concat(this.getTags()).concat("}}");
	}
	public String addStaticString(String s){
		return s.concat(" start_position => beginning ");
	}
	public InputType getInputType(){
		return Input;
	}
	public static void main(String[] args){
		LogInputAction i=new LogInputAction(InputType.File);
		i.addFileds("ff", "filed");
		i.addtags("error");
		i.path("/usr");
		System.out.print(i.getInputContent());
		
	}
	public InputType getInput() {
		return Input;
	}
	public void setInput(InputType input) {
		Input = input;
	}
	public String getPath() {
		return path;
	}
	public String getFileds() {
		return fileds;
	}
	public String getTags() {
		return tags;
	}

}