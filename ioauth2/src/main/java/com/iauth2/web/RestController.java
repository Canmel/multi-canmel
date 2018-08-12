package com.iauth2.web;

import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @RequestMapping("/abc")
    public String index(){
        return "Hello abc";
    }
}
