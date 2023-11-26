package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.StellarBurgerSpec;

import java.util.Map;

public class OrderAction extends StellarBurgerSpec {
    static final String ORDERS_PATH = "/orders";


    @Step("Создать заказ авторизованным пользователем")
    public ValidatableResponse createOrder(String accessToken) {
        return spec()
                .body(Order.orderDatas)
                .headers(Map.of("Authorization", accessToken))
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

    @Step("Создать заказ неавторизованным пользователем")
    public ValidatableResponse createOrder() {
        return spec()
                .body(Order.orderDatas)
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

    @Step("Создать заказ без ингредиентов неавторизованным пользователем")
    public ValidatableResponse createOrderWithoutIngredients() {
        return spec()
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

    @Step("Создать заказ неавторизованным пользователем")
    public ValidatableResponse createOrderWithWrongData() {
        return spec()
                .body(Order.wrongOrderDatas)
                .when()
                .post(ORDERS_PATH)
                .then().log().all();
    }

    @Step("Получить список заказов авторизованным пользователем")
    public ValidatableResponse getOrdersWithAuth(String accessToken) {
        return spec()
                .headers(Map.of("Authorization", accessToken))
                .when()
                .get(ORDERS_PATH)
                .then().log().all();
    }

    @Step("Получить список заказов неавторизованным пользователем")
    public ValidatableResponse getOrdersWithoutAuth() {
        return spec()
                .when()
                .get(ORDERS_PATH)
                .then().log().all();
    }

}