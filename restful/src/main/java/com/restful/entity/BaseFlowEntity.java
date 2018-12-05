package com.restful.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.activiti.engine.task.Task;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseFlowEntity<T extends Model> extends BaseEntity<T> {

    @TableField(exist = false)
    public UserTask task;

    public UserTask getTask() {
        return task;
    }

    public void setTask(UserTask task) {
        this.task = task;
    }
}
