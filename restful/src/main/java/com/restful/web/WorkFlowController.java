package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.restful.entity.SysUser;
import com.restful.entity.WorkFlow;
import com.restful.entity.enums.WorkFlowPublish;
import com.restful.exception.UnAuthenticationException;
import com.restful.service.SystemFlowService;
import com.restful.service.WorkFlowService;
import io.swagger.annotations.Api;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
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
    private SystemFlowService flowService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private ProcessEngine processEngine() {
        return ProcessEngines.getDefaultProcessEngine();
    }

//    @GetMapping("/test")
//    public HttpResult test(){
//        Map variable = new HashMap();
//        String txt = "123123";
//        List list = processEngine().getRepositoryService().createDeploymentQuery().list();
//        processEngine().getRepositoryService()
//        .createDeployment()
//                .name("newDeployment")
//                .addClasspathResource("bpmn/demo1.bpmn")
//                .addClasspathResource("bpmn/demo1.png").deploy();
//
//        System.out.println(list);
//        runtimeService.startProcessInstanceByKey("demo1", variable);
//        return Result.OK("123123");
//    }

    /**
     * describe: 分页查询工作流信息
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public @ResponseBody HttpResult index(WorkFlow workFlow) {
        List<Deployment> list = processEngine().getRepositoryService().createDeploymentQuery().list();
        list.forEach((item) -> {
            DeploymentEntity deploymentEntity = (DeploymentEntity) item;
            deploymentEntity.setResources(new HashMap<>());
        });
        List<Deployment> deployments = list.subList((workFlow.getCurrentPage() - 1) * workFlow.getTsize(), (workFlow.getCurrentPage() - 1) * workFlow.getTsize() + list.size() % workFlow.getTsize());
        Page<Deployment> page = new Page<>(workFlow.getCurrentPage(), workFlow.getTsize());
        page.setRecords(deployments);
        page.setTotal(list.size());
        return Result.OK(page);
    }


    /**
     * describe: 新建流程
     * creat_user: baily
     * creat_date: 2018/8/17
     **/
    @PostMapping
    public HttpResult create(HttpServletRequest request, @RequestBody WorkFlow workFlow) {
        SysUser currentUser = (SysUser) request.getSession().getAttribute("currentUser");
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
        WorkFlow workFlow = workFlowService.selectById(id);
        Deployment deployment = processEngine().getRepositoryService().createDeployment().addString("test", workFlow.getFlow()).deploy();
        System.out.println(deployment.getName());
        return Result.OK("流程部署成功");
    }
}

