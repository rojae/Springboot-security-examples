package com.example.security;

import com.example.security.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                    .mvcMatchers("/", "/info", "/account/**").permitAll()
                    .mvcMatchers("/admin").hasRole("ADMIN")
                    .anyRequest().permitAll();
        http.formLogin();

        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.httpBasic();
    }

    // security config
    // 명시적으로 service type 알려주기
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(accountService);
    }
}
