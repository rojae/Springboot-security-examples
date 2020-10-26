package com.example.security.form;

import com.example.security.account.Account;
import com.example.security.account.AccountContext;
import com.example.security.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SampleService {

    // spring security context holder의 기본적인 전략
    // Thread holder에 대한 테스트
    // 여러 사용자가 로그인을 한 경우에 서로 다른 사용자 이름을 뽑아냄
    // Thread Local 전략을 사용하기 때문이다
    // 유저마다 가지는 쓰레드는 서로 다름
    public void dashboard(){
        Account account = AccountContext.getAccountThreadLocal();
        System.out.println("==================================");
        System.out.println(account.getUsername());
    }

    // new Thread for async
    @Async
    public void asyncService(){
        SecurityLogger.log("Async Service");
        System.out.println("Async service is called");
    }

}
