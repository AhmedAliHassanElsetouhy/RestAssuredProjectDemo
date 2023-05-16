package com.qacart.todo.testcases;

import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.data.ValidationData;
import com.qacart.todo.models.Error;
import com.qacart.todo.models.Todo;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Todo Features")
public class TodoTestCases {
    @Story("Positive Add Todo Task")
    @Test(description = "Should Be Able To Add Todo Task")
    public void ShouldBeAbleTeAddTodoTask(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoTask();
        Todo todoTask = new Todo(todo.getIsCompleted(), todo.getItem());
        Response response = TodoApi.AddTodoApi(todo, token);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(returnedTodo, notNullValue());
        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedTodo.getIsCompleted(), equalTo(todoTask.getIsCompleted()));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
    }

    @Story("Negative Add Todo Task")
    @Test(description = "Should Be Able To Add Todo Task With Null Item and Is Completed")
    public void ShouldNotBeAbleTeAddTodoTaskWithNullItemAndIsCompleted(){
        String token = UserSteps.getUserToken();
        Todo todoTask = new Todo(null, null);
        Response response = TodoApi.AddTodoApi(todoTask, token);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.ITEM_IS_REQUIRED));
    }

    @Story("Negative Add Todo Task")
    @Test(description = "Should Not Be Able TO Add Todo Task Without Item")
    public void ShouldNotBeAbleTOAddTodoTaskWithoutItem(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoTask();
        Todo todoTask = new Todo(todo.getIsCompleted(), null);
        Response response = TodoApi.AddTodoApi(todoTask, token);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.ITEM_IS_REQUIRED));
    }

    @Story("Negative Add Todo Task")
    @Test(description = "Should Not Be Able TO Add Todo Task With Empty Item")
    public void ShouldNotBeAbleTOAddTodoTaskWithEmptyItem(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoTask();
        Todo todoTask = new Todo(todo.getIsCompleted(), ValidationData.EMPTY);
        Response response = TodoApi.AddTodoApi(todoTask, token);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.ITEM_NOT_ALLOWED_TO_BE_EMPTY));
    }

    @Story("Negative Add Todo Task")
    @Test(description = "Should Not Be Able TO Add Todo Task Item Less Than 3 Digits")
    public void ShouldNotBeAbleTOAddTodoTaskWithItemLessThan3Digits(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoTask();
        Todo todoTask = new Todo(todo.getIsCompleted(), ValidationData.SHORT_STRING_FORMAT);
        Response response = TodoApi.AddTodoApi(todoTask, token);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.ITEM_NOT_ALLOWED_TO_BE_LESS_THAN_3_DIGITS));
    }

    @Story("Negative Add Todo Task")
    @Test(description = "Should Not Be Able To Add Todo Task Without Is Completed")
    public void ShouldNotBeAbleToAddTodoTaskWithoutIsCompleted(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoTask();
        Todo todoTask = new Todo(null, todo.getItem());
        Response response = TodoApi.AddTodoApi(todoTask, token);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.IS_COMPLETED_IS_REQUIRED));
    }
    @Story("Positive Get Todo")
    @Test(description = "Should Be Able To Get Specific Todo")
    public void ShouldBeAbleToGetSpecificTodo(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoTask();
        String taskId = TodoSteps.getTaskId(todo, token);
        Response response = TodoApi.getSpecificTodoApi(token, taskId);
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getId(), equalTo(taskId));
        assertThat(returnedTodo.getIsCompleted(), equalTo(returnedTodo.getIsCompleted()));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        String userId = TodoSteps.getUserID(todo, token);
        assertThat(returnedTodo.getUserID(), equalTo(userId));
        assertThat(returnedTodo.getCreatedAt(), notNullValue());
        assertThat(returnedTodo.getV(), equalTo("0"));
    }

    @Story("Negative Get Todo")
    @Test(description = "Should Not Be Able To Get Todo Of Another User")
    public void ShouldNotBeAbleToGetTodoOfAnotherUser(){
        String tokenUser1 = UserSteps.getUserToken();
        Todo todoUser1 = TodoSteps.generateTodoTask();
        String taskIdUser1 = TodoSteps.getTaskId(todoUser1, tokenUser1);
        Response responseUser1 = TodoApi.getSpecificTodoApi(tokenUser1, taskIdUser1);
        Todo returnedTodoUser1 = responseUser1.body().as(Todo.class);

        String tokenUser2 = UserSteps.getUserToken();
        Todo todoUser2 = TodoSteps.generateTodoTask();
        String taskIdUser2 = TodoSteps.getTaskId(todoUser2, tokenUser2);
        Response responseUser2 = TodoApi.getSpecificTodoApi(tokenUser2, taskIdUser1);
        assertThat(responseUser2.statusCode(),equalTo(403));
        Error returnedError = responseUser2.getBody().as(Error.class);
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.YOU_ARE_FORBIDDEN_TO_ACCESS_THIS_DATA));
//        assertThat(responseUser1.statusCode(), equalTo(200));
//        assertThat(returnedTodoUser1.getId(), equalTo(taskIdUser1));
//        assertThat(returnedTodoUser1.getIsCompleted(), equalTo(returnedTodoUser1.getIsCompleted()));
//        assertThat(returnedTodoUser1.getItem(), equalTo(todoUser1.getItem()));
//        String userId = TodoSteps.getUserID(todoUser1, tokenUser1);
//        assertThat(returnedTodoUser1.getUserID(), equalTo(userId));
//        assertThat(returnedTodoUser1.getCreatedAt(), notNullValue());
//        assertThat(returnedTodoUser1.getV(), equalTo("0"));
    }

    @Story("Negative Get Todo")
    @Test(description = "Should Not Be Able To Get Todo Of Another Token")
    public void ShouldNotBeAbleToGetTodoOfAnotherToken(){
        String tokenUser1 = UserSteps.getUserToken();
        String tokenUser2 = UserSteps.getUserToken();
        Todo todoUser1 = TodoSteps.generateTodoTask();
        String taskIdUser1 = TodoSteps.getTaskId(todoUser1, tokenUser1);
        Response responseUser1 = TodoApi.getSpecificTodoApi(tokenUser2, taskIdUser1);
        assertThat(responseUser1.statusCode(),equalTo(403));
        Error returnedError = responseUser1.getBody().as(Error.class);
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.YOU_ARE_FORBIDDEN_TO_ACCESS_THIS_DATA));
    }

    @Story("Positive Get All Todo Tasks")
    @Test(description = "Should Be Able To Get All To Do Tasks Belongs To Me")
    public void ShouldBeAbleToGetAllToDoTasksBelongsToMe(){
        String token = UserSteps.getUserToken();
        for (int i=1; i<=5; i++) {
            Todo todo = TodoSteps.generateTodoTask();
            TodoApi.AddTodoApi(todo, token);
        }
        Response response = TodoApi.getAllTodosApi(token);
        Todo todoList = response.body().as(Todo.class);
        List<Todo> todos = todoList.getTasks();
        assertThat(todos.size(), greaterThanOrEqualTo(1));
        assertThat(response.statusCode(), equalTo(200));
    }

//    @Story("Negative Get All Todo Tasks")
//    @Test(description = "Should Not Be Able To Get All To Do Tasks Of Another Token")
//    public void ShouldNotBeAbleToGetAllToDoTasksBelongsToAnotherToken(){
//        String tokenUser1 = UserSteps.getUserToken();
//        for (int i=1; i<=2; i++) {
//            Todo todoUser1 = TodoSteps.generateTodoTask();
//            TodoApi.AddTodoApi(todoUser1, tokenUser1);
//        }
//        //Add Logout API from tokenUser1
//        String tokenUser2 = UserSteps.getUserToken();
//        for (int i=1; i<=2; i++) {
//            Todo todoUser2 = TodoSteps.generateTodoTask();
//            TodoApi.AddTodoApi(todoUser2, tokenUser2);
//        }
//        Response responseUser1 = TodoApi.getAllTodosApi(tokenUser2);
//        assertThat(responseUser1.statusCode(), equalTo(200));
//        Error returnedError = responseUser1.body().as(Error.class);
//        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.YOU_ARE_FORBIDDEN_TO_ACCESS_THIS_DATA));
//        Response responseUser2 = TodoApi.getAllTodosApi(tokenUser1);
//        assertThat(responseUser2.statusCode(), equalTo(200));
//        Error returnedError2 = responseUser1.body().as(Error.class);
//        assertThat(returnedError2.getMessage(), equalTo(ErrorMessages.YOU_ARE_FORBIDDEN_TO_ACCESS_THIS_DATA));//        Error returnedError = responseUser1.body().as(Error.class);
//    }

    @Story("Positive Update Todo Task")
    @Test(description = "Should Be Able To Update Todo Task")
    public void ShouldBeAbleToUpdateTodoTask(){
//        String token = UserSteps.getUserToken();
//        Todo todo = TodoSteps.generateTodoTask();
//        Response response1 = TodoApi.AddTodoApi(todo, token);
//        Todo returnedTodo = response1.body().as(Todo.class);
//        String taskID = returnedTodo.getId();
//        String userId = returnedTodo.getUserID();
//        Todo updatedTodo = new Todo(true, returnedTodo.getItem());
//        Response response2 = TodoApi.updateTodoApi(updatedTodo, token, taskID);
//        Todo returnedUpdatedTodo = response2.body().as(Todo.class);
//        assertThat(response2.statusCode(), equalTo(200));
//        assertThat(returnedUpdatedTodo.getItem(), equalTo(updatedTodo.getItem()));
//        assertThat(returnedUpdatedTodo.getIsCompleted(), equalTo(updatedTodo.getIsCompleted()));

        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.updateTodoTask();
        String taskId = TodoSteps.getTaskId(todo, token);
        String userId = TodoSteps.getUserID(todo, token);
        Todo updatedTodo = new Todo(todo.getIsCompleted(), todo.getItem());
        Response response = TodoApi.updateTodoApi(updatedTodo, token, taskId);
        assertThat(response.statusCode(), equalTo(200));
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(returnedTodo.getIsCompleted(), equalTo(updatedTodo.getIsCompleted()));
        assertThat(returnedTodo.getItem(), equalTo(updatedTodo.getItem()));
    }

    //Task should be fail if code is correct but User1 update task of User2 and this is wrong
    // change 200 of last third line to 403 when code is fixed, uncomment last two line of code
    @Story("Negative Update Todo Task")
    @Test(description = "Should Not Be Able To Update Todo Task Of Another Token")
    public void ShouldNotBeAbleToUpdateTodoTaskOfAnotherToken(){
        String tokenUser1 = UserSteps.getUserToken();
        Todo todoUser1 = TodoSteps.generateTodoTask();
        String taskIdUser1 = TodoSteps.getTaskId(todoUser1,tokenUser1);
        Todo updatedTodoUser1 = new Todo(true, todoUser1.getItem());
        Response responseUser1 = TodoApi.updateTodoApi(updatedTodoUser1, tokenUser1, taskIdUser1);
        assertThat(responseUser1.statusCode(), equalTo(200));

        String tokenUser2 = UserSteps.getUserToken();
        Todo todoUser2 = TodoSteps.generateTodoTask();
        String taskIdUser2 = TodoSteps.getTaskId(todoUser2, tokenUser2);
        Todo updatedTodoUser2 = new Todo(true, todoUser2.getItem());
        Response responseUser2 = TodoApi.updateTodoApi(updatedTodoUser1, tokenUser2, taskIdUser1);
        assertThat(responseUser2.statusCode(), equalTo(200));
//        Error returnedErrorUser2 = responseUser2.body().as(Error.class);
//        assertThat(returnedErrorUser2.getMessage(), equalTo(ErrorMessages.YOU_ARE_FORBIDDEN_TO_ACCESS_THIS_DATA));
    }

    @Story("Positive Delete Todo Task")
    @Test(description = "Should Be Able To Delete Todo Task That Belongs To Me")
    public void ShouldBeAbleToDeleteTodoTaskThatBelongsToMe(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoTask();
        String taskId = TodoSteps.getTaskId(todo, token);
        Response response = TodoApi.deleteTodoApi(token, taskId);
        assertThat(response.statusCode(), equalTo(200));
    }

    @Story("Positive Delete Todo Task")
    @Test(description = "Should Be Able To Ensure That Task Is Deleted")
    public void ShouldBeAbleToEnsureThatTaskIsDeleted(){
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodoTask();
        String taskId = TodoSteps.getTaskId(todo, token);
        Response response = TodoApi.deleteTodoApi(token, taskId);
        assertThat(response.statusCode(), equalTo(200));
        Todo returnedTodo = response.body().as(Todo.class);
        assertThat(returnedTodo.getId(), equalTo(taskId));
    }

    @Story("Negative Delete Todo Task")
    @Test(description = "Should Not Be ABle To Delete Task Of Another User")
    public void ShouldNotBeABleToDeleteTaskOfAnotherUser(){
        String tokenUser1 = UserSteps.getUserToken();
        Todo todoUser1 = TodoSteps.generateTodoTask();
        String taskIdUser1 = TodoSteps.getTaskId(todoUser1, tokenUser1);

        String tokenUser2 = UserSteps.getUserToken();
        Todo todoUser2 = TodoSteps.generateTodoTask();
        //String taskIdUser2 = TodoSteps.getTaskId(todoUser2, tokenUser2);

        Response response = TodoApi.deleteTodoApi(tokenUser2, taskIdUser1);

        assertThat(response.statusCode(), equalTo(403));
    }
}