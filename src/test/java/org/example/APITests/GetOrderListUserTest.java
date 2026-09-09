//Получение заказов конкретного пользователя:
//      авторизованный пользователь,
//      неавторизованный пользователь.

package org.example.APITests;

import jdk.jfr.Description;
import org.example.APIRequest.OrderRequest;
import org.example.APIRequest.UserRequest;
import org.example.DataOrder;
import org.example.DataUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static Utils.Constants.*;

public class GetOrderListUserTest {
    DataUser dataUser=new DataUser(EMAIL,PASSWORD,NAME);
    UserRequest userRequest=new UserRequest();
    OrderRequest orderRequest=new OrderRequest();
    public static List<String> ingredients=new ArrayList<>();
    private String accessToken;
    private String ingredient="61c0c5a71d1f82001bdaaa6d";

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
        ingredients.add(ingredient);
        DataOrder dataOrder = new DataOrder(ingredients);
        orderRequest.setDataOrder(dataOrder);
        orderRequest.createOrderWithoutAuthorization();
        orderRequest.getOrdersWithoutAuthorization()
                .then().assertThat().statusCode(UNAUTHORIZED_STATUS_COD);
    }

    @Test
    @DisplayName("Получение списка заказав авторизованного пользователя")
    @Description("Проверка получения списка заказав авторизованного пользователя, возвращается код 200")
    public void checkGetOrderWithAuthorization() {

        userRequest.loginUser();
        ingredients.add(ingredient);
        DataOrder dataOrder = new DataOrder(ingredients);
        orderRequest.setDataOrder(dataOrder);
        orderRequest.createOrderWithAuthorization(accessToken);
        orderRequest.getOrdersWithAuthorization(accessToken)
                .then().assertThat().statusCode(OK_STATUS_COD);
    }

    @AfterEach
    @DisplayName("Удаление пользователя по токену")
    public void deleteUser() {
        if (accessToken!=null) {
            userRequest.deleteUser(accessToken);
        }
    }

}
