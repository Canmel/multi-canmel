package com.restful.web;

import com.restful.BaseControllerTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class SysLogControllerTest extends BaseControllerTest {

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/logs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(HTTPSTATUS).exists())
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andExpect(jsonPath("data.records").isArray())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void details() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/logs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andExpect(jsonPath("data.title").value("分页查询菜单信息"))
                .andReturn();
    }
}