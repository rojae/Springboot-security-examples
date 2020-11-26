package com.example.security;

import com.example.security.account.Account;
import com.example.security.account.AccountService;
import com.example.security.account.WithAdmin;
import com.example.security.form.SampleService;
import javassist.tools.rmi.Sample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountService accountService;

    // ref ~ security config
    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    @WithAdmin
    public void dashboard(){
        // 사용자를 생성하지 않고
        // MockUser annotation을 사용하여 테스트 사용자 생성
      /*  Account account = new Account();
        account.setRole("USER");
        account.setUsername("rojae");
        account.setPassword("123");
        accountService.createNew(account);

        UserDetails userDetails = accountService.loadUserByUsername("rojae");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");

        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
*/
        sampleService.dashboard();
    }
}
