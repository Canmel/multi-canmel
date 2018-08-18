package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.SysMenu;
import com.restful.entity.enums.MenuLevel;
import com.restful.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * @since 2018-08-15
 */
@RestController
@RequestMapping("/api/menus")
    public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;
    
    /**
     * param: 
     *   menu: 查询项
     * describe: 分页查询菜单信息
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult index(HttpServletRequest request, SysMenu menu) {
        EntityWrapper<SysMenu> sysRoleEntityWrapper = new EntityWrapper<SysMenu>();
        sysRoleEntityWrapper.like("menuname", menu.getMenuname()).like("description", menu.getDescription());
        Page<SysMenu> menuPage = new Page<SysMenu>(menu.getCurrentPage(), 10);
        return Result.OK(sysMenuService.selectPage(menuPage, sysRoleEntityWrapper));
    }

    /**
     * describe: 查看菜单详细信息
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult details(HttpServletRequest request, @PathVariable Integer id) {
        return Result.OK(sysMenuService.selectById(id));
    }

    /**
     * describe: 修改菜单
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult update(HttpServletRequest request, HttpServletResponse response, @RequestBody SysMenu menu, @PathVariable Integer id) {
        if (sysMenuService.updateById(menu)) {
            return Result.OK("修改菜单成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    /**
     * describe: 新建菜单
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult create(@RequestBody SysMenu menu) {
        if (sysMenuService.insert(menu)) {
            return Result.OK("新建菜单成功");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    /**
     * describe: 根据id删除菜单
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult delete(@PathVariable Integer id) {
        if (sysMenuService.deleteById(id)) {
            return Result.OK("删除菜单成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    /**
     * describe: 查询所有一级菜单
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @GetMapping("/tops")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult top(HttpServletRequest request) {
        EntityWrapper<SysMenu> menuEntityWrapper = new EntityWrapper<SysMenu>();
        menuEntityWrapper.eq("level", MenuLevel.TOP_MENU.getValue());
        return Result.OK(sysMenuService.selectList(menuEntityWrapper));
    }

    /**
     * describe: 查询所有二级菜单
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @GetMapping("/subs")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult subs(HttpServletRequest request){
        EntityWrapper<SysMenu> menuEntityWrapper = new EntityWrapper<SysMenu>();
        menuEntityWrapper.eq("level", MenuLevel.SUB_MENU.getValue());
        return Result.OK(sysMenuService.selectList(menuEntityWrapper));
    }
}

