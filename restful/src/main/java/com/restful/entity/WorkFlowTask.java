package com.restful.entity;

import com.alibaba.fastjson.JSONObject;
import com.restful.config.application.WorkFlowProperties;
import org.springframework.util.StringUtils;

/**
 *
 *  前端控制器
 *
 * @author baily
 * 描述:
 * ${DESCRIPTION}
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
 * @since 2018年08月19日
 */
public class WorkFlowTask {
    private String flow;

    private String flowName;

    private String text;

    private String desc;

    private Integer role;

    private String type;

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public WorkFlowTask(String flowName, String flow) {
        if (StringUtils.isEmpty(flow) || StringUtils.isEmpty(flowName)) {
            return;
        }
        this.flow = flow;
        this.flowName = flowName;
        JSONObject jsonObject = JSONObject.parseObject(flow);
        String taskType = jsonObject.getString(WorkFlowProperties.WORKFLOW_TYPE);
        this.setType(taskType);
        String text = ((JSONObject) jsonObject).getString(WorkFlowProperties.WORKFLOW_TEXT);
        String textVal = JSONObject.parseObject(text).getString(WorkFlowProperties.WORKFLOW_TEXT);
        this.setText(textVal);
        String desc = ((JSONObject) jsonObject).getString(WorkFlowProperties.WORKFLOW_DESC);
        this.setDesc(desc);
    }

    public WorkFlowTask() {
    }
}
