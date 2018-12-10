package com.restful.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.restful.BaseControllerTest;
import com.restful.entity.SysRole;
import com.restful.entity.SysRoleMenu;
import com.restful.entity.SysUser;
import com.restful.entity.SysUserRole;
import com.restful.service.SysRoleMenuService;
import com.restful.service.SysRoleService;
import com.restful.service.SysUserRoleService;
import com.restful.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
public class SysRoleControllerTest extends BaseControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysRoleMenuService roleMenuService;

    @Before
    public void before() {
        //获取mockmvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
    * 正常访问
    */
    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(HTTPSTATUS).exists())
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
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
        map.put("id", "3");
        map.put("rolename", "召唤师");
        String content = contentFromMap(map);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/roles/" + map.get("id"))
                .param("rolename", map.get("rolename"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(jsonPath(MSG).value("修改角色成功!"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andReturn();
        // 数据库验证
        Assert.assertEquals(roleService.selectById(map.get("id")).getRolename(), map.get("rolename"));
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void delete() throws Exception {
        Integer roleId = 3;
        // 数据库验证
        Assert.assertNotNull(roleService.selectById(roleId));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/roles/" + roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("删除角色成功!"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andReturn();
        // 数据库验证
        Assert.assertNull(roleService.selectById(roleId));
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void create() throws Exception {
        String roleName = "超级兵";
        EntityWrapper<SysRole> entityWrapper = new EntityWrapper();
        entityWrapper.eq("rolename", roleName);
        // 数据库验证
        Assert.assertNull(roleService.selectOne(entityWrapper));

        Map<String, String> map = new HashMap<>();
        map.put("rolename", roleName);
        map.put("description", "这是一段描述信息");
        String content = contentFromMap(map);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("角色创建成功"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus));

        // 数据库验证
        Assert.assertNotNull(roleService.selectOne(entityWrapper));
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void saveMenus() throws Exception {
        Map<String, Object> map = new HashMap<>();
        Integer roleId = 24;
        map.put("id", roleId.toString());
        int[] x={1,2,3};
        map.put("menuIds", x);

        EntityWrapper<SysRoleMenu> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("role_id", roleId.toString());
        List<SysRoleMenu> roleMenus = roleMenuService.selectList(entityWrapper);

        // 数据库验证
        Assert.assertTrue(CollectionUtils.isEmpty(roleMenus));

        String content = contentFromMap(map);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/roles/menus").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("分配菜单成功"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andReturn();

        roleMenus = roleMenuService.selectList(entityWrapper);

        // 数据库验证
        Assert.assertFalse(CollectionUtils.isEmpty(roleMenus));
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void all() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/roles/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").isArray())
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void validRoleNameUsed() throws Exception {
        Map<String, String> map = new HashMap();
        map.put("rolename", "naverUse");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/roles/name/valid").contentType(MediaType.APPLICATION_JSON).content(contentFromMap(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("角色名可用"))
                .andExpect(jsonPath(HTTPSTATUS).value(successResult().httpStatus))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "demoUser1", roles = {"ADMIN"})
    public void validRoleNameUsed_RoleNameExist() throws Exception {
        Map<String, String> map = new HashMap();
        map.put("rolename", "manager");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/roles/name/valid").contentType(MediaType.APPLICATION_JSON).content(contentFromMap(map)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(MSG).value("角色名已存在"))
                .andExpect(jsonPath(HTTPSTATUS).value(expectationFailedResult().httpStatus))
                .andReturn();
    }
}