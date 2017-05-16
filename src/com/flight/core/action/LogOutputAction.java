package com.flight.core.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.flight.util.StringUtil;
@Controller
@Scope("prototype")
public class LogOutputAction {
	private OutputType output=null;
	public void setOutput(OutputType output) {
		this.output = output;
	}
	private String hosts;
	private String index;
	LogOutputAction(){
		
	}
	public static enum  OutputType{
		elasticsearch("elasticsearch");
		private String output;
		OutputType(String output){
			this.output=output;
		}
		public String getType(){
			return output;
		}
	}
	LogOutputAction(OutputType e){
		switch(e){
			case elasticsearch:output=OutputType.elasticsearch;break;
			default :output=OutputType.elasticsearch;break;
		}
	}
	public String hosts(String... hosts){
		String begin="hosts =>[";
		String after="]";
		String patent="";
		for(String host:hosts){
			patent+="\""+host+"\",";
		}
		patent=StringUtil.removeLastComma(patent);
		this.hosts=begin.concat(patent).concat(after);
		return this.hosts;
		
	}
	public String index(String index){
		String begin=" index => ";
		this.index=begin.concat("\""+index+"\"");
		return this.index;
	}

	public String getOutContent(){
		String begin="output"+" {"+output.getType()+" {";
		return begin.concat(this.getHosts()).concat(this.getIndex()).concat("}}");
	}

	public static void main(String[] args){
		LogOutputAction i=new LogOutputAction(OutputType.elasticsearch);
       i.hosts("loclhost:8080");
       i.index("c");
		System.out.print(i.getOutContent());
		
	}
	public OutputType getOutput() {
		return output;
	}
	public String getHosts() {
		return hosts;
	}
	public String getIndex() {
		return index;
	}


}