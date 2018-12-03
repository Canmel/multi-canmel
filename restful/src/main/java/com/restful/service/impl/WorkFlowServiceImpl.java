package com.restful.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.restful.entity.WorkFlow;
import com.restful.entity.enums.WorkFlowPublish;
import com.restful.mapper.WorkFlowMapper;
import com.restful.service.WorkFlowService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileWriter;
import java.io.IOException;

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

    @Override
    public boolean publish(Integer id) {
        WorkFlow workFlow = new WorkFlow(id, WorkFlowPublish.PUBLISHED.getValue());
        boolean flag = true;
//                workFlowMapper.updateById(workFlow) > 0;
        if(!flag) {return flag;}
            WorkFlow flow = this.selectById(id);
        repositoryService.createDeployment().name(workFlow.getName())
                .addClasspathResource("bpmn/"+ flow.getName() + ".bpmn")
//                .addClasspathResource("bpmn/" + flow.getName() + ".png")
                .deploy();
        return flag;
    }

    @Override
    public boolean insert(WorkFlow entity) {
        boolean flag = super.insert(entity);
        if(!flag){
            return flag;
        }
        entity.setName(entity.getName() + ".bpmn");

        String sourceReplace = "xmlns:activiti=\"http://activiti.org/bpmn";
        String oldReplace = "xmlns:camunda=\"http://camunda.org/schema/1.0/bpmn";
        String bpmn = entity.getFlow();
        int index = bpmn.indexOf(oldReplace);
        String result = "";
        if(index > -1) {
            result = bpmn.replace(oldReplace, sourceReplace);
        }
        result = bpmn.replaceAll("camunda", "activiti");
        result = result.replaceAll("bpmn2:", "");
        result = result.replaceAll(":bpmn2", "");
//        result = result.replaceAll("xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\"", "");
//        result = result.replaceAll("xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\"", "");
//        result = result.replaceAll("xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\"", "");
//        result = result.replaceAll("xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\"", "");
//        result = result.replaceAll("targetNamespace=\"http://bpmn.io/schema/bpmn\"", "");
//        result = result.replaceAll("xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd\"", "");
//
//        String oldParams = "xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:tns=\"http://www.activiti.org/testm1543298804245\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" expressionLanguage=\"http://www.w3.org/1999/XPath\" id=\"m1543298804245\" name=\"\" targetNamespace=\"http://www.activiti.org/testm1543298804245\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\"";
//        String newParams = "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";


//        result = result.replaceAll(newParams, oldParams);

        Deployment deployment = repositoryService.createDeployment().name(entity.getName()).addString(entity.getName(), result).deploy();
        return flag;
    }
}
