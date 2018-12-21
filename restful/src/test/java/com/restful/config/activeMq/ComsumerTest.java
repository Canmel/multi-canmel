package com.restful.config.activeMq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@WebAppConfiguration
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class ComsumerTest {

    @Autowired
    private Comsumer comsumer;

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void receiveMsg() {
    }
}