package org.example;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.example.service.BaseTest;
import org.example.service.Product;
import org.junit.Test;
import java.util.List;

public class ProductTests extends BaseTest {

    @Test
    public void getProductsList()
    {
        List<Product> ProdList = RestAssured.given()
                .get("products")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().as(new TypeRef<>() {});
    }

    @Test
    public void getExistingProduct()
    {
        List<Product> Prod = RestAssured.given()
                .get("products/" + 1)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().as(new TypeRef<>() {});
    }

    @Test
    public void getNonExistingProduct()
    {
        int id = 123423423;
        RestAssured.given()
                .get("products/" + id)
                .then().log().all().assertThat().statusCode(404);
    }
}
