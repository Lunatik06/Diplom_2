package org.example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.core.Is.is;

public class UserAssertions {
    @Step("Проверить, что создание пользователя прошло успешно")
    public String createdSuccessfully(ValidatableResponse authResponse) {
        String accessToken = authResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true))
                .extract()
                .path("accessToken");
        return accessToken;
    }

    @Step("Проверить, что авторизация прошла успешно")
    public String logedInSuccessfully(ValidatableResponse loginResponse) {
        String accessToken = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true))
                .extract()
                .path("accessToken");
        return accessToken;
    }

    @Step("Проверить, что удаление пользователя прошло успешно")
    public void deletedSuccessfully(ValidatableResponse Response) {
        Response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED)
                .body("success", is(true))
                .body("message", is("User successfully removed"));
    }

    @Step("Проверить, что нельзя создать пользователя с уже имеющимися данными")
    public void createdUserTwice(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("success", is(false))
                .body("message", is("User already exists"));
    }

    @Step("Проверить, что нельзя создать пользователя без обязательных параметров")
    public void createdWithoutObligateParam(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"));
    }

    @Step("Проверить, что нельзя авторизоватья с некорректными параметрами")
    public void loginWithWrongParam(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("email or password are incorrect"));
    }

    @Step("Проверить, что данные пользователя можно изменить с авторизацией")
    public void changeDataSuccessfully(ValidatableResponse Response) {
        Response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true));
    }

    @Step("Проверить, что данные пользователя нельзя изменить без авторизации")
    public void changeDataWithoutAuth(ValidatableResponse Response) {
        Response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("You should be authorised"));
    }
}
