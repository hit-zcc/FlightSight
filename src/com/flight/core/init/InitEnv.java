package com.flight.core.init;

import java.io.IOException;
import java.io.InputStream;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class InitEnv  implements ServletContextListener {
	
	 class InvokeBat implements Runnable {
		 public String batName="";
		 InvokeBat(String InvokeBat){
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
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub\
        String batName = "F:/ENV/elasticsearch-2.3.0/bin/elasticsearch.bat";
        InvokeBat bat = new InvokeBat(batName);
        Thread t = new Thread(bat);//创建线程
        t.start();
		
	}
	

}
