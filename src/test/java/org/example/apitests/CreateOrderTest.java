//Создание заказа:
//        с авторизацией,
//        без авторизации,
//        с ингредиентами,
//        без ингредиентов,
//        с неверным хешем ингредиентов.

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

public class CreateOrderTest {
    DataUser dataUser = new DataUser(RandomData.getRandomEmail(), RandomData.getRandomPassword(), RandomData.getRandomName());
    UserRequest userRequest=new UserRequest();
    OrderRequest orderRequest=new OrderRequest();
    public static List<String> ingredients = new ArrayList<>();
    private String accessToken;

    @BeforeEach
    public void setUp() {
        orderRequest.setUp();
        userRequest.setUp();
        userRequest.setDataUser(dataUser);
        userRequest.createUser();
        accessToken = userRequest.loginUser().then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем с ингридиентами")
    @Description("При попытке создания заказа не авторизованным пользователем с ингридиентами, возвращается код 200 и success : true")
    public void checkCreateOrderWithoutAuthorizedWithIngredient() {
        ingredients.clear();
        ingredients.add(INGREDIENT);
        DataOrder dataOrder = new DataOrder(ingredients);
        orderRequest.setDataOrder(dataOrder);
        orderRequest.createOrderWithoutAuthorization()
                .then().assertThat().statusCode(OK_STATUS_COD)
                .body("success",equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем без ингредиентов")
    @Description("При попытке создания заказа не авторизованным пользователем без ингредиентов, возвращается код 400")
    public void checkCreateOrderNoIngredientsWithoutAuthorization() {
        ingredients.clear();
        DataOrder dataOrder = new DataOrder(ingredients);
        orderRequest.setDataOrder(dataOrder);
        orderRequest.createOrderWithoutAuthorization()
                .then().assertThat().statusCode(BAD_REQUEST_STATUS_COD)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем с ингредиентами")
    @Description("При попытке создания заказа авторизованным пользователем с ингредиентами, возвращается код 200")
    public void checkCreateOrderWithIngredientsWithAuthorization(){
        userRequest.loginUser();
        ingredients.clear();
        ingredients.add(INGREDIENT);
        DataOrder dataOrder = new DataOrder(ingredients);
        orderRequest.setDataOrder(dataOrder);
        orderRequest.createOrderWithAuthorization(accessToken)
                .then().assertThat().statusCode(OK_STATUS_COD)
                .body("success",equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем без ингредиентов")
    @Description("При попытке создания заказа авторизованным пользователем без ингредиентами, возвращается код 400")
    public void checkCreateOrderNoIngredientsWithAuthorization(){
        userRequest.loginUser();
        ingredients.clear();
        DataOrder dataOrder = new DataOrder(ingredients);
        orderRequest.setDataOrder(dataOrder);
        orderRequest.createOrderWithAuthorization(accessToken)
                .then().assertThat().statusCode(BAD_REQUEST_STATUS_COD)
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем с невалидным хэшем игридиента")
    @Description("При попытке создания заказа авторизованным пользователем с невалидным хэшем игридиента, возвращается код 500")
    public void checkCreateOrderAuthorizedWithInvalidIngredientHash(){
        userRequest.loginUser();
        ingredients.add(INVALID_INGREDIENT_HASH);
        DataOrder dataOrder = new DataOrder(ingredients);
        orderRequest.setDataOrder(dataOrder);
        orderRequest.createOrderWithAuthorization(accessToken)
                .then().assertThat().statusCode(INTERNAL_SERVER_ERROR_STATUS_COD);
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем с невалидным хэшем игридиента")
    @Description("При попытке создания заказа неавторизованным пользователем с невалидным хэшем игридиента, возвращается код 500")
    public void checkCreateOrderWithoutAuthorizationWithInvalidIngredientHash(){

        ingredients.add(INVALID_INGREDIENT_HASH);
        DataOrder dataOrder = new DataOrder(ingredients);
        orderRequest.setDataOrder(dataOrder);
        orderRequest.createOrderWithoutAuthorization()
                .then().assertThat().statusCode(INTERNAL_SERVER_ERROR_STATUS_COD);
    }

    @AfterEach
    @DisplayName("Удаление пользователя по токену")
    public void deleteUser() {
        if (accessToken!=null) {
            userRequest.deleteUser(accessToken);
        }
    }
}
