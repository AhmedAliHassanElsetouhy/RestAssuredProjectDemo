package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.data.Route;
import com.qacart.todo.data.ValidationData;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

@Feature("User Feature")
public class UsersTestCases {
    @Story("Positive Register Story")
    @Test(description = "Should Be Able To Register")
    public void shouldBeAbleToRegister() {
        User user = UserSteps.generateUser();
        User registerData = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        Response response = UserApi.register(registerData);
        User returnedUser = response.getBody().as(User.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));
    }

    @Story("Positive Register Story")
    @Test(description = "Should Be Able To Register With Time Less Than 20 Second")
    public void shouldBeAbleToRegisterWithTimeLessThan20Second() {
        User user = UserSteps.generateUser();
        User registerData = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        Response response = UserApi.register(registerData);
        assertThat(TimeUnit.MILLISECONDS.toSeconds(response.time()), lessThan(20L));
    }

    @Story("Positive Register Story")
    @Test(description = "ShouldBeAbleToRegisterUsingHeaderContentTypeApplicationJson")
    public void shouldBeAbleToRegisterUsingHeaderContentTypeApplicationJson(){
        User user = UserSteps.generateUser();
        Response response = UserApi.register(user);
        assertThat(response.contentType(), equalTo(Route.CONTENT_TYPE));
    }

    @Story("Positive Register Story")
    @Test(description = "ShouldBeAbleToRegisterAndResponseBodyContainsValue")
    public void shouldBeAbleToRegisterAndResponseBodyContainsValue(){
        User user = UserSteps.generateUser();
        Response response = UserApi.register(user);
        assertThat(response.getBody().asString(), not(nullValue()));
    }

    @Story("Negative Register Story")
    @Test(description = "Should Not Be Able To Send Request Using Wrong Register URL")
    public void ShouldNotBeAbleToSendRequestUsingWrongRegisterURL(){
        User user = UserSteps.generateUser();
        Response response = UserApi.wrongRegisterURL(user);
        assertThat(response.statusCode(), equalTo(404));
    }

    @Story("Negative Register Story")
    @Test(description = "Should Not Be Able To Register Using Same Email")
    public void shouldNotBeAbleToRegisterUsingSameEmail(){
        User user = UserSteps.getRegisteredUser();
        Response response = UserApi.register(user);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedError.getMessage(),equalTo(ErrorMessages.EMAIL_IS_AlREADY_EXISTED));
    }

    @Story("Negative Register Story")
    @Test(description = "Should Not Be Able To Register Without FirstName")
    public void shouldNotBeAbleToRegisterWithoutFirstName() {
        User user = UserSteps.generateUser();
        User registerData = new User(null, user.getLastName(), user.getEmail(), user.getPassword());
        Response response = UserApi.register(registerData);
        Error returnedError = response.getBody().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.FIRST_NAME_IS_REQUIRED));
    }

    @Story("Negative Register Story")
    @Test(description = "Should Not Be Able To Register Without LastName")
    public void shouldNotBeAbleToRegisterWithoutLastName() {
        User user = UserSteps.generateUser();
        User registerData = new User(user.getFirstName(), null, user.getEmail(), user.getPassword());
        Response response = UserApi.register(registerData);
        Error returnedError = response.getBody().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.LAST_NAME_IS_REQUIRED));
    }

    @Story("Negative Register Story")
    @Test(description = "Should Not Be Able To Register Without Email")
    public void shouldNotBeAbleToRegisterWithoutEmail(){
        User user = UserSteps.generateUser();
        User registerData = new User(user.getFirstName(), user.getLastName(), null, user.getPassword());
        Response response = UserApi.register(registerData);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.EMAIL_IS_REQUIRED));
    }

    @Story("Negative Register Story")
    @Test(description = "Should Not Be Able To Register Without Password")
    public void shouldNotBeAbleToRegisterWithoutPassword(){
        User user = UserSteps.generateUser();
        User registerData = new User(user.getFirstName(), user.getLastName(), user.getEmail(), null);
        Response response = UserApi.register(registerData);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.PASSWORD_IS_REQUIRED));
    }

    @Story("Negative Register Story")
    @Test(description = "Should Not Be Able To Register With Empty FirstName")
    public void shouldNotBeAbleToRegisterWithEmptyFirstName() {
        User user = UserSteps.generateUser();
        User registerData = new User(ValidationData.EMPTY, user.getLastName(), user.getEmail(), user.getPassword());
        Response response = UserApi.register(registerData);
        Error returnedError = response.getBody().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.FIRST_NAME_NOT_ALLOWED_TO_BE_EMPTY));
    }

    @Story("Negative Register Story ")
    @Test(description = "Should Not Be Able To Register With Empty LastName")
    public void shouldNotBeAbleToRegisterWithEmptyLastName() {
        User user = UserSteps.generateUser();
        User registerData = new User(user.getFirstName(), ValidationData.EMPTY, user.getEmail(), user.getPassword());
        Response response = UserApi.register(registerData);
        Error returnedError = response.getBody().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.LAST_NAME_NOT_ALLOWED_TO_BE_EMPTY));
    }

    @Story("Negative Register Story")
    @Test(description = "Should Not Be Able To Register With Empty Email")
    public void shouldNotBeAbleToRegisterWithEmptyEmail(){
        User user = UserSteps.generateUser();
        User registerData = new User(user.getFirstName(), user.getLastName(), ValidationData.EMPTY, user.getPassword());
        Response response = UserApi.register(registerData);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.EMAIL_NOT_ALLOWED_TO_BE_EMPTY));
    }

    @Story("Negative Register Story")
    @Test(description = "Should Not Be Able To Register With Invalid Email")
    public void shouldNotBeAbleToRegisterWithInvalidEmail(){
        User user = UserSteps.generateUser();
        User registerData = new User(user.getFirstName(), user.getLastName(), ValidationData.INVALID_EMAIL_FORMAT, user.getPassword());
        Response response = UserApi.register(registerData);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.EMAIL_MUST_BE_VALID_EMAIL));
    }

    @Story("Negative Register Story")
    @Test(description = "Should Not Be Able To Register With Empty Password")
    public void shouldNotBeAbleToRegisterWithEmptyPassword(){
        User user = UserSteps.generateUser();
        User registerData = new User(user.getFirstName(), user.getLastName(), user.getEmail(), ValidationData.EMPTY);
        Response response = UserApi.register(registerData);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.PASSWORD_NOT_ALLOWED_TO_BE_EMPTY));
    }

    @Story("Negative Register Story")
    @Test(description = "Should Not Be Able To Register With Invalid Password Length")
    public void shouldNotBeAbleToRegisterWithInvalidPasswordLength(){
        User user = UserSteps.generateUser();
        User registerData = new User(user.getFirstName(), user.getLastName(), user.getEmail(), ValidationData.INVALID_PASSWORD_LENGTH);
        Response response = UserApi.register(registerData);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.PASSWORD_LENGTH_MUST_BE_AT_LEAST_8_CHARS));
    }

    @Story("Positive Login Story")
    @Test(description = "Should Be Able To Login Using Valid Data")
    public void shouldBeAbleToLoginUsingValidData(){
        User user = UserSteps.getRegisteredUser();
        User loggenInUser = new User(user.getEmail(), user.getPassword());
        Response response = UserApi.login(loggenInUser);
        User returnedUser = response.body().as(User.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));
    }

    @Story("Negative Login Story")
    @Test(description = "Should Not Be Able To Login Using InValid Data")
    public void shouldNotBeAbleToLoginUsingInValidData(){
        User user = UserSteps.getRegisteredUser();
        User loggenInUser = new User(ValidationData.INVALID_EMAIL_FORMAT, ValidationData.INVALID_PASSWORD_LENGTH);
        Response response = UserApi.login(loggenInUser);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.PLEASE_FILL_CORRECT_PASSWORD));
    }

    @Story("Negative Login Story")
    @Test(description = "Should Not Be Able To Login Using Invalid Email And Valid Password")
    public void shouldNotBeAbleToLoginUsingInvalidEmailAndValidPassword() {
        User user = UserSteps.getRegisteredUser();
        User loggenInUser = new User(ValidationData.INVALID_EMAIL_FORMAT, user.getPassword());
        Response response = UserApi.login(loggenInUser);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.PLEASE_FILL_CORRECT_PASSWORD));
    }

    @Story("Negative Login Story")
    @Test(description = "Should Not Be Able To Login Using Empty Email")
    public void shouldNotBeAbleToLoginUsingEmptyEmail(){
        User user = UserSteps.getRegisteredUser();
        User loggenInUser = new User(ValidationData.EMPTY, user.getPassword());
        Response response = UserApi.login(loggenInUser);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.PLEASE_FILL_CORRECT_PASSWORD));
    }

    @Story("Negative Login Story")
    @Test(description = "Should Not Be Able To Login Without Email")
    public void shouldNotBeAbleToLoginWithoutEmail() {
        User user = UserSteps.getRegisteredUser();
        User loggenInUser = new User(null, user.getPassword());
        Response response = UserApi.login(loggenInUser);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.PLEASE_FILL_CORRECT_PASSWORD));
    }

    @Story("Negative Login Story")
    @Test(description = "Should Not Be Able To Login Using Invalid Password And Valid Email")
    public void shouldNotBeAbleToLoginUsingInvalidPasswordAndValidEmail() {
        User user = UserSteps.getRegisteredUser();
        User loggenInUser = new User(user.getEmail(), ValidationData.INVALID_PASSWORD_LENGTH);
        Response response = UserApi.login(loggenInUser);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.PLEASE_FILL_CORRECT_PASSWORD));
    }

    @Story("Negative Login Story")
    @Test(description = "Should Not Be Able To Login Using Empty Password")
    public void shouldNotBeAbleToLoginUsingEmptyPassword() {
        User user = UserSteps.getRegisteredUser();
        User loggenInUser = new User(user.getEmail(), ValidationData.EMPTY);
        Response response = UserApi.login(loggenInUser);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.PLEASE_FILL_CORRECT_PASSWORD));
    }

    @Story("Negative Login Story")
    @Test(description = "Should Not Be Able To Login Without Password")
    public void shouldNotBeAbleToLoginWithoutPassword() {
        User user = UserSteps.getRegisteredUser();
        User loggenInUser = new User(user.getEmail(), null);
        Response response = UserApi.login(loggenInUser);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.PLEASE_FILL_CORRECT_PASSWORD));
    }

    @Story("Negative Login Story")
    @Test(description = "Should Not Be Able To Login With MaleWare Input")
    public void shouldNotBeAbleToLoginWithMaleWareInput() {
        User user = UserSteps.getRegisteredUser();
        User loggenInUser = new User(user.getEmail(), ValidationData.MALE_WARE_ATTACK);
        Response response = UserApi.login(loggenInUser);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.THIS_EMAIL_AND_COMBINATION_IS_NOT_CORRECT));
    }

    @Story("Negative Login Story")
    @Test(description = "Should Not Be Able To Login With XXS Attack Input")
    public void shouldNotBeAbleToLoginWithXSSAttackInput() {
        User user = UserSteps.getRegisteredUser();
        User loggenInUser = new User(user.getEmail(), ValidationData.XSS_ATTACK);
        Response response = UserApi.login(loggenInUser);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.THIS_EMAIL_AND_COMBINATION_IS_NOT_CORRECT));
    }

    @Story("Negative Login Story")
    @Test(description = "Should Not Be Able To Login With Sql Injection Input")
    public void shouldNotBeAbleToLoginWithSqlInjectionInput() {
        User user = UserSteps.getRegisteredUser();
        User loggenInUser = new User(user.getEmail(), ValidationData.SQL_INJECTION_INPUT_DROP_TABLE);
        Response response = UserApi.login(loggenInUser);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.THIS_EMAIL_AND_COMBINATION_IS_NOT_CORRECT));
    }

    @Story("Negative Login Story")
    @Test(description = "Should Not Be Able To Login With Sql Injection Input2")
    public void shouldNotBeAbleToLoginWithSqlInjectionInput2() {
        User user = UserSteps.getRegisteredUser();
        User loggenInUser = new User(user.getEmail(), ValidationData.SQL_INJECTION_INPUT);
        Response response = UserApi.login(loggenInUser);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.THIS_EMAIL_AND_COMBINATION_IS_NOT_CORRECT));
    }
}