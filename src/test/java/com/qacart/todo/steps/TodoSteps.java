package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.models.Todo;
import io.restassured.response.Response;

public class TodoSteps {
    public static Todo generateTodoTask(){
        Faker faker = new Faker();
        Boolean isCompleted = false;
        String item = faker.educator().course();
        return new Todo(isCompleted, item);
    }

    public static Response addTodo(String token){
        Todo todo = TodoSteps.generateTodoTask();
        Response response = TodoApi.AddTodoApi(todo, token);
        return response;
    }

    public static String getTaskId(Todo todo, String token){
        Response response = TodoApi.AddTodoApi(todo, token);
        Todo returnedTodo = response.body().as(Todo.class);
        return returnedTodo.getId();
    }

    public static String getUserID(Todo todo, String token){
        Response response = TodoApi.AddTodoApi(todo, token);
        Todo addedTodo = response.body().as(Todo.class);
        return addedTodo.getUserID();
    }

    public static Todo updateTodoTask(){
        Faker faker = new Faker();
        Boolean isCompleted = true;
        String item = faker.educator().course();
        return new Todo(isCompleted, item);
    }
}
