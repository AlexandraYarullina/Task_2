//Получение заказов конкретного пользователя:
//      авторизованный пользователь,
//      неавторизованный пользователь.

package org.example.APITests;

import org.example.APIRequest.OrderRequest;
import org.example.APIRequest.UserRequest;
import org.example.DataOrder;
import org.example.DataUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static Utils.Constants.*;

public class GetOrderUserTest {
    DataUser dataUser=new DataUser(EMAIL,PASSWORD,NAME);
    UserRequest userRequest=new UserRequest();
    OrderRequest orderRequest=new OrderRequest();
    public static List<String> ingredients=new ArrayList<>();
    private String accessToken;

    public void setUp() {
        userRequest.setUp();
        orderRequest.setUp();
        userRequest.setDataUser(dataUser);
        userRequest.createUser();
        accessToken = userRequest.loginUser().then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Получение списка заказав неавторизованного пользователя")
    public void orderCreateWithAuthorizationWithWrongIngredientshhjki() {
        ingredients.add("61c0c5a71d1f82001bdaaa6d");
        DataOrder dataOrder = new DataOrder(ingredients);
        orderRequest.setDataOrder(dataOrder);
        orderRequest.getOrdersWithoutAuthorization()
                .then().assertThat().statusCode(UNAUTHORIZED_STATUS_COD);
    }

    @Test
    @DisplayName("Получение списка заказав авторизованного пользователя")
    public void orderCreateWithAuthorizationWithWrongIngredientshhjkiweweqw() {
        userRequest.loginUser();
        ingredients.add("61c0c5a71d1f82001bdaaa6d");
        DataOrder dataOrder = new DataOrder(ingredients);
        orderRequest.setDataOrder(dataOrder);
        orderRequest.getOrdersWithAuthorization(accessToken)
                .then().assertThat().statusCode(OK_STATUS_COD);
    }

}
