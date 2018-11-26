package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.annotation.SaveLog;
import com.restful.entity.SysRole;
import com.restful.entity.SysRoleMenu;
import com.restful.service.SysRoleMenuService;
import com.restful.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author *
 *  ┏ ┓   ┏ ┓
 * ┏┛ ┻━━━┛ ┻┓
 * ┃         ┃
 * ┃    ━    ┃
 * ┃  ┳┛  ┗┳ ┃
 * ┃         ┃
 * ┃    ┻    ┃
 * ┃         ┃
 * ┗━━┓    ┏━┛
 *    ┃    ┃神兽保佑
 *    ┃    ┃代码无BUG！
 *    ┃    ┗━━━━━━━┓
 *    ┃            ┣┓
 *    ┃            ┏┛
 *    ┗┓┓┏━━━━━━┳┓┏┛
 *     ┃┫┫      ┃┫┫
 *     ┗┻┛      ┗┻┛
 * @since 2018-08-12
 */
@RestController
@Api(value = "角色接口", description = "角色接口")
@RequestMapping("/api/roles")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * param:
     * sysRole： 角色查询条件
     * describe: 分页查询角色信息
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @GetMapping()
    @ApiOperation(value = "分页查询角色信息", notes = "分页查询角色信息")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult index(SysRole sysRole) {
        EntityWrapper<SysRole> sysRoleEntityWrapper = new EntityWrapper<SysRole>();
        sysRoleEntityWrapper.like("rolename", sysRole.getRolename()).like("description", sysRole.getDescription());
        Page<SysRole> rolePage = new Page<SysRole>(sysRole.getCurrentPage(), 10);
        return Result.OK(sysRoleService.selectPage(rolePage, sysRoleEntityWrapper));
    }

    /**
     * param:
     * id： 角色 ID
     * describe: 根据角色ID查询角色详细信息
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @GetMapping("/{id}")
    @SaveLog(title = "修改角色", value = "根据角色ID修改角色")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult details(@PathVariable Integer id) {
        SysRole role = sysRoleService.selectRoleDetails(id);
        return Result.OK(role);
    }

    /**
     * param:
     * role: 需要更新的角色信息
     * id: 角色id
     * describe: 根据角色ID更新角色信息
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult update(@RequestBody SysRole role, @PathVariable Integer id) {
        if (sysRoleService.updateById(role.addId(id))) {
            return Result.OK("修改角色成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    /**
     * param:
     * id: 角色ID
     * describe: 根据ID删除角色
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult delete(HttpServletRequest request, @PathVariable Integer id) {
        if (sysRoleService.deleteById(id)) {
            return Result.OK("删除角色成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    /**
     * param:
     * role: 角色详细信息
     * describe: 创建角色
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult create(@RequestBody SysRole role) {
        if (role.insert()) {
            return Result.OK("角色创建成功");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    /**
     * param:
     * sysrole： 角色信息，包括包含的菜单信息
     * describe: 更新保存角色的菜单
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @PostMapping("/menus")
    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult saveMenus(@RequestBody SysRole sysRole) {
        List list = new ArrayList();
        for (Integer id : sysRole.getMenuIds()) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setMenuId(id);
            roleMenu.setRoleId(sysRole.getId());
            list.add(roleMenu);
        }
        EntityWrapper<SysRoleMenu> sysRoleMenuEntityWrapper = new EntityWrapper<SysRoleMenu>();
        sysRoleMenuEntityWrapper.eq("role_id", sysRole.getId());
        sysRoleMenuService.delete(sysRoleMenuEntityWrapper);
        if (sysRoleMenuService.insertBatch(list)) {
            return Result.OK("分配菜单成功");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    /**
     * param:
     * <p>
     * describe: 查询所有角色
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult all() {
        List<SysRole> sysRoleList = sysRoleService.selectList(new EntityWrapper<>());
        return Result.OK(sysRoleList);
    }

    @ApiOperation(value = "验证角色名称是否可用", notes = "验证角色名是否可用")
    @PostMapping("/name/valid")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult validRoleNameUsed(@RequestBody SysRole sysRole) {
        Map<String, Object> map = new HashMap<>();
        map.put("rolename", sysRole.getRolename());
        List<SysRole> roles = sysRoleService.selectByMap(map);
        if (roles.size() > 1) {
            return ErrorResult.EXPECTATION_FAILED("角色名已存在");
        }
        if (roles.size() == 0) {
            return Result.OK("角色名可用");
        }
        SysRole role = roles.get(0);
        if (!ObjectUtils.isEmpty(sysRole.getId()) && role.getId().equals(sysRole.getId())) {
            return Result.OK("角色名可使用");
        } else {
            return ErrorResult.EXPECTATION_FAILED("角色名已存在");
        }
    }

}

