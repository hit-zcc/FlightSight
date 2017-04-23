package com.flight.core.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class LogFileManager {
	private String dir="";
	public String getDir() {
		return dir;
	}

	FileReader fileReader=null;
	LogFileManager(){
		
	}
	public void setFileDir(String dir){
		this.dir=dir.replaceAll("\\\\", "/");
		System.out.println(this.dir);
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
//			e.printStackTrace();
		}
		return result;
		
	}
	public static void main(String[] args){
		LogFileManager l=new LogFileManager();
		System.out.print("C:\\fakepath\\3.txt".replaceAll("\\\\", "/"));
//		l.setFileDir("C:\fakepath\3.txt");
        System.out.print(l.getDir());
//		System.out.print(l.getLogFileLine(1).get(0));
		
	}
}
