package com.restful.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.restful.config.application.WorkFlowProperties;
import com.restful.entity.enums.WorkFlowRectType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
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
 * @since 2018-08-18
 */
public class WorkFlowInstance extends BaseEntity<WorkFlowInstance> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer businessId;
    private Integer workFlowId;
    private String current;
    private String currentFlow;
    private String next;
    private String nextFlow;
    private Integer status;
    private Date createdAt;
    private String flow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Integer workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getCurrentFlow() {
        return currentFlow;
    }

    public void setCurrentFlow(String currentFlow) {
        this.currentFlow = currentFlow;
    }

    public String getNextFlow() {
        return nextFlow;
    }

    public void setNextFlow(String nextFlow) {
        this.nextFlow = nextFlow;
    }

    /** TODO
     * describe: 流程进入下一步
     * creat_user: baily
     * creat_date: 2018/8/20
     **/
    public void toNextTask(){

    }

    /** TODO
     * describe: 是否下一步是结束
     * creat_user: baily
     * creat_date: 2018/8/20
     **/
    public boolean isNextEnd(){
        return false;
    }

    /** TODO
     * describe: 当前节点是否是结束节点
     * creat_user: baily
     * creat_date: 2018/8/20
     **/
    public boolean isCurrentEnd(){
        return false;
    }

    /**
     * describe: 更新工作流实例，核实下一步 等信息
     * creat_user: baily
     * creat_date: 2018/8/20
     **/
    public boolean updateInstance() {
        if (!this.paramsCheck()) {
            return false;
        }
        if (StringUtils.isEmpty(this.currentFlow) || StringUtils.isEmpty(this.current)) {
            String currentTaskJson = currentTaskJson();
            String currentTaskName = currentTaskName();
            this.currentFlow = currentTaskJson;
            this.current = currentTaskName;
        }

        if (StringUtils.isEmpty(this.nextFlow) || StringUtils.isEmpty(this.next)) {
            nextTaskStr();
            Map<String, String> resultMap = this.nextTaskMap();
            this.next = resultMap.get("key");
            this.nextFlow = resultMap.get("value");
        }
        return true;
    }

    /**
     * describe: 校验
     * creat_user: baily
     * creat_date: 2018/8/20
     **/
    private boolean paramsCheck() {
        boolean isCheck = true;
        if (StringUtils.isEmpty(this.flow)) {
            isCheck = false;
        }
        return isCheck;
    }

    /**
     * describe: 获取当前任务JSON
     * creat_user: baily
     * creat_date: 2018/8/20
     **/
    public String currentTaskJson() {
        if (!StringUtils.isEmpty(this.currentFlow)) {
            return this.currentFlow;
        }
        // 获取start
        Map<String, String> map = getStartObject();
        if (ObjectUtils.isEmpty(map)) {
            return "";
        } else {
            return map.get("value");
        }
    }

    /**
     * describe: 获取当前任务名
     * creat_user: baily
     * creat_date: 2018/8/20
     **/
    public String currentTaskName() {
        if (!StringUtils.isEmpty(this.current)) {
            return this.current;
        }
        // 获取start
        Map<String, String> map = getStartObject();
        if (ObjectUtils.isEmpty(map)) {
            return "";
        } else {
            return map.get("key");
        }
    }

    public Map<String, String> getStartObject() {
        JSONObject map = JSONObject.parseObject(this.flow);
        JSONObject stateObj = map.getJSONObject(WorkFlowProperties.WORKFLOW_STATES);
        for (Object state : stateObj.keySet()) {
            JSONObject rectItem = stateObj.getJSONObject((String) state);
            String itemType = rectItem.getString(WorkFlowProperties.WORKFLOW_TYPE);
            if (WorkFlowRectType.RECT_TYPE_START.getValue().equals(itemType)) {
                Map result = new HashMap();
                result.put("key", (String) state);
                result.put("value", stateObj.getString((String) state));
                return result;
            }
        }
        return null;
    }


    /**
     * describe: 当前任务
     * creat_user: baily
     * creat_date: 2018/8/20
     **/
    public WorkFlowTask currentTask() {
        WorkFlowTask workFlowTask = null;
        if (StringUtils.isEmpty(this.currentFlow)) {
            // 当前为空
            JSONObject map = JSONObject.parseObject(this.flow);
            JSONObject stateObj = map.getJSONObject(WorkFlowProperties.WORKFLOW_STATES);
            for (Object state : stateObj.keySet()) {
                System.out.println((String) state);

                JSONObject rectItem = stateObj.getJSONObject((String) state);
                String itemType = rectItem.getString(WorkFlowProperties.WORKFLOW_TYPE);
                System.out.println(itemType);

                if (WorkFlowRectType.RECT_TYPE_START.getValue().equals(itemType)) {
                    this.current = (String) state;
                    this.currentFlow = stateObj.getString((String) state);
                    workFlowTask = new WorkFlowTask(current, this.currentFlow);
                    break;
                }
            }
        } else {
            // 当前不为空 直接使用
            workFlowTask = new WorkFlowTask(this.current, this.currentFlow);
        }
        JSONObject map = JSONObject.parseObject(this.flow);
        return workFlowTask;
    }


    /**
     * describe: 下一任务
     * creat_user: baily
     * creat_date: 2018/8/20
     **/
    public WorkFlowTask nextTask() {
        WorkFlowTask workFlowTask = null;
        if (StringUtils.isEmpty(this.currentFlow) && StringUtils.isEmpty(this.current)) {
            return null;
        } else {
            String nextKey = nextTaskStr();
            String next = getTaskStrByRectName(this.next);
            this.nextFlow = nextKey;
            workFlowTask = new WorkFlowTask(this.next, this.nextFlow);
        }
        return workFlowTask;
    }

    /**
     * describe: 下一任务名称
     * creat_user: baily
     * creat_date: 2018/8/20
     **/
    private String nextTaskStr() {
        if (StringUtils.isEmpty(this.current)) {
            return null;
        }

        JSONObject map = JSONObject.parseObject(this.flow);
        JSONObject pathObject = map.getJSONObject(WorkFlowProperties.WORKFLOW_PATH);
        for (Object po : pathObject.values()) {
            String start = ((JSONObject) po).getString(WorkFlowProperties.WORKFLOW_PATH_START);
            if (this.current.equals(start)) {
                String to = ((JSONObject) po).getString(WorkFlowProperties.WORKFLOW_PATH_END);
                JSONObject states = map.getJSONObject(WorkFlowProperties.WORKFLOW_STATES);
                for (String str : states.keySet()) {
                    if (str.equals(to)) {
                        this.next = to;
                        return states.getString(str);
                    }
                }
            }
        }
        return null;
    }

    /**
     * describe: 下一任务名称
     * creat_user: baily
     * creat_date: 2018/8/20
     **/
    private Map<String, String> nextTaskMap() {
        if (StringUtils.isEmpty(this.current)) {
            return null;
        }
        JSONObject map = JSONObject.parseObject(this.flow);
        JSONObject pathObject = map.getJSONObject(WorkFlowProperties.WORKFLOW_PATH);
        for (Object po : pathObject.values()) {
            String start = ((JSONObject) po).getString(WorkFlowProperties.WORKFLOW_PATH_START);
            if (this.current.equals(start)) {
                String to = ((JSONObject) po).getString(WorkFlowProperties.WORKFLOW_PATH_END);
                JSONObject states = map.getJSONObject(WorkFlowProperties.WORKFLOW_STATES);
                for (String str : states.keySet()) {
                    if (str.equals(to)) {
                        this.next = to;
                        String starteStr = states.getString(str);
                        Map<String, String> result = new HashMap<>();
                        result.put("key", to);
                        result.put("value", starteStr);
                        return result;
                    }
                }
            }
        }
        return null;
    }

    /**
     * describe: 通过下一任务名称获取下一任务JSON 字符串
     * creat_user: baily
     * creat_date: 2018/8/20
     **/
    private String getTaskStrByRectName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        String nextTask = "";

        JSONObject map = JSONObject.parseObject(this.flow);
        JSONObject stateObj = map.getJSONObject(WorkFlowProperties.WORKFLOW_STATES);
        for (Object state : stateObj.keySet()) {
            System.out.println((String) state);
            JSONObject rectItem = stateObj.getJSONObject((String) state);
            String stateStr = (String) state;

            if (name.equals(stateStr)) {
                nextTask = stateObj.getString(stateStr);
            }

        }
        return nextTask;
    }

    @Override
    public String toString() {
        return "WorkFlowInstance{" +
                ", id=" + id +
                ", businessId=" + businessId +
                ", workFlowId=" + workFlowId +
                ", current=" + current +
                ", next=" + next +
                ", status=" + status +
                ", createdAt=" + createdAt +
                "}";
    }
}
