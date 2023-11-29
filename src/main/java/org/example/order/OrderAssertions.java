package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class OrderAssertions {

    //Добавил аннотации Step
    @Step("Проверить, что заказ создался успешно")
    public void createdOrderSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true));
    }

    @Step("Проверить, что нельзя создать заказ без ингредиентов")
    public void createdOrderWithoutIngredients(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"));
    }

    @Step("Проверить, что нельзя создать заказ с некорректными параметрами")
    public void createdOrderWithWrongData(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Step("Проверить, что можно получить список заказов с атворизацией")
    public void getOrderListWithAuthSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("orders", notNullValue())
                .body("success", is(true));
    }

    @Step("Проверить, что нельзя получить список заказов без авторизации")
    public void getOrderListWithoutAuth(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("You should be authorised"));
    }
}