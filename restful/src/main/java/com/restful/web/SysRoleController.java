package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.SysRole;
import com.restful.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @GetMapping()
    public HttpResult index(HttpServletRequest request, SysRole sysRole) {
        EntityWrapper<SysRole> sysRoleEntityWrapper = new EntityWrapper<SysRole>();
        sysRoleEntityWrapper.like("rolename", sysRole.getRolename()).like("description", sysRole.getDescription());
        Page<SysRole> rolePage = new Page<SysRole>(sysRole.getCurrentPage(), 10);
        return Result.OK(request, sysRoleService.selectPage(rolePage, sysRoleEntityWrapper));
    }

    @GetMapping("/{id}")
    public HttpResult details(@PathVariable Integer id) {
        SysRole role = sysRoleService.selectById(id);
        return Result.OK(role);
    }

    @PutMapping("/{id}")
    public HttpResult update(HttpServletRequest request, HttpServletResponse response, @RequestBody SysRole role, @PathVariable Integer id) {
        if (sysRoleService.updateById(role)) {
            return Result.OK(request, "修改角色成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    @DeleteMapping("/{id}")
    public HttpResult delete(HttpServletRequest request, @PathVariable Integer id) {
        if (sysRoleService.deleteById(id)) {
            return Result.OK(request, "删除角色成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    @PostMapping()
    public HttpResult create(HttpServletRequest request, @RequestBody SysRole role) {
        if (role.insert()) {
            return Result.OK(request, "角色创建成功");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }


}

