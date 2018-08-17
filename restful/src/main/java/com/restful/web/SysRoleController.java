package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.SysRole;
import com.restful.entity.SysRoleMenu;
import com.restful.service.SysRoleMenuService;
import com.restful.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
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
 * @since 2018-08-12
 */
@RestController
@RequestMapping("/api/roles")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @GetMapping()
    public HttpResult index(SysRole sysRole) {
        EntityWrapper<SysRole> sysRoleEntityWrapper = new EntityWrapper<SysRole>();
        sysRoleEntityWrapper.like("rolename", sysRole.getRolename()).like("description", sysRole.getDescription());
        Page<SysRole> rolePage = new Page<SysRole>(sysRole.getCurrentPage(), 10);
        return Result.OK(sysRoleService.selectPage(rolePage, sysRoleEntityWrapper));
    }

    @GetMapping("/{id}")
    public HttpResult details(@PathVariable Integer id) {
        SysRole role = sysRoleService.selectRoleDetails(id);
        return Result.OK(role);
    }

    @PutMapping("/{id}")
    public HttpResult update(@RequestBody SysRole role, @PathVariable Integer id) {
        if (sysRoleService.updateById(role)) {
            return Result.OK("修改角色成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    @DeleteMapping("/{id}")
    public HttpResult delete(HttpServletRequest request, @PathVariable Integer id) {
        if (sysRoleService.deleteById(id)) {
            return Result.OK("删除角色成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    @PostMapping
    public HttpResult create(@RequestBody SysRole role) {
        if (role.insert()) {
            return Result.OK("角色创建成功");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    @PostMapping("/menus")
    @Transactional
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

    @GetMapping("/all")
    public HttpResult all(){
        List<SysRole> sysRoleList = sysRoleService.selectList(new EntityWrapper<>());
        return Result.OK(sysRoleList);
    }

}

