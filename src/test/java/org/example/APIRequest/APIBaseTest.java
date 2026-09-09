package org.example.APIRequest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

import static Utils.Constants.BASE_URI;

public class APIBaseTest {

    @BeforeEach
    public void setUp(){
        RestAssured.baseURI=BASE_URI;
    }

}
