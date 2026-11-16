//Создание пользователя:
//        создать уникального пользователя;
//        создать пользователя, который уже зарегистрирован;
//        создать пользователя и не заполнить одно из обязательных полей.

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
import static org.hamcrest.Matchers.*;

public class UserRegistrationTest {
    UserRequest userRequest = new UserRequest();
    DataUser dataUser = new DataUser(RandomData.getRandomEmail(), RandomData.getRandomPassword(), RandomData.getRandomName());
    private String accessToken;

    @BeforeEach
    public void setUp() {
        userRequest.setUp();
        userRequest.setDataUser(dataUser);
    }

    @Test
    @DisplayName("Регистрация пользователя")
    @Description("Проверка успешной регистрации пользователя, возвращается код 200 и success : true")
    public void createUserTest() {
        userRequest.createUser()
                .then().assertThat().statusCode(OK_STATUS_COD)
                .body("success",equalTo(true));
    }

    @Test
    @DisplayName("Регистрация пользователя без email")
    @Description("При попытке регистрации без логина, возвращается код 403 и success : false")
    public void checkCreateUserWithoutPassword() {
        dataUser.setEmail("");
        userRequest.createUser()
                .then().assertThat().statusCode(FORBIDDEN_STATUS_COD)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Регистрация пользователя без пароля")
    @Description("При попытке регистрации без пароля, возвращается код 403 и success : false")
    public void checkCreateUserWithoutEmail() {
        dataUser.setPassword("");
        userRequest.createUser()
                .then().assertThat().statusCode(FORBIDDEN_STATUS_COD)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Регистрация пользователя без имени")
    @Description("При попытке регистрации без имени, возвращается код 403 и success : false")
    public void checkCreateUserWithoutName() {
        dataUser.setName("");
        userRequest.createUser()
                .then().assertThat().statusCode(FORBIDDEN_STATUS_COD)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Регистрация одинакового пользователя")
    @Description("При попытке регистрации одинакового пользователя, возвращается код 403 и success : false")
    public void checkCreateDuplicateUser() {
        userRequest.createUser();
        userRequest.createUser()
                .then().assertThat().statusCode(FORBIDDEN_STATUS_COD)
                .body("success", equalTo(false));
    }

    @AfterEach
    @DisplayName("Удаление пользователя по токену")
    public void deleteUser() {
        accessToken = userRequest.loginUser().then().extract().path("accessToken");
        if (accessToken!=null) {
            userRequest.deleteUser(accessToken);
        }
    }
}
