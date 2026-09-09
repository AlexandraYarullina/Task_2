package org.example.APIRequest;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.DataUser;

import static Utils.Constants.*;
import static io.restassured.RestAssured.given;

public class UserRequest extends APIBaseTest {
    private DataUser dataUser;

    public void setDataUser(DataUser dataUser) {
        this.dataUser = dataUser;
    }

    @Step("Создание пользователя")
    public Response createUser() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(dataUser)
                        .when()
                        .post(CREATE_ENDPOINT_USER);
        return response;

    }

    @Step("Авторизация пользователя")
    public Response loginUser() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(dataUser)
                        .when()
                        .post(LOGIN_ENDPOINT_USER);
        return response;

    }

    @Step("Обновление данных пользователя")
    public Response updateUserData(String accessToken) {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .header("Authorization", accessToken)
                        .body(dataUser)
                        .when()
                        .patch(CHANGES_ENDPOINT_USER);
        return response;
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken) {
        Response response =
                given()
                        .header("Authorization", accessToken)
                        .when()
                        .delete(CHANGES_ENDPOINT_USER);
        return response;
    }
}

