package com.iweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("access_token", session.getAttribute("access_token"));
        return "/users/index";
    }
}
