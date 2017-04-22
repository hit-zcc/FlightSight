package com.flight.core.gather.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class mainService {
	@RequestMapping(value="/",method=RequestMethod.GET)
    public String dispatchTest()
    {
        System.out.println("Enter MainController");
        return "index";
    }
//	@RequestMapping(value="/test",method=RequestMethod.GET)
//    public String dispatchTest()
//    {
//        System.out.println("Enter MainController");
//        return "index";
//    }
}
