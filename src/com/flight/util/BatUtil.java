package com.flight.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BatUtil {

	public static String ESDIR="F:/ENV/elasticsearch-2.3.0/bin/elasticsearch.bat";
	public static String LGDIR="F:/ENV/logstash-5.1.1/bin/logstash.bat";
	public static class RunES implements Runnable {
		 public String batName="";
		 public RunES(String InvokeBat){
			 this.batName=InvokeBat;
		 }
	    public void runbat(String batName) {
	        try {
	            Process ps = Runtime.getRuntime().exec(batName);
	            InputStream in = ps.getInputStream();
	            int c;
	            while ((c = in.read()) != -1) {
	                System.out.print((char)c);
	            }
	            in.close();
	            ps.waitFor();

	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        System.out.println("elasticsearch thread done");
	    }

		public void run() {
			// TODO Auto-generated method stub
			this.runbat(batName);
		}
	}
	
	public static class RunLogStashConf implements Runnable {
		String conf;
		String dir;
		public RunLogStashConf(String conf,String dir){
			this.conf=conf;
			this.dir=dir;
		}
		public void runbat(String conf,String dir) {
	        try {
	        	Runtime rt=Runtime.getRuntime(); 
	        	 Process pcs=rt.exec(dir+" -f  "+conf);
	        	 BufferedReader br = new BufferedReader(new InputStreamReader(pcs.getInputStream()));
	        	 String line=new String();
	        	  while((line = br.readLine()) != null)
	        	  {
	        	  System.out.println(line);
	   
	            }
	        	  br.close();
	            pcs.waitFor();


	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

		public void run() {
			// TODO Auto-generated method stub
			this.runbat(conf, dir);
		}
		
	}
}
