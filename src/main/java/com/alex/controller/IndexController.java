package com.alex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "alex");
        model.addAttribute("msg", "springboot 第一个页面");
        return "/index";
    }
}
