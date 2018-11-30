package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.SysUser;
import com.restful.entity.WorkFlow;
import com.restful.exception.UnAuthenticationException;
import com.restful.service.SysUserService;
import com.restful.service.SystemFlowService;
import com.restful.service.WorkFlowService;
import io.swagger.annotations.Api;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
 * @since 2018-08-17
 */
@RestController
@Api(value = "工作流实例接口", description = "工作流实例接口")
@RequestMapping("/api/workflow/instances")
public class WorkFlowInstanceController extends BaseController {

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SystemFlowService flowService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessEngine processEngine() {
        return ProcessEngines.getDefaultProcessEngine();
    }

    /**
     * describe: 分页查询工作流信息
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
//    @GetMapping()
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
//    public @ResponseBody
//    HttpResult index(WorkFlow workFlow) {
//        EntityWrapper<WorkFlow> workFlowEntityWrapper = new EntityWrapper<>();
//        workFlowEntityWrapper.like("name", workFlow.getName());
//        workFlowEntityWrapper.eq("is_public", workFlow.getIsPublic());
//        Page<WorkFlow> workFlowPage = new Page<>(workFlow.getCurrentPage(), workFlow.getTsize());
//        return Result.OK(workFlowService.selectPage(workFlowPage, workFlowEntityWrapper));
//    }
}

