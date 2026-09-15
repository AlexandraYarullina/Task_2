//Логин пользователя:
//        логин под существующим пользователем,
//        логин с неверным логином и паролем.

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
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserAuthorizationTest {
    UserRequest userRequest = new UserRequest();
    DataUser dataUser = new DataUser(RandomData.getRandomEmail(), RandomData.getRandomPassword(), RandomData.getRandomName());
    private String accessToken;

    @BeforeEach
    public void setUp() {
        userRequest.setUp();
        userRequest.setDataUser(dataUser);
        userRequest.createUser();
        accessToken = userRequest.loginUser().then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Авторизация пользователя")
    @Description("Проверка успешной авторизации пользователя, возвращается код 200 и success : true")
    public void checkAuthorizationUser() {
        userRequest.loginUser()
                .then().assertThat().statusCode(OK_STATUS_COD)
                .body("success",equalTo(true));
    }

    @Test
    @DisplayName("Авторизация незарегистрированного пользователя")
    @Description("Проверка авторизации несуществующего пользователя, возвращается код 401 и success : false")
    public void checkAuthorizationNonExistentUser() {
        dataUser.setEmail(RandomData.getRandomIncorrectEmail());
        dataUser.setPassword(RandomData.getRandomIncorrectPassword());
        userRequest.loginUser()
                .then().assertThat().statusCode(UNAUTHORIZED_STATUS_COD)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Авторизация пользователя c неверным логином")
    @Description("При попытке авторизации c неверным логином, возвращается код 401 и success : false")
    public void checkAuthorizationUserWithIncorrectLogin(){
        dataUser.setEmail(RandomData.getRandomIncorrectEmail());
        userRequest.loginUser()
                .then().assertThat().statusCode(UNAUTHORIZED_STATUS_COD)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Авторизация пользователя c неверным паролем")
    @Description("При попытке авторизации c неверным паролем, возвращается код 401 и success : false")
    public void checkAuthorizationUserWithIncorrectPassword(){
        dataUser.setPassword(RandomData.getRandomIncorrectPassword());
        userRequest.loginUser()
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
