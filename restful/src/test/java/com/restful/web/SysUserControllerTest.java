package com.restful.web;

import com.restful.BaseControllerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Slf4j
public class SysUserControllerTest extends BaseControllerTest {


    private MockMvc mockMvc;// 2

    @Autowired
    private WebApplicationContext webApplicationContext; // 3

    @Before
    public void before() {
        //获取mockmvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); // 4
    }


    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void index() throws Exception {
        /**
         * 正常访问 未携带参数
         */
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("httpStatus").exists())
                .andExpect(jsonPath("httpStatus").value("200"))
//                .andExpect(model().attributeExists("data"))
                .andReturn();
    }

    /**
     * TODO 这个没法测， 暂时没想出解决思路， 待定
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void current() throws Exception {
        /**
         * 正常访问
         */
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/current"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"httpStatus\":"+ HttpStatus.UNAUTHORIZED.value() +",\"msg\":\"未找到当前登录人\"}")))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void details() throws Exception {
        /**
         * 正常访问
         */
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("httpStatus").value("200"))
                .andExpect(jsonPath("data.username").value("demoUser1"))
                .andReturn();
    }

    @Test
    public void update() {
    }

    @Test
    public void create() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void saveRoles() {
    }

    @Test
    public void validUserNameUsed() {
    }
}
