package com.restful.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.restful.BaseControllerTest;
import com.restful.entity.SysUser;
import com.restful.entity.SysUserRole;
import com.restful.service.SysUserRoleService;
import com.restful.service.SysUserService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.protocol.HTTP;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysUserControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext; // 3

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserRoleService userRoleService;

    /**
     * 正常访问 未携带参数
     */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(HTTPSTATUS).exists())
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
//                .andExpect(model().attributeExists("data"))
                .andReturn();
    }

    /**
     * TODO 这个没法测， 暂时没想出解决思路， 待定
     * @throws Exception
     */
    /**
     * 正常访问
     */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void current() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/current"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"httpStatus\":" + HttpStatus.UNAUTHORIZED.value() + ",\"msg\":\"未找到当前登录人\"}")))
                .andReturn();
    }

    /**
     * 正常访问
     */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void details() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andExpect(jsonPath("data.username").value("demoUser1"))
                .andReturn();
    }

    /**
     * 正常访问
     */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void update() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "2");
        map.put("username", "百里守约");
        String content = contentFromMap(map);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/" + map.get("id"))
                .param("username", map.get("username"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(jsonPath(MSG).value("修改用户成功!"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andReturn();
        // 数据库验证
        Assert.assertEquals(userService.selectById(map.get("id")).getUsername(), map.get("username"));

    }

    /**
     * 正常访问
     */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void create() throws Exception {
        String userName = "不知火舞";
        EntityWrapper<SysUser> entityWrapper = new EntityWrapper();
        entityWrapper.eq("username", userName);
        // 数据库验证
        Assert.assertNull(userService.selectOne(entityWrapper));

        Map<String, String> map = new HashMap<>();
        map.put("username", userName);
        map.put("password", "123456");
        map.put("nickname", "火火");
        map.put("email", "unknownFileWu@c7.com");
        map.put("mobile", "13900001111");
        map.put("remarke", "这是一段备注信息");
        map.put("address", "这是一段地址信息");
        String content = contentFromMap(map);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("新建用户成功"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus));

        // 数据库验证
        Assert.assertNotNull(userService.selectOne(entityWrapper));
    }

    /**
     * 正常访问 未携带参数
     */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void delete() throws Exception {
        Integer userId = 25;
        // 数据库验证
        Assert.assertNotNull(userService.selectById(userId));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("删除用户成功!"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andReturn();
        // 数据库验证
        Assert.assertNull(userService.selectById(userId));
    }

    /**
     * 正常访问
     */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void saveRoles() throws Exception {
        Map<String, Object> map = new HashMap<>();
        Integer userId = 24;
        map.put("id", userId.toString());
        int[] x={1,2,3};
        map.put("roleIds", x);

        EntityWrapper<SysUserRole> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_id", userId.toString());
        List<SysUserRole> userRoleList = userRoleService.selectList(entityWrapper);

        // 数据库验证
        Assert.assertTrue(CollectionUtils.isEmpty(userRoleList));

        String content = contentFromMap(map);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("分配角色成功"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andReturn();

        userRoleList = userRoleService.selectList(entityWrapper);

        // 数据库验证
        Assert.assertFalse(CollectionUtils.isEmpty(userRoleList));
    }

    /**
     * 正常访问
     */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void validUserNameUsed() throws Exception {
        Map<String, String> map = new HashMap();
        map.put("username", "娜可露露");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/name/valid").contentType(MediaType.APPLICATION_JSON).content(contentFromMap(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("用户名可用"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andReturn();
    }

    /**
     * 异常访问 - 用户名已存在
     */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void validUserNameUsed_UserNameExist() throws Exception {
        Map<String, String> map = new HashMap();
        map.put("username", "张小斐");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/name/valid").contentType(MediaType.APPLICATION_JSON).content(contentFromMap(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("用户名已存在"))
                .andExpect(jsonPath(HTTPSTATUS).value(expectationFailedResult().httpStatus))
                .andReturn();
    }
}
