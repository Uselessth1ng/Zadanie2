package org.example;

import io.restassured.RestAssured;
import org.example.service.BaseTest;
import org.example.service.Login;
import org.junit.Test;


public class UserTests extends BaseTest {

    private String token;

    @Test
    public void testLogExistingUser()
    {
        String name = "login", password = "parol";
        registerUser(name, password);
        token = getToken(name, password );
    }

    @Test
    public void testLogNonExistingUser()
    {
        String name = "nonExistinglogin", password = "nonExistingparol";
        RestAssured.given()
                .spec(requestSpecification)
                .body(new Login(name, password))
                .post("login")
                .then().log().all()
                .assertThat().statusCode(401);
    }

    @Test
    public void testLoginWithWrongPassword()
    {
        String name = "login", password = "parol1";
        RestAssured.given()
                .spec(requestSpecification)
                .body(new Login(name, password))
                .post("login")
                .then().log().all()
                .assertThat().statusCode(401);
    }
}
