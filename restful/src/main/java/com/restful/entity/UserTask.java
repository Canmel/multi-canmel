package com.restful.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.activiti.engine.impl.persistence.entity.TaskEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTask{
    public String id;
    private String name;
    private String description;

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
}
