package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static java.lang.Integer.MAX_VALUE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

public class InformationControllerTest extends RestAssuredTest {

    @Test
    public void shouldGetAll() {
        given().get("/information")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    public void shouldCreateNewPlayer() {
        JSONObject requestParams = new JSONObject();
        //int value = 4;

        //int value = ThreadLocalRandom.current().nextInt(20, MAX_VALUE); //definicja danych
        //requestParams.put("id", value);
        String myName = RandomStringUtils.randomAlphabetic(10);
        requestParams.put("name", myName);
        String country = "Poland";
        requestParams.put("nationality", country);
        requestParams.put("salary", 1000);

        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .body("nationality", equalTo(country))
                .body("salary", equalTo(1000))
                .body("name", equalTo(myName))
                .body("id", greaterThanOrEqualTo(0));
                //.body("id", equalTo(value));



    }

    @Test
        public void shouldDelete() { //delete
            given().delete("/information/11")
                    .then()
                    .log().all()
                    .statusCode(204);
    }
}

