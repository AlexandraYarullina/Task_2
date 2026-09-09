package org.example.APITests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.example.APIRequest.APIBaseTest;
import org.example.APIRequest.UserRequest;
import org.example.DataUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Utils.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateUserTest {
    public Response response;
    private String accessToken;
    UserRequest userRequest = new UserRequest();
    DataUser dataUser = new DataUser(EMAIL, PASSWORD, NAME);

    @BeforeEach
    public void setUp() {
        if (userRequest == null) {
            throw new IllegalStateException("userRequest is null before setUp(). " +
                    "Ensure it's initialized in the field declaration or constructor.");
        }
        userRequest.setUp();
    }

    @Test
    @DisplayName("Создаём пользователя")
    public void createUserTest() {
        userRequest.setDataUser(dataUser);
        userRequest.createUser()
                .then().assertThat().body("success", equalTo(true))
                .statusCode(OK_STATUS_COD);
//        accessTokenResponse=response.then().extract().body().path("accessToken");
//        accessToken=accessTokenResponse.substring(7);
//        System.out.println(accessToken);

        //               .statusCode(OK_STATUS_COD);
//                .log().all();
//        System.out.println();
        // System.out.println(response.body().asString());
        // accessToken = userRequest.getAccessTokenUser();

//        Response response =
//                given()
//                        .header("Content-type", "application/json")
//                        .body(new DataUser("rterty","gu328hrg","user6"))
//                        .when()
//                        .post("api/v1/courier");
        // accessToken = userRequest.getAccessTokenUser();
        // response.then().statusCode(CREATE_STATUS_COD);


    }

    @Test
    @DisplayName("Создаём пользователя без email")
    @Description("В документации к API необходимо ввсети корректировки в документе")
    public void createUserWithoutPasswordTest() {
        userRequest.setDataUser(new DataUser("", PASSWORD, NAME));
        userRequest.createUser()
                .then().assertThat().body("success", equalTo(false))
                .statusCode(FORBIDDEN_STATUS_COD);
        //.statusCode(UNAUTHORIZED_STATUS_COD);
    }
    @Test
    @DisplayName("Создаём пользователя без пароля")
    @Description("В документации к API необходимо ввсети корректировки в документе")
    public void createUserWithoutEmailTest() {
        userRequest.setDataUser(new DataUser(EMAIL, "", NAME));
        userRequest.createUser()
                .then().assertThat().body("success", equalTo(false))
                .statusCode(FORBIDDEN_STATUS_COD);
        //.statusCode(UNAUTHORIZED_STATUS_COD);
    }

    @Test
    @DisplayName("Создаём пользователя без имени")
    @Description("В документации к API необходимо ввсети корректировки в документе")
    public void createUserWithoutNameTest() {
        userRequest.setDataUser(new DataUser(EMAIL, PASSWORD, ""));
        userRequest.createUser()
                .then().assertThat().body("success", equalTo(false))
                .statusCode(FORBIDDEN_STATUS_COD);
        //.statusCode(UNAUTHORIZED_STATUS_COD);
    }

    @Test
    @DisplayName("Создаём одинакового пользователя")
    public void checkDuplicateUserCreated() {
        userRequest.setDataUser(dataUser);
        userRequest.createUser()
                .then().assertThat().body("success", equalTo(true))
                .statusCode(OK_STATUS_COD);
        userRequest.createUser()
                .then().assertThat().body("success", equalTo(false))
                .statusCode(FORBIDDEN_STATUS_COD);
    }

    @AfterEach
    @DisplayName("Удаление пользователя по токену")
    public void deleteUser() {
        String accessToken =
                given()
                        .header("Content-type", "application/json")
                        .body(dataUser)
                        .when()
                        .post(LOGIN_ENDPOINT_USER)
                        .then().extract().body().path("accessToken");
        if (accessToken!=null) {
            userRequest.deleteUser(accessToken);
        }
    }

}
