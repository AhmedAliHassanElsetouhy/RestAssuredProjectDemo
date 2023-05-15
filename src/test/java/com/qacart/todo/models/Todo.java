package com.qacart.todo.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Todo {
    public Todo(){
    }

    public Todo(Boolean isCompleted, String item){
        this.isCompleted = isCompleted;
        this.item = item;
    }
    @JsonProperty("isCompleted")
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    @JsonProperty("isCompleted")
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    @JsonProperty("isCompleted")
    private Boolean isCompleted;
    private String item;
    @JsonProperty("_id")
    private String id;
    private String userID;
    private String createdAt;
    @JsonProperty("__v")
    private String v;
    private List<Todo> tasks;


    public List<Todo> getTasks() {
        return tasks;
    }

    public void setTasks(List<Todo> tasks) {
        this.tasks = tasks;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("__v")
    public String getV() {
        return v;
    }
    @JsonProperty("__v")
    public void setV(String v) {
        this.v = v;
    }
}
