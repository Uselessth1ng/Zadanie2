package org.example;

import io.restassured.RestAssured;
import org.example.service.*;
import org.junit.Before;
import org.junit.Test;

public class CartTests extends BaseTest {

    private static String token;

    @Before
    public void before(){
        String name = "login", password = "parol";
        token = getToken(name, password);
    }

    @Test
    public void getCart()
    {
        RestAssured.given()
            .filter(new AuthFilter(token))
            .get("cart")
            .then().log().all()
            .assertThat().statusCode(200)
            .extract().as(Cart.class);
    }

    @Test
    public void addProductToCart(){
        RestAssured.given()
            .spec(requestSpecification)
            .filter(new AuthFilter(token))
            .body(AddProduct.builder().productId(1).quantity(3).build())
            .post("cart")
            .then().log().all().assertThat().statusCode(201)
            .extract().as(SuccessMessage.class);
    }

    @Test
    public void addNonExistingProductToCart(){
        RestAssured.given()
            .spec(requestSpecification)
            .filter(new AuthFilter(token))
            .body(AddProduct.builder().productId(235424234).quantity(3).build())
            .post("cart")
            .then().log().all().assertThat().statusCode(404)
            .extract().as(SuccessMessage.class);
    }

    @Test
    public void addProductToCartWithoutUser(){
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        RestAssured.given()
            .spec(requestSpecification)
            .filter(new AuthFilter(token))
            .body(AddProduct.builder().productId(1).quantity(1).build())
            .post("cart")
            .then().log().all().assertThat().statusCode(422)
            .extract().as(SuccessMessage.class);
    }

    @Test
    public void delExistingProduct()
    {
        RestAssured.given()
            .filter(new AuthFilter(token))
            .delete("cart/1")
            .then().log().all().assertThat().statusCode(200)
            .extract().as(SuccessMessage.class);
    }

    @Test
    public void delNonExistingProduct()
    {
        RestAssured.given()
            .spec(requestSpecification)
            .filter(new AuthFilter(token))
            .delete("cart/11231231")
            .then().log().all().assertThat().statusCode(404)
            .extract().as(SuccessMessage.class);
    }
}
