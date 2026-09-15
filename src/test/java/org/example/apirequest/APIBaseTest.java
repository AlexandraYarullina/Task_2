package org.example.apirequest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

import static utils.Constants.BASE_URI;

public class APIBaseTest {

    @BeforeEach
    public void setUp(){
        RestAssured.baseURI=BASE_URI;
    }
}
