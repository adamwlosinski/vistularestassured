package org.vistula.restassured.pet;



import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;
import org.vistula.restassured.RestAssuredTest;

public class PetPostGetScenarioTest extends RestAssuredTest {


@Test
public void shouldGetPet() {
    JSONObject requestParams = new JSONObject();
    int value = ThreadLocalRandom.current().nextInt(20, Integer.MAX_VALUE); //definicja danych
    requestParams.put("id", value);
    String randomName = RandomStringUtils.randomAlphabetic(10);
    requestParams.put("name", randomName);

    createNewPet(requestParams);
    {
        getItem(value, randomName);

        deletePet(value);
    }
}

    private void deletePet(int value) { //delete
        given().delete("/pet/" + value)
                .then()
                .log().all()
                .statusCode(204);
    }

    private void getItem(int value, String randomName) {  //get
        given().get("/pet/" + value)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(value))
                .body("name", equalTo(randomName));
    }

    private void createNewPet(JSONObject requestParams) {   //post
        given().header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);
    }
    }