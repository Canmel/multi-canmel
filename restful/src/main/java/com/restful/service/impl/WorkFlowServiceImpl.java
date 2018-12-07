package com.restful.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.restful.config.activiti.ActivitiEndCallBack;
import com.restful.entity.BaseEntity;
import com.restful.entity.UserTask;
import com.restful.entity.WorkFlow;
import com.restful.entity.enums.WorkFlowPublish;
import com.restful.mapper.WorkFlowMapper;
import com.restful.service.WorkFlowService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.util.StringUtil;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    @Override
    public boolean publish(Integer id) {
        WorkFlow workFlow = new WorkFlow(id, WorkFlowPublish.PUBLISHED.getValue());
        boolean flag = true;
        if (!flag) {
            return flag;
        }
        WorkFlow flow = this.selectById(id);
        repositoryService.createDeployment().name(workFlow.getName())
                .addClasspathResource("bpmn/" + flow.getName() + ".bpmn")
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

        SAXBuilder builder = new SAXBuilder();
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(result.getBytes(Charset.forName("UTF-8")));
            Document documen = builder.build(inputStream);
            Element foo = documen.getRootElement();
            Element element = (Element) foo.getChildren().get(0);
            Attribute attribute = element.getAttribute("id");
            String processKey = attribute.getValue();
            entity.setKey(processKey);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        entity.setFlow(result);
        return super.insert(entity);
    }

    @Override
    public boolean startProcess(BaseEntity baseEntity, WorkFlow workFlow, Map params) {
        // 将业务和流程绑定来
        String busniessKey = baseEntity.getClass().getSimpleName() + baseEntity.getId();
        // 启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(workFlow.getKey(), busniessKey, params);
        return !ObjectUtils.isEmpty(pi);
    }

    /**
     *
     * @param id 未结束的流程: ID为任务ID， 结束的流程： 历史记录中 流程实例ID
     * @return
     */
    @Override
    public InputStream traceProcessImage(String id) {
        Task task = taskService.createTaskQuery().taskId(id).singleResult();
        String processInstanceId = "";
        if (!ObjectUtils.isEmpty(task)) {
            processInstanceId = task.getProcessInstanceId();
        } else {
            processInstanceId = id;
        }

        // 获取历史流程实例
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();

        // 获取流程中已经执行的节点，按照执行先后顺序排序
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceId().asc().list();

        // 高亮已经执行流程节点ID集合
        List<String> highLightedActivitiIds = new ArrayList<>();
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            highLightedActivitiIds.add(historicActivityInstance.getActivityId());
        }

        List<HistoricProcessInstance> historicFinishedProcessInstances = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).finished()
                .list();

        processEngineConfiguration.setXmlEncoding("utf-8");
        processEngineConfiguration.setActivityFontName("宋体");
        processEngineConfiguration.setLabelFontName("宋体");
        // 生成图片的工具
        ProcessDiagramGenerator processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();

        BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
        // 高亮流程已发生流转的线id集合
        List<String> highLightedFlowIds = getHighLightedFlows(bpmnModel, historicActivityInstances);

        // 使用默认配置获得流程图表生成器，并生成追踪图片字符流
        InputStream imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitiIds, highLightedFlowIds, "宋体", "宋体", null, 2.0);

        return imageStream;
    }

    /**
     * 获取高亮流程
     * @param bpmnModel
     * @param historicActivityInstances
     * @return
     */
    private static List<String> getHighLightedFlows(BpmnModel bpmnModel, List<HistoricActivityInstance> historicActivityInstances) {
        // 高亮流程已发生流转的线id集合
        List<String> highLightedFlowIds = new ArrayList<>();
        // 全部活动节点
        List<FlowNode> historicActivityNodes = new ArrayList<>();
        // 已完成的历史活动节点
        List<HistoricActivityInstance> finishedActivityInstances = new ArrayList<>();

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance.getActivityId());
            historicActivityNodes.add(flowNode);
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstances.add(historicActivityInstance);
            }
        }

        FlowNode currentFlowNode = null;
        FlowNode targetFlowNode = null;
        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        for (HistoricActivityInstance currentActivityInstance : finishedActivityInstances) {
            // 获得当前活动对应的节点信息及outgoingFlows信息
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance.getActivityId());
            List<SequenceFlow> sequenceFlows = currentFlowNode.getOutgoingFlows();

            /**
             * 遍历outgoingFlows并找到已已流转的 满足如下条件认为已已流转： 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
             */
            if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配流程目标节点的
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(sequenceFlow.getTargetRef());
                    if (historicActivityNodes.contains(targetFlowNode)) {
                        highLightedFlowIds.add(targetFlowNode.getId());
                    }
                }
            } else {
                List<Map<String, Object>> tempMapList = new ArrayList<>();
                for (SequenceFlow sequenceFlow : sequenceFlows) {
                    for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
                        if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("highLightedFlowId", sequenceFlow.getId());
                            map.put("highLightedFlowStartTime", historicActivityInstance.getStartTime().getTime());
                            tempMapList.add(map);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(tempMapList)) {
                    // 遍历匹配的集合，取得开始时间最早的一个
                    long earliestStamp = 0L;
                    String highLightedFlowId = null;
                    for (Map<String, Object> map : tempMapList) {
                        long highLightedFlowStartTime = Long.valueOf(map.get("highLightedFlowStartTime").toString());
                        if (earliestStamp == 0 || earliestStamp >= highLightedFlowStartTime) {
                            highLightedFlowId = map.get("highLightedFlowId").toString();
                            earliestStamp = highLightedFlowStartTime;
                        }
                    }
                    highLightedFlowIds.add(highLightedFlowId);
                }
            }
        }
        return highLightedFlowIds;
    }

    /**
     * 流程通过
     * @param taskId 需要通过的任务ID
     * @param variables 流程存储参数
     * @return
     */
    @Override
    public boolean passProcess(String taskId, Map<String, Object> variables) {
        taskService.addComment(taskId, null, (String) variables.get("comment"));
        try {
            commitProcess(taskId, variables, null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if(isEndByTaskId(taskId)){
            // 执行回调函数
        }
        return true;
    }

    /**
     * 驳回流程
     * @param taskId 需要驳回的任务ID
     * @param activityId 目标ID
     * @param variables 流程存储参数
     * @return
     */
    @Override
    public boolean backProcess(String taskId, String activityId, Map<String, Object> variables, ActivitiEndCallBack activitiEndCallBack) {
        taskService.addComment(taskId, null, (String) variables.get("comment"));
        try {
            ActivityImpl endActivity = findActivitiImpl(taskId, "end");
            commitProcess(taskId, null, endActivity.getId());
        } catch (Exception e) {
            return false;
        }
        if(isEndByTaskId(taskId)){
            // 执行回调函数
            activitiEndCallBack.callBack();
        }

        return true;

//        taskService.addComment(taskId, null, (String) variables.get("comment"));
//        List<Task> tasks = taskService.createTaskQuery().taskId(taskId).list();
//        for (Task task : tasks) {// 级联结束本节点发起的会签任务
//            try {
//                commitProcess(task.getId(), variables, null);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        }
//        return false;
    }

    /**
     * 提交
     * @param taskId 任务ID
     * @param variables 流程参数
     * @param activityId 目标节点
     * @throws Exception
     */
    private void commitProcess(String taskId, Map<String, Object> variables,
                               String activityId) throws Exception {
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        // 跳转节点为空，默认提交操作
        if (StringUtils.isEmpty(activityId)) {
            taskService.complete(taskId, variables);
        } else {// 流程转向操作
            turnTransition(taskId, activityId, variables);
        }
//        taskService.complete(taskId, variables);
    }

    private void turnTransition(String taskId, String activityId,
                                Map<String, Object> variables) throws Exception {
        // 当前节点
        ActivityImpl currActivity = findActivitiImpl(taskId, null);
        // 清空当前流向
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

        // 创建新流向
        TransitionImpl newTransition = currActivity.createOutgoingTransition();
        // 目标节点
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
        // 设置新流向的目标节点
        newTransition.setDestination(pointActivity);

        // 执行转向任务
        taskService.complete(taskId, variables);
        // 删除目标节点新流入
        pointActivity.getIncomingTransitions().remove(newTransition);

        // 还原以前流向
        restoreTransition(currActivity, oriPvmTransitionList);
    }

    private ActivityImpl findActivitiImpl(String taskId, String activityId)
            throws Exception {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

        // 获取当前活动节点ID
        if (StringUtils.isEmpty(activityId)) {
            activityId = findTaskById(taskId).getTaskDefinitionKey();
        }

        // 根据流程定义，获取该流程实例的结束节点
        if (activityId.toUpperCase().equals("END")) {
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {
                List<PvmTransition> pvmTransitionList = activityImpl
                        .getOutgoingTransitions();
                if (pvmTransitionList.isEmpty()) {
                    return activityImpl;
                }
            }
        }

        // 根据节点ID，获取对应的活动节点
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)
                .findActivity(activityId);

        return activityImpl;
    }

    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
        // 存储当前节点所有流向临时变量
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
        // 获取当前节点所有流向，存储到临时变量，然后清空
        List<PvmTransition> pvmTransitionList = activityImpl
                .getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();

        return oriPvmTransitionList;
    }

    private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
            String taskId) throws Exception {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(findTaskById(taskId)
                        .getProcessDefinitionId());

        if (processDefinition == null) {
            throw new Exception("流程定义未找到!");
        }

        return processDefinition;
    }


    private TaskEntity findTaskById(String taskId) throws Exception {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(
                taskId).singleResult();
        if (task == null) {
            throw new Exception("任务实例未找到!");
        }
        return task;
    }

    private void restoreTransition(ActivityImpl activityImpl,
                                   List<PvmTransition> oriPvmTransitionList) {
        // 清空现有流向
        List<PvmTransition> pvmTransitionList = activityImpl
                .getOutgoingTransitions();
        pvmTransitionList.clear();
        // 还原以前流向
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
    }

    @Override
    public List<UserTask> comments(String id) {
        Task task = taskService.createTaskQuery().taskId(id).singleResult();
        List<UserTask> userTasks = new ArrayList<>();
        List<HistoricTaskInstance> tasks = new ArrayList<>();
        if (ObjectUtils.isEmpty(task)) {
            // 去历史记录中寻找
            tasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(id).list();
        } else {
            String processInstanceId = task.getProcessInstanceId();
            tasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
        }

        for (HistoricTaskInstance t : tasks) {
            List<Comment> comments = taskService.getTaskComments(t.getId());
            UserTask userTask = new UserTask();
            userTask.setName(t.getName());
            userTask.setComment(comments);
            userTasks.add(userTask);
        }
        return userTasks;
    }

    public boolean isEndByTaskId(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (!ObjectUtils.isEmpty(task)) {
            return false;
        }
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        String processInstanceId = historicTaskInstance.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        return ObjectUtils.isEmpty(processInstance);

    }
}
