package com.restful;

import com.alibaba.fastjson.JSONObject;
import com.core.entity.ErrorResult;
import com.core.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebAppConfiguration
@Slf4j
public class BaseControllerTest {

    public static String HTTPSTATUS = "httpStatus";
    public static String MSG = "msg";

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
