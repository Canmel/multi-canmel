package com.restful.web;

import com.core.entity.ErrorResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class AuthorizedController {

    @GetMapping("/401")
    public ErrorResult error401(){
        return ErrorResult.UNAUTHORIZED("401啊401");
    }
}
