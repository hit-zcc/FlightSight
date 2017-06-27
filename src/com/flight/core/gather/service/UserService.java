package com.flight.core.gather.service;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flight.core.gather.entity.ChartUser;
import com.flight.core.gather.entity.ChartUserRepository;
import com.flight.core.gather.entity.LogUser;
import com.flight.core.gather.entity.LogUserRepository;
import com.flight.core.gather.entity.User;
import com.flight.core.gather.entity.UserRespository;
import com.flight.util.HttpClientUtil;
import com.flight.util.ParseResultUtil;
import com.flight.util.ResponseEntity;

@Controller
@Scope("prototype")
@RequestMapping(value = "/")
@SessionAttributes("currUser") 
public class UserService {
	@Autowired
	UserRespository userRespository;
	@Autowired
	LogUserRepository logUserRepository;
	@Autowired
	ResponseEntity entity;
	@Autowired
	ParseResultUtil parseutil;
	@Autowired
	ChartUserRepository chartUserRepository;
	@RequestMapping(value="/user/login",method=RequestMethod.GET)
	@ResponseBody 
    public String login(@RequestParam("name") String name,@RequestParam("password") String password,HttpSession session) throws Exception{

		User u=userRespository.validateUser(name, password);
		JSONObject user=new JSONObject();
		user.put("name", u.getName());
		HttpStatus status = u!=null?HttpStatus.OK:HttpStatus.NOT_FOUND;
		JSONObject en=entity.fill(status,user);
		session.setAttribute("currUser",user);
		return en.toJSONString();
		
	}
	@RequestMapping(value="/user/loginout",method=RequestMethod.GET)
	@ResponseBody 
    public String loginout(HttpSession session) throws Exception{

		Object o=session.getAttribute("currUser");
		HttpStatus status;
		if(o!=null){
			session.removeAttribute("currUser");
			 status=HttpStatus.OK;
		}
		else{
			 status=HttpStatus.NOT_FOUND;
		}
		JSONObject en=entity.fill(status,null);
		return en.toJSONString();
		
	}
	
	@RequestMapping(value="/user/auth",method=RequestMethod.GET)
	@ResponseBody 
    public String auth(HttpSession session) throws Exception{

		JSONObject o=(JSONObject) session.getAttribute("currUser");
		HttpStatus status;
		if(o!=null){
			 status=HttpStatus.OK;
		}
		else{
			 status=HttpStatus.NOT_FOUND;
		}
		JSONObject en=entity.fill(status,o);
		return en.toJSONString();
		
	}
	
	@RequestMapping(value="/user/register",method=RequestMethod.GET)
	@ResponseBody 
    public String register(@RequestParam("name") String name,@RequestParam("password") String password,HttpSession session) throws Exception{
		User u=userRespository.register(name, password);
		JSONObject user=new JSONObject();
		user.put("name", u.getName());
		HttpStatus status = u!=null?HttpStatus.OK:HttpStatus.NOT_FOUND;
		JSONObject en=entity.fill(status,user);
		return en.toJSONString();
		
	}
	@RequestMapping(value="/user/logUser",method=RequestMethod.GET)
	@ResponseBody 
    public String logUser(HttpSession session) throws Exception{
		JSONObject u=(JSONObject) session.getAttribute("currUser");
		if(u==null){
			return "login failed";
		}
		List<LogUser> lu=logUserRepository.validateLogUser(u.getString("name"));
		JSONArray array=new JSONArray();
		for(LogUser l:lu){
		JSONObject LogUser=new JSONObject();
		LogUser.put("Logname",l.getLogname() );
		LogUser.put("name",u.getString("name"));
		array.add(LogUser);
		 
		}
		HttpStatus status = lu!=null?HttpStatus.OK:HttpStatus.NOT_FOUND;
		JSONObject en=entity.fill(status,array);
		return en.toJSONString();
		
	}
	@RequestMapping(value="/user/IndexInfo",method=RequestMethod.GET)
	@ResponseBody 
    public String IndexInfo(@RequestParam("name") String name,HttpSession session) throws Exception{
	    String responseContent = HttpClientUtil.getInstance().
	    		sendHttpGet("http://localhost:9200/"+name+"/_settings/");  
	    JSONObject re=JSONObject.parseObject(responseContent);
	    JSONObject result=parseutil.PareseIndexInfoQuery(re);
	    String body=result.toString();
		return body;
	}
	@RequestMapping(value="/user/ChartMap",method=RequestMethod.GET,produces = "application/json; charset=utf-8") 
	@ResponseBody 
    public String IndexInfo(HttpSession session) throws Exception{
		JSONObject u=(JSONObject) session.getAttribute("currUser");
		if(u==null){
			return "login failed";
		}
		List<ChartUser> lsit=chartUserRepository.validateChartUser(u.getString("name"));
		JSONArray array=new JSONArray();
		for(ChartUser l:lsit){
		JSONObject LogUser=new JSONObject();
		LogUser.put("chartname",l.getChartname());
		LogUser.put("xdata",l.getXdata());
		LogUser.put("ydata",l.getYdata());
		array.add(LogUser);
		 
		}

	    String body=array.toString();
		return body;
	}
	@RequestMapping(value="/user/deleteLog",method=RequestMethod.GET)
	@ResponseBody 
	public String deleteLog(@RequestParam("index") String name,HttpSession session) throws Exception{
		String responseContent = HttpClientUtil.getInstance().sendHttpDelete("http://localhost:9200/"+name);  
		logUserRepository.deleteLogUser(name);
		return name;
		
	}

}
