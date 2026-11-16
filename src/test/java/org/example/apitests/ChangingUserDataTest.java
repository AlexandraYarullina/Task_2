//Изменение данных пользователя:
//        с авторизацией,
//        без авторизации,
//        Для обеих ситуаций нужно проверить, что любое поле можно изменить. Для неавторизованного пользователя — ещё и то, что система вернёт ошибку.

package org.example.apitests;


import io.qameta.allure.Description;
import org.example.apirequest.UserRequest;
import org.example.DataUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.RandomData;

import static utils.Constants.*;
import static org.hamcrest.Matchers.equalTo;

public class ChangingUserDataTest {

    UserRequest userRequest = new UserRequest();
    DataUser dataUser = new DataUser(RandomData.getRandomEmail(), RandomData.getRandomPassword(), RandomData.getRandomName());
    private String accessToken;

    @BeforeEach
    public void setUp(){
        userRequest.setUp();
        userRequest.setDataUser(dataUser);
        userRequest.createUser();
        accessToken = userRequest.loginUser().then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Обновление поля email авторизованного пользователя")
    @Description("Проверка обновления поля email авторизованного пользователя, возвращается код 200")
    public void checkUpdatingUserEmailWithAuthorization(){
        userRequest.loginUser();
        dataUser.setEmail(RandomData.getRandomEmail());
        userRequest.updateUserData(accessToken)
                .then().assertThat().statusCode(OK_STATUS_COD)
                .body("success",equalTo(true));
    }

    @Test
    @DisplayName("Обновление поля name авторизованного пользователя")
    @Description("Проверка обновления поля name авторизованного пользователя, возвращается код 200")
    public void checkUpdatingUserNameWithAuthorization(){
        userRequest.loginUser();
        dataUser.setName(RandomData.getRandomName());
        userRequest.updateUserData(accessToken)
                .then().assertThat().statusCode(OK_STATUS_COD)
                .body("success",equalTo(true));
    }

    @Test
    @DisplayName("Обновление поля email неавторизованного пользователя")
    @Description("Проверка обновления поля email неавторизованного пользователя, возвращается код 401")
    public void checkUpdatingUserEmailWithoutAuthorization() {
        String accessTokenWithoutAuthorization = "";
        dataUser.setEmail(RandomData.getRandomEmail());
        userRequest.updateUserData(accessTokenWithoutAuthorization)
                .then().assertThat().statusCode(UNAUTHORIZED_STATUS_COD)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Обновление поля name неавторизованного пользователя")
    @Description("Проверка обновления поля name неавторизованного пользователя, возвращается код 401")
    public void checkUpdatingUserNameWithoutAuthorization(){
        String accessTokenWithoutAuthorization="";
        dataUser.setName(RandomData.getRandomName());
        userRequest.updateUserData(accessTokenWithoutAuthorization)
                .then().assertThat().statusCode(UNAUTHORIZED_STATUS_COD)
                .body("success", equalTo(false));
    }

    @AfterEach
    @DisplayName("Удаление пользователя по токену")
    public void deleteUser() {
        if (accessToken!=null) {
            userRequest.deleteUser(accessToken);
        }
    }
}
