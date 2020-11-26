package com.example.security.form;

import com.example.security.account.Account;
import com.example.security.account.AccountContext;
import com.example.security.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;

@Service
public class SampleService {

    // spring security context holder의 기본적인 전략
    // Thread holder에 대한 테스트
    // 여러 사용자가 로그인을 한 경우에 서로 다른 사용자 이름을 뽑아냄
    // Thread Local 전략을 사용하기 때문이다
    // 유저마다 가지는 쓰레드는 서로 다름

    /*
        Security Method

        메소드 사용 이후에 검사
        @PostAuthorize("hasRole(USER)")

        메소드 사용 이전에 검사
        @RolesAllowed("ROLE_USER")
        @PreAuthorize("hasRole(USER)")
        @Secured("ROLE_USER")

        상세 정보는 api 참조
    */
    @Secured("ROLE_USER")
    public void dashboard(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("==================================");
        System.out.println(authentication);
        System.out.println(userDetails.getUsername());
    }

    // new Thread for async
    @Async
    public void asyncService(){
        SecurityLogger.log("Async Service");
        System.out.println("Async service is called");
    }

}
