package com.restful.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.restful.entity.BaseEntity;
import com.restful.entity.WorkFlow;
import com.restful.entity.enums.WorkFlowPublish;
import com.restful.mapper.WorkFlowMapper;
import com.restful.service.WorkFlowService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
 * @since 2018-08-17
 */
@Service
public class WorkFlowServiceImpl extends ServiceImpl<WorkFlowMapper, WorkFlow> implements WorkFlowService {
    @Autowired
    private WorkFlowMapper workFlowMapper;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public boolean publish(Integer id) {
        WorkFlow workFlow = new WorkFlow(id, WorkFlowPublish.PUBLISHED.getValue());
        boolean flag = true;
//                workFlowMapper.updateById(workFlow) > 0;
        if (!flag) {
            return flag;
        }
        WorkFlow flow = this.selectById(id);
        repositoryService.createDeployment().name(workFlow.getName())
                .addClasspathResource("bpmn/" + flow.getName() + ".bpmn")
//                .addClasspathResource("bpmn/" + flow.getName() + ".png")
                .deploy();
        return flag;
    }

    @Override
    public boolean insert(WorkFlow entity) {

        entity.setName(entity.getName() + ".bpmn");

        String sourceReplace = "xmlns:activiti=\"http://activiti.org/bpmn";
        String oldReplace = "xmlns:camunda=\"http://camunda.org/schema/1.0/bpmn";
        String bpmn = entity.getFlow();
        int index = bpmn.indexOf(oldReplace);
        String result = "";
        if (index > -1) {
            result = bpmn.replace(oldReplace, sourceReplace);
        }
        result = bpmn.replaceAll("camunda", "activiti");
        result = result.replaceAll("bpmn2:task", "userTask");
        result = result.replaceAll("bpmn2:", "");
        result = result.replaceAll(":bpmn2", "");

        entity.setFlow(result);
        return super.insert(entity);
    }

    @Override
    public boolean startProcess(BaseEntity baseEntity, WorkFlow workFlow, Map params) {
        // 将业务和流程绑定来
        String busniessKey = baseEntity.getClass().getSimpleName() + baseEntity.getId();
        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(workFlow.getWorkFlowType().name(), busniessKey, params);
        return !ObjectUtils.isEmpty(pi);
    }
}
