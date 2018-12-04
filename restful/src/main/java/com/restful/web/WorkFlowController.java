package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.SysRole;
import com.restful.entity.SysUser;
import com.restful.entity.WorkFlow;
import com.restful.entity.enums.WorkFlowPublish;
import com.restful.exception.UnAuthenticationException;
import com.restful.service.SysUserService;
import com.restful.service.SystemFlowService;
import com.restful.service.WorkFlowService;
import com.sun.xml.internal.bind.v2.TODO;
import io.swagger.annotations.Api;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Api(value = "工作流接口", description = "工作流接口")
@RequestMapping("/api/workflow")
public class WorkFlowController extends BaseController {

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
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public @ResponseBody
    HttpResult index(WorkFlow workFlow) {
        EntityWrapper<WorkFlow> workFlowEntityWrapper = new EntityWrapper<>();
        workFlowEntityWrapper.like("name", workFlow.getName());
        workFlowEntityWrapper.eq("is_public", workFlow.getIsPublic());
        Page<WorkFlow> workFlowPage = new Page<>(workFlow.getCurrentPage(), workFlow.getTsize());
        // 部署
//        Deployment deployment = processEngine().getRepositoryService().createDeploymentQuery().deploymentId("17501").singleResult();
//        ProcessDefinition pd = processEngine().getRepositoryService().createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        // 启动
//        ProcessInstance pi = processEngine().getRuntimeService().startProcessInstanceById(pd.getId());

        // 查询
//        ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey("newPros");
//        List<ProcessInstance> ps = processEngine().getRuntimeService().createProcessInstanceQuery().list();
//        List<Task> tasks = processEngine().getTaskService().createTaskQuery().list();

        return Result.OK(workFlowService.selectPage(workFlowPage, workFlowEntityWrapper));
    }


    /**
     * describe: 新建流程
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
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
        if (workFlowService.deleteById(id)) {
            return Result.OK("删除流程成功");
        } else {
            return ErrorResult.EXPECTATION_FAILED();
        }
    }

    /**
     * describe: 发布流程
     * creat_user: baily
     * creat_date: 2018/8/19
     **/
    @GetMapping("/publish/{id}")
    public HttpResult publish(@PathVariable Integer id) {
//        if (workFlowService.publish(id)) {
            WorkFlow workFlow = workFlowService.selectById(id);
//            TODO 实物添加，当数据库提交失败 发布的要撤回

            Deployment deployment = processEngine().getRepositoryService()
                    .createDeployment()
                    .name(workFlow.getName())
                    .addClasspathResource("bpmn/demo1.bpmn")
                    .addClasspathResource("bpmn/demo1.png").deploy();
            System.out.println(deployment.getName());
//        }
        return Result.OK("流程部署成功");
    }
}

