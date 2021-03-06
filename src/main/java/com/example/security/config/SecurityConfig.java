package com.example.security.config;

import com.example.security.account.AccountService;
import com.example.security.common.LoggerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    // Voter's expression handler custom
    public SecurityExpressionHandler accessDecisionManager() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        return handler;
    }


    // for test spring security filter size = 0 (IN FilterChainProxy)
    // more speedy

    // webSecurity permitAll보다 빠르다.
    // 모든 필터를 거치며
    // permitAll (허용)은 SecurityFilterInterceptor (마지막 필터)를 거치기 때문에
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.ignoring().requestMatchers(PathRequest.toH2Console());
        //web.ignoring().regexMatchers("");
        //web.ignoring().mvcMatchers("/info");
        //web.ignoring().mvcMatchers("/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new LoggerFilter() , WebAsyncManagerIntegrationFilter.class);
        http.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**", "/signup", "/error").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .mvcMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
                .expressionHandler(accessDecisionManager());
        http.formLogin()
                .loginPage("/login")
                .permitAll();

        http.httpBasic();

        http.logout()
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");

        // 403 forbid
        http.exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    String username = principal.getUsername();
                    System.out.println(username + " is denied to access (" + request.getRequestURI() +")");
                    response.sendRedirect("/access-denied");
                });

        http.rememberMe()
                //.tokenValiditySeconds()   쿠키 만료 일자
                //.rememberMeParameter()  html 파라미터
                //.useSecureCookie() https만 접근 가능하도록 설계
                //.alwaysRemember() form에서 remember-me 버튼을 만들지 않아도 항상 로그인 기억 동작
                .userDetailsService(accountService)
                .key("remember-me-sample");



        // session security strategy
        http.sessionManagement().sessionFixation()
                    .changeSessionId().invalidSessionUrl("/error")
                    .maximumSessions(1).expiredUrl("/login").maxSessionsPreventsLogin(false);



        // Security Strategy : local thread -> change to share child thread
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    // security config
    // 명시적으로 service type 알려주기
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService);
    }

    // method security에서 authenticationManager를 bean으로 접근 가능하도록
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }
}
