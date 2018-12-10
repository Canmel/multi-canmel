package com.restful.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.core.entity.Result;
import com.restful.BaseControllerTest;
import net.minidev.json.JSONUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

/**
 * <p>
 *  前端控制器测试
 * </p>
 *
 * @author  *
 *   ┏ ┓   ┏ ┓
 *  ┏┛ ┻━━━┛ ┻┓
 *  ┃         ┃
 *  ┃    ━    ┃
 *  ┃  ┳┛  ┗┳ ┃
 *  ┃         ┃
 *  ┃    ┻    ┃
 *  ┃         ┃
 *  ┗━━┓    ┏━┛
 *     ┃    ┃神兽保佑
 *     ┃    ┃代码无BUG！
 *     ┃    ┗━━━━━━━┓
 *     ┃            ┣┓
 *     ┃            ┏┛
 *     ┗┓┓┏━━━━━━┳┓┏┛
 *      ┃┫┫      ┃┫┫
 *      ┗┻┛      ┗┻┛
 * @since 2018-08-17
 */

public class WorkFlowControllerTest extends BaseControllerTest {

    /**
     * 正常访问
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/workflow/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value(Result.SUCCESS_DEFAULT_MSG))
                .andExpect(jsonPath("data.records").isArray())
                .andReturn();
    }

    /**
     * 正常访问
     * 参数 name
     * @throws Exception
     */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void index_name() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/workflow/").param("name", "报销"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value(Result.SUCCESS_DEFAULT_MSG))
                .andExpect(jsonPath("data.records").isArray())
                .andReturn().getResponse().getContentAsString();

        JSONObject obj = JSONObject.parseObject(result);

        JSONArray jsonArray =obj.getJSONObject("data").getJSONArray("records");
        System.out.println(jsonArray);
        Assert.assertEquals(((JSONObject) jsonArray.get(0)).getString("name"), "报销.bpmn");
    }



    @Test
    public void create() {
    }

    @Test
    public void details() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void publish() {
    }

    @Test
    public void deployed() {
    }

    @Test
    public void taskImage() {
    }

    @Test
    public void pass() {
    }

    @Test
    public void back() {
    }

    @Test
    public void comments() {
    }
}