package com.flight.core.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class LogFileManager {
	private String dir;
	FileReader fileReader=null;
	LogFileManager(){
		
	}
	public void setFileDir(String dir){
		this.dir=dir;
	}
	public List<String> getLogFileLine(int lineNum){
		List<String> result=new ArrayList<String>();
		try{
		fileReader=new FileReader(dir);
		BufferedReader bf= new BufferedReader(fileReader);
		String str;
		while ((str = bf.readLine()) != null) {
			if(lineNum==0){
				break;
			}
			else{
				result.add(str);
			}
			lineNum--;
		   }
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
		
	}
}
