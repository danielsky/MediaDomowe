package com.dskimina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * Created by daniel on 09.07.16.
 */
@Controller
public class MainController {

    @Autowired
    private MessageSource ms;


    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {

        model.put("time", new Date());
        model.put("msg", "Hello");
        return "index";
    }
}
