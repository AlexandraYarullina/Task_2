//Получение заказов конкретного пользователя:
//      авторизованный пользователь,
//      неавторизованный пользователь.

package org.example.apitests;

import io.qameta.allure.Description;
import org.example.apirequest.OrderRequest;
import org.example.apirequest.UserRequest;
import org.example.DataOrder;
import org.example.DataUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.RandomData;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.*;
import static org.hamcrest.Matchers.equalTo;

public class GetOrderListUserTest {
    DataUser dataUser = new DataUser(RandomData.getRandomEmail(), RandomData.getRandomPassword(), RandomData.getRandomName());
    UserRequest userRequest=new UserRequest();
    OrderRequest orderRequest=new OrderRequest();
    public static List<String> ingredients=new ArrayList<>();
    private String accessToken;

    @BeforeEach
    public void setUp() {
        userRequest.setUp();
        orderRequest.setUp();
        userRequest.setDataUser(dataUser);
        userRequest.createUser();
        accessToken = userRequest.loginUser().then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Получение списка заказав неавторизованного пользователя")
    @Description("Проверка получения списка заказав неавторизованного пользователя, возвращается код 401")
    public void checkGetOrderWithoutAuthorization() {
        ingredients.add(INGREDIENT);
        DataOrder dataOrder = new DataOrder(ingredients);
        orderRequest.setDataOrder(dataOrder);
        orderRequest.createOrderWithoutAuthorization();
        orderRequest.getOrdersWithoutAuthorization()
                .then().assertThat().statusCode(UNAUTHORIZED_STATUS_COD)
                .body("success",equalTo(false));
    }

    @Test
    @DisplayName("Получение списка заказав авторизованного пользователя")
    @Description("Проверка получения списка заказав авторизованного пользователя, возвращается код 200")
    public void checkGetOrderWithAuthorization() {
        userRequest.loginUser();
        ingredients.add(INGREDIENT);
        DataOrder dataOrder = new DataOrder(ingredients);
        orderRequest.setDataOrder(dataOrder);
        orderRequest.createOrderWithAuthorization(accessToken);
        orderRequest.getOrdersWithAuthorization(accessToken)
                .then().assertThat().statusCode(OK_STATUS_COD)
                .body("success",equalTo(true));
    }

    @AfterEach
    @DisplayName("Удаление пользователя по токену")
    public void deleteUser() {
        if (accessToken!=null) {
            userRequest.deleteUser(accessToken);
        }
    }
}
