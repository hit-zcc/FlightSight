package com.flight.core.gather.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/main")
public class mainService {
	@RequestMapping
    public String dispatchTest()
    {
        System.out.println("Enter MainController");
        return "test";
    }
}
