package com.flight.core.gather.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.flight.core.action.ESAggsAction.termsAggs;
import com.flight.util.HttpClientUtil;
import com.flight.util.ParseResultUtil;

@Controller
@RequestMapping(value = "/")
public class mainService {
	@Autowired
	ESAggsAction es;
	@Autowired
	ParseResultUtil parseutil;
	@RequestMapping(value="/main/initpic",method=RequestMethod.GET)
	@ResponseBody 
    public String initPic(@RequestParam("type") String type)
    {
		String body="";
		ESAggsAction.termsAggs term=es.new termsAggs();
		if(type.equals("chartpreferences")){
		term.setTermsAim("_index");
	   	es.setTermsName("main");
	   	es.addAggsMethod(term);
	   	System.out.print(es.getAggsBody().toJSONString());
	    String responseContent = HttpClientUtil.getInstance()  
	               .sendHttpPost("http://localhost:9200/_search/", es.getAggsBody());  
	    JSONObject re=JSONObject.parseObject(responseContent);
	    Set result=parseutil.PareseTermAggs(re, "main");
	     body=result.toString();
		}else if (type.equals("chartHours")){
			term.setTermsAim("@timestamp");
		   	es.setTermsName("main");
		   	es.addAggsMethod(term);
		   	System.out.print(es.getAggsBody().toJSONString());
		    String responseContent = HttpClientUtil.getInstance()  
		               .sendHttpPost("http://localhost:9200/_search/", es.getAggsBody());  
		    JSONObject re=JSONObject.parseObject(responseContent);
		    Set result=parseutil.PareseTermAggs(re, "main");
		     body=result.toString();
		}else if (type.equals("sharedsAndReplicas")){
			term.setTermsAim("@timestamp");
		   	es.setTermsName("main");
		   	es.addAggsMethod(term);
		   	System.out.print(es.getAggsBody().toJSONString());
		    String responseContent = HttpClientUtil.getInstance().
		    		sendHttpGet("http://localhost:9200/_settings/");  
		    JSONObject re=JSONObject.parseObject(responseContent);
		    List<JSONObject> result=parseutil.PareseSettingsQuery(re);
		    body=result.toString();
		}
		
        return body;
    }
	@RequestMapping(value="/",method=RequestMethod.GET)
    public String dispatchTest()
    {
        System.out.println("Enter MainController");
        return "index";
    }
}


