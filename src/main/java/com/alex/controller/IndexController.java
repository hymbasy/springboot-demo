package com.alex.controller;

import com.alex.model.User;
import com.alex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "alex");
        model.addAttribute("msg", "springboot 第一个页面");
        return "/index";
    }

    @ResponseBody
    @RequestMapping("/getUser")
    @Cacheable(value = "user", unless="#result == null")
    public User getUser() {
        User user = userService.getUserByName("aa");
        System.out.println("若是从缓存中直接查询,不显示此信息");
        return user;
    }

    /**
     * session共享
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
