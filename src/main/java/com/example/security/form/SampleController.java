package com.example.security.form;

import com.example.security.account.AccountContext;
import com.example.security.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SampleController {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountRepository accountRepository;

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

        // spring security contextHolder는 ThreadLocal을 통해서 공유된다
        // 다른 브라우저에서 다른 계정 로그인 시에, 다른 로그 출력
        // admin 로그인 -> admin, rojae 로그인 -> rojae
        AccountContext.setAccountThreadLocal(accountRepository.findByUsername(principal.getName()));
        sampleService.dashboard();

        return "dashboard";
    }

    // After Login
    @GetMapping("/admin")
    public String admin(Model model, Principal principal){
        model.addAttribute("message", "Welcome Admin" + principal.getName());
        return "admin";
    }
}
