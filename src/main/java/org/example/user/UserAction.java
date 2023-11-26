package org.example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.StellarBurgerSpec;

import java.util.Map;

public class UserAction extends StellarBurgerSpec {
    static final String USER_PATH = "/auth";

    @Step("Создать пользователя")
    public ValidatableResponse create(User user) {
        return spec()
                .body(user)
                .when()
                .post(USER_PATH + "/register")
                .then().log().all();
    }

    @Step("Авторизоваться пользователем")
    public ValidatableResponse login(Credentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(USER_PATH + "/login")
                .then().log().all();
    }

    @Step("Авторизоваться пользователем")
    public ValidatableResponse login(User user) {
        return spec()
                .body(user)
                .when()
                .post(USER_PATH + "/login")
                .then().log().all();
    }

    @Step("Удалить пользователя")
    public ValidatableResponse delete(String accessToken) {
        return spec()
                .headers(Map.of("Authorization", accessToken))
                .when()
                .delete(USER_PATH + "/user")
                .then().log().all();
    }

    @Step("Получить данные пользователя")
    public ValidatableResponse get(String accessToken) {
        return spec()
                .headers(Map.of("Authorization", accessToken))
                .when()
                .get(USER_PATH + "/user")
                .then().log().all();
    }


    @Step("Изменить данные пользователя с авторизацией")
    public ValidatableResponse changeWithAuth(User user, String accessToken) {
        return spec()
                .body(user)
                .headers(Map.of("Authorization", accessToken))
                .when()
                .patch(USER_PATH + "/user")
                .then().log().all();
    }

    @Step("Изменить данные пользователя без авторизации")
    public ValidatableResponse changeWithoutAuth(User user) {
        return spec()
                .body(user)
                .when()
                .patch(USER_PATH + "/user")
                .then().log().all();
    }


}
