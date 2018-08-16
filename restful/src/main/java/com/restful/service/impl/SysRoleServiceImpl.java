package com.restful.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.restful.entity.SysMenu;
import com.restful.entity.SysRole;
import com.restful.entity.SysRoleMenu;
import com.restful.mapper.SysMenuMapper;
import com.restful.mapper.SysRoleMapper;
import com.restful.mapper.SysRoleMenuMapper;
import com.restful.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public SysRole selectRoleDetails(Integer id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        if (ObjectUtils.isEmpty(sysRole)) {
            return null;
        }
        EntityWrapper<SysRoleMenu> sysRoleMenuEntityWrapper = new EntityWrapper<SysRoleMenu>();
        sysRoleMenuEntityWrapper.eq("role_id", id);
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(sysRoleMenuEntityWrapper);
        List<Integer> menu_ids = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : roleMenus) {
            menu_ids.add(sysRoleMenu.getMenuId());
        }
        sysRole.setMenuIds(menu_ids);
        if(menu_ids.size() > 0){
            sysRole.setMenus(sysMenuMapper.selectBatchIds(menu_ids));
        }
        return sysRole;
    }
}
