package com.flight.util;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.flight.core.action.ESAggsAction;
import com.flight.core.action.ESAggsAction.termsAggs;

@Component
public class HttpClientUtil {  
    private RequestConfig requestConfig = RequestConfig.custom()  
            .setSocketTimeout(15000)  
            .setConnectTimeout(15000)  
            .setConnectionRequestTimeout(15000)  
            .build();  
      
    private static HttpClientUtil instance = null;  
    private HttpClientUtil(){}  
    public static HttpClientUtil getInstance(){  
        if (instance == null) {  
            instance = new HttpClientUtil();  
        }  
        return instance;  
    }  
      
    /** 
     * 发送 post请求 
     * @param httpUrl 地址 
     */  
    public String sendHttpPost(String httpUrl) {  
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost    
        return sendHttpPost(httpPost);  
    }  
      
    /** 
     * 发送 post请求 
     * @param httpUrl 地址 
     * @param params 参数(格式:key1=value1&key2=value2) 
     */  
//    public String sendHttpPost(String httpUrl, String params) {  
//        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost    
//        try {  
//            //设置参数  
//            StringEntity stringEntity = new StringEntity(params, "UTF-8");  
//            stringEntity.setContentType("application/x-www-form-urlencoded");  
//            httpPost.setEntity(stringEntity);  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
//        return sendHttpPost(httpPost);  
//    }  
      
    /** 
     * 发送 post请求 
     * @param httpUrl 地址 
     * @param maps 参数 
     */  
    public String sendHttpPost(String httpUrl,JSONObject json) {  
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost    
        // 创建参数队列   
        
        try {  
        	StringEntity s = new StringEntity(json.toString(),"utf-8"); 
        	s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			s.setContentType("application/json");//发送json数据需要设置contentType
			httpPost.setEntity(s);
 
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return sendHttpPost(httpPost);  
    }  
      

    /** 
     * 发送Post请求 
     * @param httpPost 
     * @return 
     */  
    private String sendHttpPost(HttpPost httpPost) {  
        CloseableHttpClient httpClient = null;  
        CloseableHttpResponse response = null;  
        HttpEntity entity = null;  
        String responseContent = null;  
        try {  
            // 创建默认的httpClient实例.  
            httpClient = HttpClients.createDefault(); 
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            httpPost.setConfig(requestConfig);  
          
            // 执行请求  
            response = httpClient.execute(httpPost);  
            entity = response.getEntity();  
            responseContent = EntityUtils.toString(entity, "UTF-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                // 关闭连接,释放资源  
                if (response != null) {  
                    response.close();  
                }  
                if (httpClient != null) {  
                    httpClient.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return responseContent;  
    }  
  
    /** 
     * 发送 get请求 
     * @param httpUrl 
     */  
    public String sendHttpGet(String httpUrl) {  
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求  
        return sendHttpGet(httpGet);  
    }  
      
      
    /** 
     * 发送Get请求 
     * @param httpPost 
     * @return 
     */  
    private String sendHttpGet(HttpGet httpGet) {  
        CloseableHttpClient httpClient = null;  
        CloseableHttpResponse response = null;  
        HttpEntity entity = null;  
        String responseContent = null;  
        try {  
            // 创建默认的httpClient实例.  
            httpClient = HttpClients.createDefault();  
            httpGet.setConfig(requestConfig);  
            // 执行请求  
            response = httpClient.execute(httpGet);  
            entity = response.getEntity();  
            responseContent = EntityUtils.toString(entity, "UTF-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                // 关闭连接,释放资源  
                if (response != null) {  
                    response.close();  
                }  
                if (httpClient != null) {  
                    httpClient.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return responseContent;  
    }  
      

   public static void main(String[] args){
	   Map<String, String> maps = new HashMap<String, String>();  
   	ESAggsAction es=new ESAggsAction();
   	ESAggsAction.termsAggs term=new ESAggsAction().new termsAggs();
   	term.setTermsAim("_index");
   	es.setTermsName("main");
   	es.addAggsMethod(term);
   	System.out.print(es.getAggsBody().toJSONString());
       String responseContent = HttpClientUtil.getInstance()  
               .sendHttpPost("http://localhost:9200/_search/", es.getAggsBody());  
       JSONObject re=JSONObject.parseObject(responseContent);
       ParseResultUtil p=new ParseResultUtil();
       System.out.print(p.PareseTermAggs(re, "main"));
       System.out.println("reponse content:" + re.getString("buckets"));  
	   
   }
}  