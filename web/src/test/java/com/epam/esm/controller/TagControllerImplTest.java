package com.epam.esm.controller;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TagControllerImplTest {

    @Test
    void testGivenUrl() {
        get("/api/tags/1876").then().statusCode(200).assertThat()
                .body("name", equalTo("tag794"));
    }

    @Test
    public void whenRequestGet_thenOK(){
        when().request("GET", "/api/tags").then().statusCode(200);
    }

    @Test
    public void whenLogRequest_thenOK() {
        given().log().all()
                .when().get("/api/tags")
                .then().statusCode(200);
    }

    @Test
    public void whenLogResponse_thenOK() {
        when().get("/api/tags/1")
                .then().log().body().statusCode(200);
    }

}
