package com.flight.core.gather.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flight.core.action.ESAggsAction;
import com.flight.core.action.LogFileManager;
import com.flight.core.action.LogInputAction;
import com.flight.core.gather.entity.ChartUserRepository;
import com.flight.core.gather.entity.LogUser;
import com.flight.core.gather.entity.LogUserRepository;
import com.flight.core.gather.entity.UserRespository;
import com.flight.core.action.ESTermAcrion;
import com.flight.util.HttpClientUtil;
import com.flight.util.ParseResultUtil;

@Controller
@Scope("prototype")
@RequestMapping(value = "/")
public class mainService {
	@Autowired
	ESAggsAction es;
	@Autowired
	ParseResultUtil parseutil;
	@Autowired
	ESTermAcrion esTermAction;
	@Autowired
	UserRespository userRespository;
	@Autowired
	LogUserRepository logUserRepository;
	@Autowired
	ChartUserRepository chartUserRepository;
	@RequestMapping(value="/main/initpic",method=RequestMethod.GET)
	@ResponseBody 
    public String initPic(@RequestParam("type") String type)
    {
		String body="";
		
		if(type.equals("chartpreferences")){
		es.setTermsAim("_index");
	   	es.setAggsName("main");
	   	es.setAggsType(es.getAggsType().terms);
	   	System.out.print(es.getAggsBody().toJSONString());
	    String responseContent = HttpClientUtil.getInstance()  
	               .sendHttpPost("http://localhost:9200/_search/", es.getAggsBody());  
	    JSONObject re=JSONObject.parseObject(responseContent);
	    Set result=parseutil.PareseTermAggs(re, "main");
	     body=result.toString();
		}else if (type.equals("chartHours")){
			es.setTermsAim("@timestamp");
		   	es.setAggsName("main");
		   	es.setAggsType(es.getAggsType().terms);
		   	System.out.print(es.getAggsBody().toJSONString());
		    String responseContent = HttpClientUtil.getInstance()  
		               .sendHttpPost("http://localhost:9200/_search/", es.getAggsBody());  
		    JSONObject re=JSONObject.parseObject(responseContent);
		    Set result=parseutil.PareseTermAggs(re, "main");
		     body=result.toString();
		}else if (type.equals("sharedsAndReplicas")){
		    String responseContent = HttpClientUtil.getInstance().
		    		sendHttpGet("http://localhost:9200/_settings/");  
		    JSONObject re=JSONObject.parseObject(responseContent);
		    List<JSONObject> result=parseutil.PareseSettingsQuery(re);
		    body=result.toString();
		}
		
        return body;
    }
	@RequestMapping(value="/main/userIndex",method=RequestMethod.GET)
	@ResponseBody 
	public String userIndex(HttpSession session) throws Exception{
		JSONObject u=(JSONObject) session.getAttribute("currUser");
		if(u==null){
			return "login failed";
		}
		List<LogUser> lu=logUserRepository.validateLogUser(u.getString("name"));
		JSONArray array=new JSONArray();
		for(LogUser l:lu){
		JSONObject LogUser=new JSONObject();
		LogUser.put("key",l.getLogname() );
		array.add(LogUser);
		 
		}
		return array.toString();
		
	}
	@RequestMapping(value="/",method=RequestMethod.GET)
    public String dispatchTest()
    {
        System.out.println("Enter MainController");
        return "index";
    }
	@RequestMapping(value="main/getMpping",method=RequestMethod.GET)
	@ResponseBody 
    public String dispatchTest(@RequestParam("index") String index)
    {
		String responseContent = HttpClientUtil.getInstance().
		    		sendHttpGet("http://localhost:9200/"+index+"/_mapping/");
		JSONObject re=JSONObject.parseObject(responseContent);
		JSONObject result=parseutil.PareseMappingQuery(re,index);
        return result.toString();
    }
	
	@RequestMapping(value="main/Chartsave",method=RequestMethod.POST)
	@ResponseBody 
    public String chartsave(@RequestBody JSONObject obj,HttpSession session) throws Exception{
    JSONObject u=(JSONObject) session.getAttribute("currUser");
	if(u==null){
		return "login failed";
	}
	String xdata=obj.getString("xdata");
	String ydata=obj.getString("ydata");
	String name=obj.getString("name");
	chartUserRepository.saveChartUser(u.getString("name"), xdata,ydata, name);
	
    return HttpStatus.OK.toString();
    }
	@RequestMapping(value="main/Bodysearch",method=RequestMethod.POST)
	@ResponseBody
	public String FormSubmit(@RequestBody JSONObject obj){
		esTermAction.setTerm(obj.getJSONArray("term"));
		JSONObject object=esTermAction.getJSONObject();
		String index=obj.getString("index");
		if(obj.getJSONArray("bucket").size()==0){
			es.setTermsAim("@timestamp");
		   	es.setAggsName("main");
		   	es.setAggsType(es.getAggsType().terms);
		}
		else{
			es.parseQueryAggs(obj.getJSONArray("bucket"));

		}
		JSONObject body=new JSONObject();
		body.put("query",  esTermAction.getJSONObject().getJSONObject("query"));
		body.put("size", 200);
		body.put("aggs",  es.getAggsBody().getJSONObject("aggs"));
		System.out.println(body);
		String responseContent = HttpClientUtil.getInstance()  
	               .sendHttpPost("http://localhost:9200/"+index+"/_search/", body); 
		JSONObject re=JSONObject.parseObject(responseContent);
		JSONArray termresult=parseutil.ParseTerm(re);
	    Set aggsresult=parseutil.PareseTermAggs(re, "main");
//		System.out.println(responseContent);
		JSONObject result=new JSONObject();
		result.put("termresult", termresult);
		result.put("aggsresult", aggsresult);
		result.put("total",re.getJSONObject("hits").get("total"));
		return result.toString();
		
	}
}


