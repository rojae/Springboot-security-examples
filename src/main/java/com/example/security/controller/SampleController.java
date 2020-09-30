package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SampleController {

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if(principal == null){
            model.addAttribute("message", "Hello Spring Security");
        }else{
            model.addAttribute("message", "Hello" + principal.getName());
        }
        return "index";
    }


    @GetMapping("/info")
    public String info(Model model){
        model.addAttribute("message", "Welcome Info page");
        return "info";
    }

    // After Login
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("message", "Welcome"+principal.getName());
        return "dashboard";
    }

    // After Login
    @GetMapping("/admin")
    public String admin(Model model, Principal principal){
        model.addAttribute("message", "Welcome Admin" + principal.getName());
        return "admin";
    }
}
