package com.restful.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.restful.entity.SysUser;
import com.restful.entity.SysUserRole;
import com.restful.mapper.SysRoleMapper;
import com.restful.mapper.SysUserMapper;
import com.restful.mapper.SysUserRoleMapper;
import com.restful.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
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
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    public SysUserMapper sysUserMapper;

    @Autowired
    public SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    public SysRoleMapper sysRoleMapper;

    @Override
    public SysUser current(HttpServletRequest request) {
        return (SysUser) request.getSession().getAttribute("currentUser");
    }

    @Override
    public SysUser selectUserDetails(Integer id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        sysUser.setPassword(null);
        if (ObjectUtils.isEmpty(sysUser)) {
            return null;
        }
        EntityWrapper<SysUserRole> sysUserRoleEntityWrapper = new EntityWrapper<SysUserRole>();
        sysUserRoleEntityWrapper.eq("user_id", id);
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(sysUserRoleEntityWrapper);
        List<Integer> role_ids = new ArrayList<>();
        for (SysUserRole userRole : userRoles) {
            role_ids.add(userRole.getRoleId());
        }
        if (role_ids.size() > 0) {
            sysUser.setRoleIds(role_ids);
            sysUser.setSysRoles(sysRoleMapper.selectBatchIds(role_ids));
        }
        return sysUser;
    }

    @Override
    public SysUser current(Principal principal) {
        EntityWrapper<SysUser> userEntityWrapper = new EntityWrapper<>();
        userEntityWrapper.eq("username", principal.getName());
        return this.selectOne(userEntityWrapper);
    }
}
