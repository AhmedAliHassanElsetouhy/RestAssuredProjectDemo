package com.qacart.todo.steps;

import com.github.javafaker.Faker;
import com.qacart.todo.apis.UserApi;
import com.qacart.todo.models.Todo;
import com.qacart.todo.models.User;
import io.restassured.response.Response;

public class UserSteps {
    public static User generateUser(){
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress();
//        String password = "iLikeQaCart";
        String password = faker.internet().password(8,16);
        return new User(firstName, lastName, email, password);
    }

    public static User getRegisteredUser(){
        User user = generateUser();
        UserApi.registerApi(user);
        return user;
    }

    public static String getUserToken(){
        User user = generateUser();
        Response response = UserApi.registerApi(user);
        User addedUser = response.body().as(User.class);
        return addedUser.getAccessToken();
//        return response.body().path("access_token");
    }

    public static Response login(String token){
        User user = UserSteps.getRegisteredUser();
        Response response = UserApi.loginApi(user);
        return response;
    }
}
