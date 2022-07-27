package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.oauth2;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class GitHubProject {
    //Request and Response specification
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    String sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCOy8ZGkPH0EcYs/055Ol9tx0geHY85hdQYDXVSF5adyBtbPFMN09OK21H1bm/vAAMf2iaiH+sWSDhlA0xVIOoqzF5bqPi9vX7L+s9RyviiO9nh3Dkkj3HSImB10j1/ISsmtz1ow47A6PNNRxEetWGJ/RRZLCyd463/yqVFuk7Vb0VC+LsszGzHkTL9J1hdpOYtFp1/QtLiBy79FndVe7Pjq04nHI9qa53pkcq6BLTDxkWDtG9FpJJMyPmQfLUAVqHElsq6/4fWBeFBMEYnv0+D4JwGQm9k5XrxahIUon3ib/qIB+sU1IYoxv3vCyVyJO6kq9EAnlAlqP1G6UhONmnH";
    int KeyID;

    @BeforeClass
    public void setUp() {

        //Create request specification
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAuth(oauth2("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"))
                .setBaseUri("https://api.github.com")
                .build();

    }
    @Test(priority = 1)
    public void postKeys() {
        String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"" + sshKey + "\"}";

        Response response = given().spec(requestSpec) // Use requestSpec
                .body(reqBody) // Send request body
                .when().post("/user/keys");

        System.out.println(response.getBody().asString());
        KeyID = response.then().extract().path("id");

        response.then().statusCode(201).log().all();
    }

    @Test
    public void getKeys() {

    }
    }

