package com.flight.core.gather.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping(value = "/log")
public class LogService {
	@RequestMapping(value="/test",method=RequestMethod.GET)
    public String test(@RequestParam(value="dir") String test)
    {
      return "index";
    }
	
	@RequestMapping(value="/test",method=RequestMethod.POST,consumes = "application/json")
	@ResponseBody
    public String test1(@RequestBody Map<String, String> map)
    {
		JSONObject result=new JSONObject();
		System.out.print(map.get("name"));
		
		result.put("name","sdcdxcx" );
		return result.toJSONString();
    }

	
}
