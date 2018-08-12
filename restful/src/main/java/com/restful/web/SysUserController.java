package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.SysUser;
import com.restful.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("")
    public HttpResult index(HttpServletRequest request, SysUser sysUser) {
        EntityWrapper<SysUser> userEntityWrapper = new EntityWrapper<SysUser>();
        userEntityWrapper.setEntity(sysUser);

        return Result.OK(sysUser.selectPage(new Page<SysUser>(), userEntityWrapper));
    }

    @GetMapping("/current")
    public HttpResult current(HttpServletRequest request) {
        return Result.OK(sysUserService.current(request));
    }

    @GetMapping("/{id}")
    public HttpResult details(@PathVariable Integer id){
        SysUser user = sysUserService.selectById(id);
        user.setPassword(null);
        return Result.OK(user);
    }
}

