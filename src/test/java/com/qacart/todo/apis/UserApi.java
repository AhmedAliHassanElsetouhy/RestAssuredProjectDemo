package com.qacart.todo.apis;

import com.qacart.todo.base.Specs;
import com.qacart.todo.data.Route;
import com.qacart.todo.models.User;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class UserApi {
    public static Response registerApi(User user){
        return given()
                .spec(Specs.getRequestSpec())
                .body(user)
                .when()
                .post(Route.REGISTER_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response wrongRegisterApi(User user){
        return given()
                .spec(Specs.getRequestSpec())
                .body(user)
                .when()
                .post(Route.WRONG_REGISTER_ROUTE)
                .then()
                .log().all()
                .extract().response();
    }

    public static Response loginApi(User user){
        return
                given()
                        .spec(Specs.getRequestSpec())
                        .body(user)
                        .when()
                        .post(Route.LOGIN_ROUTE)
                        .then()
                        .log().all()
                        .extract().response();
    }
}
