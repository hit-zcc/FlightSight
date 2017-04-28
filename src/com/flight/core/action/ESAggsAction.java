package com.flight.core.action;



import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
@Component
public class ESAggsAction {
    String termsName;
    JSONObject Agg=new JSONObject();
    public ESAggsAction(){
    	
    }
    public void setTermsName(String name){
    	this.termsName=name;
    }
    public class termsAggs extends AbstractESAction{
        String termsAim;
        JSONObject terms=new JSONObject();
        public termsAggs(){
        	
        }
        public void setTermsAim(String name){
        	termsAim=name;
        }
        public JSONObject getJSONObject(){
        	JSONObject object=new JSONObject();
        	object.put("field", this.termsAim);
        	terms.put("terms", object);
			return terms;
        }
        
    }
    public void addAggsMethod(AbstractESAction aggs){
    	Agg.put(this.termsName, aggs.getJSONObject());
    }
    public JSONObject getAggsBody(){
    	JSONObject object=new JSONObject();
    	object.put("aggs", Agg);
    	return object;
    }
    public static void main(String[] args){
    	ESAggsAction es=new ESAggsAction();
    	ESAggsAction.termsAggs term=new ESAggsAction().new termsAggs();
    	term.setTermsAim("_index");
    	es.setTermsName("main");
    	es.addAggsMethod(term);
    	System.out.print(es.getAggsBody().toJSONString());
    }
}
