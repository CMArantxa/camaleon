package com.ceica.securityspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/nosotros")
    public String nosotros(){
        return "nosotros";
    }
    @GetMapping("/a360")
    public String a360(){
        return "a360";
    }
}

