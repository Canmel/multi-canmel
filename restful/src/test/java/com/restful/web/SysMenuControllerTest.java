package com.restful.web;

import com.core.entity.Result;
import com.restful.BaseControllerTest;
import com.restful.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class SysMenuControllerTest extends BaseControllerTest {

    @Autowired
    private SysMenuService menuService;

    /**
    * 正常访问
    */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/menus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(HTTPSTATUS).exists())
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andExpect(jsonPath("data.records").isArray())
                .andReturn();
    }

    /**
    * 正常访问
    */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void details() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/menus/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andExpect(jsonPath("data.menuname").value("系统管理"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void update() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "1");
        map.put("menuname", "召唤师");
        String content = contentFromMap(map);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/menus/" + map.get("id"))
                .param("menuname", map.get("menuname"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(jsonPath(MSG).value("修改菜单成功!"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andReturn();
        // 数据库验证
        Assert.assertEquals(menuService.selectById(map.get("id")).getMenuname(), map.get("menuname"));
    }


    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void create() throws Exception {
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void delete() throws Exception {
        Integer menuId = 2;
        // 数据库验证
        Assert.assertNotNull(menuService.selectById(menuId));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/menus/" + menuId))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("删除菜单成功!"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andReturn();
        // 数据库验证
        Assert.assertNull(menuService.selectById(menuId));
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void top() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/menus/tops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value(Result.SUCCESS_DEFAULT_MSG))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andExpect(jsonPath(DATA).isArray())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void subs() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/menus/subs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value(Result.SUCCESS_DEFAULT_MSG))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andExpect(jsonPath(DATA).isArray())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void validMenuNameUsed_MenuNameExist() throws Exception {
        Map<String, String> map = new HashMap();
        map.put("menuname", "系统管理");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/menus/name/valid").contentType(MediaType.APPLICATION_JSON).content(contentFromMap(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("菜单名已存在"))
                .andExpect(jsonPath(HTTPSTATUS).value(expectationFailedResult().httpStatus))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void validMenuNameUsed() throws Exception {
        Map<String, String> map = new HashMap();
        map.put("menuname", "未使用过的菜单名称");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/menus/name/valid").contentType(MediaType.APPLICATION_JSON).content(contentFromMap(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("菜单名可用"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andReturn();

    }
}