package com.restful;

import com.alibaba.fastjson.JSONObject;
import com.core.entity.ErrorResult;
import com.core.entity.Result;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
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
