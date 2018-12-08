package com.restful.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.core.entity.ErrorResult;
import com.core.entity.HttpResult;
import com.core.entity.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.config.activiti.ActivitiForm;
import com.restful.entity.SysRole;
import com.restful.entity.SysUser;
import com.restful.entity.UserTask;
import com.restful.entity.WorkFlow;
import com.restful.entity.enums.WorkFlowPublish;
import com.restful.exception.UnAuthenticationException;
import com.restful.exception.WorkFlowImageGenerateFaildException;
import com.restful.service.SysUserService;
import com.restful.service.SystemFlowService;
import com.restful.service.WorkFlowService;
import com.sun.xml.internal.bind.v2.TODO;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    private ObjectMapper objectMapper;

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SystemFlowService flowService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

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
        WorkFlow workFlow = workFlowService.selectById(id);
//            TODO 实物添加，当数据库提交失败 发布的要撤回
        Deployment deployment = repositoryService.createDeployment()
                .name(workFlow.getKey() + ".bpmn")
                .addString(workFlow.getKey() + ".bpmn", workFlow.getFlow())
                .deploy();
        System.out.println(deployment.getName());
        return Result.OK("流程部署成功");
    }

    /**
     * describe: 已发布流程
     * creat_user: baily
     * creat_date: 2018/8/19
     **/
    @GetMapping("/deployed")
    public HttpResult deployed(WorkFlow workFlow) {
        EntityWrapper<WorkFlow> workFlowEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(workFlow.getFlowType())) {
            workFlowEntityWrapper.eq("flowType", workFlow.getFlowType());
        }
        List<WorkFlow> workFlows = workFlowService.selectList(workFlowEntityWrapper);
        return Result.OK(workFlows);
    }

    /**
     * 流程跟踪图
     * @param request
     * @param response
     * @param id
     */
    @GetMapping("/task/image/{id}")
    public void taskImage(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
        InputStream inputStream = workFlowService.traceProcessImage(id);

        try {
            OutputStream outputStream = response.getOutputStream();
            // 输出图片内容
            byte[] b = new byte[1024];
            int len;
            while ((len = inputStream.read(b, 0, 1024)) != -1) {
                outputStream.write(b, 0, len);
            }
        } catch (IOException e) {
            throw new WorkFlowImageGenerateFaildException();
        }
    }

    /**
     * 通过
     * @param id
     * @param params
     * @return
     */
    @GetMapping("/task/pass/{id}")
    public Result pass(@PathVariable String id, ActivitiForm params) {
        Map paramMap = objectMapper.convertValue(params, HashMap.class);
        boolean isPass = workFlowService.passProcess(id, paramMap, ()->{
            System.out.println("通过回调");
        });
        return Result.OK("审批成功");
    }

    /**
     * 驳回
     * @param id
     * @param params
     * @return
     */
    @GetMapping("/task/back/{id}")
    public Result back(@PathVariable String id, ActivitiForm params) {
        Map paramMap = objectMapper.convertValue(params, HashMap.class);
        boolean isBack = workFlowService.backProcess(id, null, paramMap, ()-> {
            System.out.println("工作流控制器中调用");
        });
        return Result.OK("驳回成功");
    }

    /**
     * 获取评论信息列表
     * @param id
     * @return
     */
    @GetMapping("/comments")
    public Result comments(String id){
        List<UserTask> list = workFlowService.comments(id);
        return Result.OK(list);
    }
}

