package com.restful.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.restful.entity.enums.WorkFlowRectType;
import com.restful.entity.enums.WorkFlowType;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
    private String next;
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

    public WorkFlowTask currentTask() {
        WorkFlowTask workFlowTask = new WorkFlowTask();
        if (!StringUtils.isEmpty(this.current)) {
            JSONObject currentMap = JSONObject.parseObject(this.flow);

            String type = (String) currentMap.get("type");
            workFlowTask.setType(type);

            JSONObject text = (JSONObject) currentMap.get("text");
            String textVal = (String) text.get("text");
            workFlowTask.setText(textVal);

            JSONObject desc = currentMap.getJSONObject("desc");
            String descVal = (String) desc.get("value");
            workFlowTask.setDesc(descVal);
        }
        if (StringUtils.isEmpty(this.flow)) {
            return null;
        }
        workFlowTask.setFlow(this.flow);


        JSONObject map = JSONObject.parseObject(this.flow);
        JSONObject states = map.getJSONObject("states");
        for (Object o: states.values()) {
            String taskType = ((JSONObject) o).getString("type");
            if(WorkFlowRectType.RECT_TYPE_START.getValue().equals(taskType)){
                workFlowTask.setType(taskType);
                String text = ((JSONObject) o).getString("text");
                String textVal = JSONObject.parseObject(text).getString("text");
                workFlowTask.setText(textVal);
                String desc = ((JSONObject) o).getString("desc");
                workFlowTask.setDesc(desc);
                break;
            }
        }
        // TODO 看我 ！ 看我 ！修剪修剪这里的代码哈
        return workFlowTask;
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
