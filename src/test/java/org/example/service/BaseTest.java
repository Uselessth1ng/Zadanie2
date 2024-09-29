package org.example.service;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseTest {

    private static String token;
    public static RequestSpecification requestSpecification;

    static {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "http://9b142cdd34e.vps.myjino.ru:49268/";
        requestSpecification = RestAssured.given().contentType(ContentType.JSON);
    }

    public static void registerUser(String name, String password)
    {
        RestAssured.given()
                .spec(requestSpecification)
                .body(new Login(name, password))
                .post("register");
    }

    public static String getToken(String name, String password){
        token = RestAssured.given()
                .spec(requestSpecification)
                .body(new Login(name, password))
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().body().jsonPath().getString("access_token");
        return token;
    }
}
