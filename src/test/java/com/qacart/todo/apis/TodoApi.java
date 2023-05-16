package com.qacart.todo.apis;

import com.qacart.todo.base.Specs;
import com.qacart.todo.data.Route;
import com.qacart.todo.models.Todo;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class TodoApi {
    public static Response AddTodoApi(Todo todo, String token){
        return given()
                .spec(Specs.getRequestSpec())
                .body(todo)
                .auth().oauth2(token)
                .when()
                .post(Route.TODO_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getSpecificTodoApi(String token, String id){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .get(Route.TODO_ROUTE + id)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response getAllTodosApi(String token){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .get(Route.TODO_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response updateTodoApi(Todo todo, String token, String taskId){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .body(todo)
                .when()
                .put(Route.TODO_ROUTE + taskId)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response deleteTodoApi(String token, String taskId){
        return given()
                .spec(Specs.getRequestSpec())
                .auth().oauth2(token)
                .when()
                .delete(Route.TODO_ROUTE + taskId)
                .then()
                .log().all()
                .extract().response();
    }
}
