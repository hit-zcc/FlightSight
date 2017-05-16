package com.flight.core.init;

import java.io.IOException;
import java.io.InputStream;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.flight.util.BatUtil;


public class InitEnv  implements ServletContextListener {
	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub\
//        String batName = "F:/ENV/elasticsearch-2.3.0/bin/elasticsearch.bat";
//        BatUtil.RunES bat = new BatUtil.RunES(BatUtil.ESDIR);
//        Thread t = new Thread(bat);//创建线程
//        t.start();
		
	}
	

}
