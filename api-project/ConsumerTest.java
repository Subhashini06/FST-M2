package liveProject;


import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
    //Headers
    Map<String, String> reqHeaders = new HashMap<>();
    //Set the resource path
    String resourcePath = "/api/users";

    @Pact(consumer = "UserConsumer", provider = "UserProvider")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        //Set headers
        reqHeaders.put("Content-Type", "application/json");
        //Create the body of the request and response
        DslPart reqResBody = new PactDslJsonBody()
                .numberType("id")
                .stringType("firstName", "Subhashini")
                .stringType("lastName", "rangaraju")
                .stringType("email", "subhashini@test.com");

        //Create the interaction
        return builder.given("Request to create a user")
                .uponReceiving("Request to create a user")
                .method("POST")
                .path(resourcePath)
                .headers(reqHeaders)
                .body(reqResBody)
                .willRespondWith()
                .status(201)
                .body(reqResBody)
                .toPact();
    }
        @Test
        @PactTestFor(providerName = "UserProvider", port = "8282")
        public void consumerTest() {
        //Set BaseURI
        String baseURI = "http://localhost:8282";

        //Create request body
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("id", 123);
        reqBody.put("firstName", "subhashini");
        reqBody.put("lastName","rangaraju");
        reqBody.put("email", "subhashini@test.com");

        //Generate response
        given().headers(reqHeaders).body(reqBody).
        when().post(baseURI + resourcePath).
        then().statusCode(201).log().all();
        }


    }



