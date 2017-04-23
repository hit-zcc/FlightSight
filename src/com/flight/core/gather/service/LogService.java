package com.flight.core.gather.service;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flight.core.action.LogFileManager;

@Controller
@RequestMapping(value = "/log")
public class LogService {
	@Autowired
	LogFileManager logFileManager;
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

	
}
