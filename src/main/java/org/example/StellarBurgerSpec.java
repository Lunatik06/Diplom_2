package org.example;

import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class StellarBurgerSpec {
    static final String BASE_URI = "https://stellarburgers.nomoreparties.site/";
    private static final String BASE_PATH = "/api";

    public RequestSpecification spec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(BASE_PATH)
                //Добавил в спецификацию логирование ответов
                .filter(new ResponseLoggingFilter());
    }
}
