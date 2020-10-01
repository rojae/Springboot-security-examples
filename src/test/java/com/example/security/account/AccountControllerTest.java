package com.example.security.account;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    // default index page test
    @Test
    @WithAnonymousUser
    public void index_anonymous() throws Exception{
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // mocking to user test
    @Test
    @WithUser
    public void index_user() throws Exception{
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    // admin page test
    @Test
    @WithUser
    public void admin_user() throws Exception{
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    // admin page test
    @Test
    @WithAdmin
    public void admin_admin() throws Exception{
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

