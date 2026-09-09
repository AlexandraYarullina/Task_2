//Создание пользователя:
//        создать уникального пользователя;
//        создать пользователя, который уже зарегистрирован;
//        создать пользователя и не заполнить одно из обязательных полей.

package org.example.APITests;

import jdk.jfr.Description;
import org.example.APIRequest.UserRequest;
import org.example.DataUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Utils.Constants.*;
import static org.hamcrest.Matchers.*;

public class UserRegistrationTest {
    UserRequest userRequest = new UserRequest();
    DataUser dataUser = new DataUser(EMAIL, PASSWORD, NAME);
    private String accessToken;

    @BeforeEach
    public void setUp() {

        userRequest.setUp();
        userRequest.setDataUser(dataUser);
        accessToken = userRequest.loginUser().then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Регистрация пользователя")
    @Description("Проверка успешной регистрации пользователя, возвращается код 200 и success : true")
    public void createUserTest() {
        userRequest.createUser()
                .then().assertThat().body("success", equalTo(true))
                .statusCode(OK_STATUS_COD);
    }

    @Test
    @DisplayName("Регистрация пользователя без email")
    @Description("При попытке регистрации без логина, возвращается код 403 и success : false")
    public void checkCreateUserWithoutPassword() {
        dataUser.setEmail("");
        userRequest.createUser()
                .then().assertThat().body("success", equalTo(false))
                .statusCode(FORBIDDEN_STATUS_COD);
    }

    @Test
    @DisplayName("Регистрация пользователя без пароля")
    @Description("При попытке регистрации без пароля, возвращается код 403 и success : false")
    public void checkCreateUserWithoutEmail() {
        dataUser.setPassword("");
        userRequest.createUser()
                .then().assertThat().body("success", equalTo(false))
                .statusCode(FORBIDDEN_STATUS_COD);
    }

    @Test
    @DisplayName("Регистрация пользователя без имени")
    @Description("При попытке регистрации без имени, возвращается код 403 и success : false")
    public void checkCreateUserWithoutName() {
        dataUser.setName("");
        userRequest.createUser()
                .then().assertThat().body("success", equalTo(false))
                .statusCode(FORBIDDEN_STATUS_COD);
    }

    @Test
    @DisplayName("Регистрация одинакового пользователя")
    @Description("При попытке регистрации одинакового пользователя, возвращается код 403 и success : false")
    public void checkCreateDuplicateUser() {
        userRequest.createUser();
        userRequest.createUser()
                .then().assertThat().body("success", equalTo(false))
                .statusCode(FORBIDDEN_STATUS_COD);
    }

    @AfterEach
    @DisplayName("Удаление пользователя по токену")
    public void deleteUser() {
        if (accessToken!=null) {
            userRequest.deleteUser(accessToken);
        }
    }

}
