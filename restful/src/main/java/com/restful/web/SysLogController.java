package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.SysLog;
import com.restful.entity.SysRole;
import com.restful.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
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
 * @since 2018-11-20
 */
@RestController
@RequestMapping("/api/logs")
@Api(value = "日志接口", description = "日志后台接口")
public class SysLogController extends BaseController {

    @Autowired
    private SysLogService service;


    @GetMapping
    @ApiOperation(value = "分页查询日志", notes = "分页查询日志列表")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public Result index(HttpServletRequest request, SysLog sysLog) {
        return Result.OK(service.selectLogsPage(sysLog));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "查询日志详细信息", notes = "根据ID查询日志详细信息")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public HttpResult details(HttpServletRequest request, @PathVariable Integer id) {
        return Result.OK(service.selectById(id));
    }
}

