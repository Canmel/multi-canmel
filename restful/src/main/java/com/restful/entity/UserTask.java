package com.restful.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTask{
    private String id;
    private String name;
    private String description;
    private String isEnd;

    public UserTask() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }
}
