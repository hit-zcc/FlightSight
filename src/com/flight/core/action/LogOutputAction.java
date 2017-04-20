package com.flight.core.action;

import com.flight.util.StringUtil;

public class LogOutputAction {
	private OutputType output=null;
	private String hosts;
	private String index;

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
		String begin=" index => {";
		String after="}";
		this.index=begin.concat("\""+index+"\"").concat(after);
		return this.index;
	}

	public String getInputContent(){
		String begin="output"+" {"+output.getType()+" {";
		return begin.concat(this.getHosts()).concat(this.getIndex()).concat("}}");
	}

	public static void main(String[] args){
		LogOutputAction i=new LogOutputAction(OutputType.elasticsearch);
       i.hosts("loclhost:8080");
       i.index("c");
		System.out.print(i.getInputContent());
		
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