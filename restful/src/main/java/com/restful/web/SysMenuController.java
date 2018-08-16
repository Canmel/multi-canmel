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

    @GetMapping
    public HttpResult index(HttpServletRequest request, SysMenu menu) {
        EntityWrapper<SysMenu> sysRoleEntityWrapper = new EntityWrapper<SysMenu>();
        sysRoleEntityWrapper.like("menuname", menu.getMenuname()).like("description", menu.getDescription());
        Page<SysMenu> menuPage = new Page<SysMenu>(menu.getCurrentPage(), 10);
        return Result.OK(request, sysMenuService.selectPage(menuPage, sysRoleEntityWrapper));
    }

    @GetMapping("/{id}")
    public HttpResult details(HttpServletRequest request, @PathVariable Integer id) {
        return Result.OK(request, sysMenuService.selectById(id));
    }

    @PutMapping("/{id}")
    public HttpResult update(HttpServletRequest request, HttpServletResponse response, @RequestBody SysMenu menu, @PathVariable Integer id) {
        if (sysMenuService.updateById(menu)) {
            return Result.OK(request, "修改菜单成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    @PostMapping()
    public HttpResult create(HttpServletRequest request, @RequestBody SysMenu menu) {
        if (sysMenuService.insert(menu)) {
            return Result.OK(request, "新建菜单成功");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    @DeleteMapping("/{id}")
    public HttpResult delete(HttpServletRequest request, @PathVariable Integer id) {
        if (sysMenuService.deleteById(id)) {
            return Result.OK(request, "删除菜单成功!");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    @GetMapping("/tops")
    public HttpResult top(HttpServletRequest request) {
        EntityWrapper<SysMenu> menuEntityWrapper = new EntityWrapper<SysMenu>();
        menuEntityWrapper.eq("level", MenuLevel.TOP_MENU.getValue());
        return Result.OK(request, sysMenuService.selectList(menuEntityWrapper));
    }
}

