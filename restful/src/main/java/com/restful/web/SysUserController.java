package com.restful.web;


import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.SysUser;
import com.restful.entity.SysUserRole;
import com.restful.service.SysUserRoleService;
import com.restful.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/api/users")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

   /**
    * param: 
    *   sysUser 用户
    * describe: （分页）查询用户列表
    * creat_user: baily
    * creat_date: 2018/8/17
    **/
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult index(HttpServletRequest request, SysUser sysUser, Authentication authentication) {
        EntityWrapper<SysUser> userEntityWrapper = new EntityWrapper<SysUser>();
        userEntityWrapper.like("username", sysUser.getUsername(), SqlLike.DEFAULT)
                .like("email", sysUser.getEmail(), SqlLike.DEFAULT);
        Page<SysUser> userPage = new Page<SysUser>(sysUser.getCurrentPage(), 10);
        return Result.OK(sysUserService.selectPage(userPage, userEntityWrapper));
    }

    /**
     * param: 
     *   request
     * describe: 当前登录人，不设使用权限
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @GetMapping("/current")
    public HttpResult current(HttpServletRequest request) {
        return Result.OK(sysUserService.current(request));
    }

    /**
     * param: 
     *   id: 根据ID获取用户详情
     * describe: 根据ID获取用户详情
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping("/{id}")
    public HttpResult details(@PathVariable Integer id) {
        SysUser user = sysUserService.selectUserDetails(id);
        return Result.OK(user);
    }

    /**
     * param: 
     *   user: 被修改的用户
     *   id: 被修改用户ID
     * describe: 更新修改指定用户
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PutMapping("/{id}")
    public HttpResult update(HttpServletResponse response, @RequestBody SysUser user, @PathVariable Integer id) {
        if (sysUserService.updateById(user)) {
            return Result.OK("修改用户成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    /**
     * param: 
     *   user: 用户信息
     * describe: 保存用户
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping()
    public HttpResult create(@RequestBody SysUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (sysUserService.insert(user)) {
            return Result.OK("新建用户成功");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    /**
     * param: 
     *   id: 用户ID
     * describe: 根据ID删除用户
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable Integer id) {
        if (sysUserService.deleteById(id)) {
            return Result.OK("删除用户成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    /**
     * param: 
     *   sysUser: 更新用户的角色信息
     * describe: sysUser: 更新用户的角色信息
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @PostMapping("/roles")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult saveRoles(@RequestBody SysUser sysUser) {
        // 获取用户的角色列表
        EntityWrapper<SysUserRole> userRoleEntityWrapper = new EntityWrapper<>();
        userRoleEntityWrapper.eq("user_id", sysUser.getId());
        // 删除用户所有角色
        sysUserRoleService.delete(userRoleEntityWrapper);
        // 添加用户角色
        List<SysUserRole> userRoles = new ArrayList<>();
        for (Object id : sysUser.getRoleIds()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getId());
            sysUserRole.setRoleId((Integer) id);
            userRoles.add(sysUserRole);
        }
        if (CollectionUtils.isEmpty(userRoles)) {
            return ErrorResult.EXPECTATION_FAILED();
        }
        sysUserRoleService.insertBatch(userRoles);
        return Result.OK("分配角色成功");
    }
}

