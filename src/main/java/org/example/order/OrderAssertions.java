package org.example.order;

import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class OrderAssertions {

    public void createdOrderSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true))
        ;

    }

    public void createdOrderWithoutIngredients(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"))
        ;

    }

    public void createdOrderWithWrongData(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        ;

    }

    public void getOrderListWithAuthSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("orders", notNullValue())
                .body("success", is(true))
        ;

    }

    public void getOrderListWithoutAuth(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("You should be authorised"))
        ;

    }


}