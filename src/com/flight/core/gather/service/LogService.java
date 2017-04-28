package com.flight.core.gather.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flight.core.action.LogFileManager;
import com.flight.core.action.LogFilterAction;
import com.flight.core.action.LogInputAction;
import com.flight.core.action.LogOutputAction;
import com.flight.util.BatUtil;

@Controller
@RequestMapping(value = "/log")
public class LogService {
	@Autowired
	LogFileManager logFileManager;
	@Autowired
	LogInputAction logInputAction;
	@Autowired
	LogFilterAction logFilterAction;
	@Autowired
	LogOutputAction logOutputAction;
	@RequestMapping(value="/test",method=RequestMethod.GET)
    public String test(@RequestParam(value="dir") String test)
    {
      return "index";
    }
	
	@RequestMapping(value="/test",method=RequestMethod.POST,consumes = "application/json")
	@ResponseBody
    public String test1(@RequestBody Map<String, String> map)
    {
		System.out.print(map.get("name"));
		logFileManager.setFileDir(map.get("name"));
		List<String> list=logFileManager.getLogFileLine(1);
		JSONArray json = new JSONArray();  
        json.addAll(list);
		return json.toJSONString();
    }
	
	@RequestMapping(value="/FormSubmit",method=RequestMethod.POST,consumes = "application/json")
	@ResponseBody
    public String FormSubmit(@RequestBody Map<String, Object> map)
    {
		System.out.print(map.get("table"));
		logInputAction.setInput(LogInputAction.InputType.File);
		logInputAction.addtags(map.get("Tags").toString());
		logInputAction.path(map.get("Dir").toString());
		System.out.println(logInputAction.getInputContent());
		logFilterAction.setFt(LogFilterAction.FilterType.Grok);
		logFilterAction.parseJsonToMap(JSON.parseObject(map.get("table").toString()));
		JSONObject json = JSON.parseObject(map.get("table").toString());  
		logOutputAction.setOutput(LogOutputAction.OutputType.elasticsearch);
		logOutputAction.hosts("localhost:9200");
		logOutputAction.index(map.get("Index").toString());
		String conf=logInputAction.getInputContent()+logFilterAction.getFilterContent()+logOutputAction.getOutContent();
		File file=new File("logstash.conf");
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(conf);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BatUtil.RunLogStashConf lc=new BatUtil.RunLogStashConf("logstash.conf",BatUtil.LGDIR);
		Thread t = new Thread(lc);//创建线程
	    t.start();
		return "xcx";
    }

	
}
