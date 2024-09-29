package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.example.service.*;
import org.junit.Before;
import org.junit.Test;

public class CartTests extends BaseTest {

    private static String token;
    private static ObjectMapper mapper = new ObjectMapper();

    @Before
    public void before(){
        String name = "login", password = "parol";
        token = getToken(name, password);
    }

    @Test
    public void getCart()
    {
        Cart gCart= RestAssured.given()
            .filter(new AuthFilter(token))
            .get("cart")
            .then().log().all()
            .assertThat().statusCode(200)
            .extract().as(Cart.class);
    }

    @Test
    public void addProductToCart() throws JsonProcessingException {
        String body = mapper.writeValueAsString(AddProduct.builder().productId(1).quantity(3).build());
        SuccessMessage msg = RestAssured.given()
            .spec(requestSpecification)
            .filter(new AuthFilter(token))
            .body(body)
            .post("cart")
            .then().log().all().assertThat().statusCode(201)
            .extract().as(SuccessMessage.class);
    }

    @Test
    public void addNonExistingProductToCart() throws JsonProcessingException {
        String body = mapper.writeValueAsString(AddProduct.builder().productId(235424234).quantity(3).build());
        SuccessMessage msg = RestAssured.given()
            .spec(requestSpecification)
            .filter(new AuthFilter(token))
            .body(body)
            .post("cart")
            .then().log().all().assertThat().statusCode(404)
            .extract().as(SuccessMessage.class);
    }

    @Test
    public void addProductToCartWithoutUser() throws JsonProcessingException {
        String body = mapper.writeValueAsString(AddProduct.builder().productId(1).quantity(1).build());
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        SuccessMessage msg = RestAssured.given()
                .spec(requestSpecification)
                .filter(new AuthFilter(token))
                .body(body)
                .post("cart")
                .then().log().all().assertThat().statusCode(422)
                .extract().as(SuccessMessage.class);
    }

    @Test
    public void delExistingProduct()
    {
        SuccessMessage msg = RestAssured.given()
                .filter(new AuthFilter(token))
                .delete("cart/1")
                .then().log().all().assertThat().statusCode(200)
                .extract().as(SuccessMessage.class);
    }

    @Test
    public void delNonExistingProduct()
    {
        SuccessMessage msg = RestAssured.given()
                .spec(requestSpecification)
                .filter(new AuthFilter(token))
                .delete("cart/11231231")
                .then().log().all().assertThat().statusCode(404)
                .extract().as(SuccessMessage.class);
    }

}
