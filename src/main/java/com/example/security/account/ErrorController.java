package com.example.security.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public ModelAndView errorPage(){
        ModelAndView mav =  new ModelAndView("/error");
        mav.addObject("message", "Sorry. Try again");
        return mav;
    }
}
