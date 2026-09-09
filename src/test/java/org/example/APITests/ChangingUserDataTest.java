//Изменение данных пользователя:
//        с авторизацией,
//        без авторизации,
//        Для обеих ситуаций нужно проверить, что любое поле можно изменить. Для неавторизованного пользователя — ещё и то, что система вернёт ошибку.

package org.example.APITests;

import jdk.jfr.Description;
import org.example.APIRequest.UserRequest;
import org.example.DataUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Utils.Constants.*;
import static org.hamcrest.Matchers.equalTo;

public class ChangingUserDataTest {

    UserRequest userRequest = new UserRequest();
    DataUser dataUser = new DataUser(EMAIL, PASSWORD, NAME);
    private String accessToken;
    private String email="new_vita@yandex.ru";
    private String name="vita";


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
        dataUser.setEmail(email);
        userRequest.updateUserData(accessToken)
                .then().assertThat().body("success",equalTo(true))
                .statusCode(OK_STATUS_COD);
    }

    @Test
    @DisplayName("Обновление поля name авторизованного пользователя")
    @Description("Проверка обновления поля name авторизованного пользователя, возвращается код 200")
    public void checkUpdatingUserNameWithAuthorization(){
        userRequest.loginUser();
        dataUser.setName(name);
        userRequest.updateUserData(accessToken)
                .then().assertThat().body("success",equalTo(true))
                .statusCode(OK_STATUS_COD);
    }

    @Test
    @DisplayName("Обновление поля email неавторизованного пользователя")
    @Description("Проверка обновления поля email неавторизованного пользователя, возвращается код 401")
    public void checkUpdatingUserEmailWithoutAuthorization(){
      String accessTokenWithoutAuthorization="";
        dataUser.setEmail(email);
        userRequest.updateUserData(accessTokenWithoutAuthorization)
                .then().assertThat().body("success",equalTo(false))
                .statusCode(UNAUTHORIZED_STATUS_COD);
    }

    @Test
    @DisplayName("Обновление поля name неавторизованного пользователя")
    @Description("Проверка обновления поля name неавторизованного пользователя, возвращается код 401")
    public void checkUpdatingUserNameWithoutAuthorization(){
        String accessTokenWithoutAuthorization="";
        dataUser.setName(name);
        userRequest.updateUserData(accessTokenWithoutAuthorization)
                .then().assertThat().body("success",equalTo(false))
                .statusCode(UNAUTHORIZED_STATUS_COD);
    }

    @AfterEach
    @DisplayName("Удаление пользователя по токену")
    public void deleteUser() {
        if (accessToken!=null) {
            userRequest.deleteUser(accessToken);
        }
    }

}
