package com.phonebook.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class RecordControllerTest {

    public static Response response;

    @BeforeClass
    public static void setupURL() {
        RestAssured.baseURI = "http://localhost:8081/records";
    }

    @Test
    public void customTest() {
        response = when()
                .get("")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();
        System.out.println(response.asString());
        response.prettyPrint();
    }

    @Test
    public void postShouldReturnOkOnValidDataInput() {
        String myJson = "{" +
                "\"name\":\"Bruce\"," +
                "\"surname\":\"Wayne\"," +
                "\"phoneNumber\":\"5551245\"," +
                "\"email\":\"test1@at.com\"" +
                "}";

        response = given()
                .contentType("application/json")
                .body(myJson)
                .when()
                .post("")
                .then()
                .contentType(ContentType.JSON)  // check that the content type return from the API is JSON
                .extract()
                .response();
        System.out.println(response.asString());
        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test
    public void postShouldReturnErrorOnInvalidEmail() {
        String myJson = "{" +
                "\"name\":\"Bruce\"," +
                "\"surname\":\"Wayne\"," +
                "\"phoneNumber\":\"5551245\"," +
                "\"email\":\"emailWithoutAtSymbol\"" +
                "}";

        response = given()
                .contentType("application/json")
                .body(myJson)
                .when()
                .post("");
        Assert.assertEquals(response.getStatusCode(), 400);
    }

}