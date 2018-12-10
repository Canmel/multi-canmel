package com.restful;

import com.alibaba.fastjson.JSONObject;
import com.core.entity.ErrorResult;
import com.core.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebAppConfiguration
@Slf4j
public class BaseControllerTest {

    public static final String HTTPSTATUS = "httpStatus";
    public static final String MSG = "msg";
    public static final String DATA = "data";

    public MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void before() {
        //获取mockmvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public Result successResult(){
        return Result.OK("");
    }

    public ErrorResult expectationFailedResult(){
        return ErrorResult.EXPECTATION_FAILED();
    }

    public String contentFromMap(Map map){
        return JSONObject.toJSONString(map);
    }

}
