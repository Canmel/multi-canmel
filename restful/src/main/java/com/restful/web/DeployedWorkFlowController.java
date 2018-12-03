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
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
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
 * @since 2018-08-17
 */
@RestController
@Api(value = "已发布工作流接口", description = "已发布工作流接口")
@RequestMapping("/api/deployedWorkflows")
public class DeployedWorkFlowController extends BaseController {

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

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @ApiOperation(value = "分页查询已发布工作流", notes = "分页查询已发布流程")
    public @ResponseBody
    HttpResult deployedList(WorkFlow workFlow) {
        Page<Deployment> page = new Page<>(workFlow.getCurrentPage(), workFlow.getTsize());
        List<Deployment> list = processEngine().getRepositoryService().createDeploymentQuery().list();
        page.setTotal(list.size());
        Collections.reverse(list);
        list.forEach((item) -> {
            DeploymentEntity deploymentEntity = (DeploymentEntity) item;
            deploymentEntity.setResources(new HashMap<>());
        });
        List<Deployment> deployments = list.subList((workFlow.getCurrentPage() - 1) * workFlow.getTsize(), list.size());
        page.setRecords(deployments);
        return Result.OK(page);
    }

    @PostMapping
    public HttpResult create(@RequestBody WorkFlow workFlow, Principal principal) {
        SysUser currentUser = sysUserService.current(principal);
        if (ObjectUtils.isEmpty(currentUser)) {
            throw new UnAuthenticationException("当前用户不存在");
        }
        workFlow.setCreator(currentUser.getId());
        if (workFlowService.insert(workFlow)) {
            return Result.OK("新建流程成功");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    /**
     * describe: 根据ID获取工作流详情
     * creat_user: baily
     * creat_date: 2018/8/18
     **/
    @GetMapping("/{id}")
    public HttpResult details(@PathVariable Integer id) {
        return Result.OK(workFlowService.selectById(id));
    }

    /**
     * describe: 修改更新工作流
     * creat_user: baily
     * creat_date: 2018/8/18
     **/
    @PutMapping("/{id}")
    public HttpResult update(@PathVariable Integer id, @RequestBody WorkFlow workFlow) {
        workFlow.setId(id);
        if (workFlowService.updateById(workFlow)) {
            return Result.OK("修改流程信息成功");
        } else {
            return ErrorResult.UNAUTHORIZED();
        }
    }

    /**
     * describe: 删除流程
     * creat_user: baily
     * creat_date: 2018/8/19
     **/
    @DeleteMapping("/{id}")
    public HttpResult delete(@PathVariable Integer id) {
        try {
            RepositoryService repositoryService = processEngine().getRepositoryService();
            repositoryService.deleteDeployment(id.toString());
            return Result.OK("删除流程成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ErrorResult.EXPECTATION_FAILED("流程已经被使用或其他错误，流程未删除！");
        }
    }

    /**
     * describe: 发布流程
     * creat_user: baily
     * creat_date: 2018/8/19
     **/
    @GetMapping("/publish/{id}")
    public HttpResult publish(@PathVariable Integer id) {
        if (workFlowService.publish(id)) {
            WorkFlow workFlow = workFlowService.selectById(id);
//            TODO 实物添加，当数据库提交失败 发布的要撤回
            Deployment deployment = processEngine().getRepositoryService().createDeployment().addString(workFlow.getName(), workFlow.getFlow()).deploy();
            System.out.println(deployment.getName());
        }
        return Result.OK("流程部署成功");
    }
}

