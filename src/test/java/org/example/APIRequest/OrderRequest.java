package org.example.APIRequest;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.example.DataOrder;

import static Utils.Constants.CREATE_ENDPOINT_ORDER;
import static io.restassured.RestAssured.given;

public class OrderRequest extends APIBaseTest {
    private DataOrder dataOrder;

    public void setDataOrder(DataOrder dataOrder){
        this.dataOrder=dataOrder;
    }

    @Step("Создание заказа без авторизации")
    public Response createOrderWithoutAuthorization(){
        Response response=
                given()
                        .header("Content-Type","application/json")
                        .body(dataOrder)
                        .when()
                        .post(CREATE_ENDPOINT_ORDER);
        return response;

    }

    @Step("Создание заказа после авторизации")
    public Response createOrderWithAuthorization(String accessToken){
        Response response=
                given()
                        .header("Content-Type","application/json")
                        .header("Authorization",accessToken)
                        .body(dataOrder)
                        .when()
                        .post(CREATE_ENDPOINT_ORDER);
        return response;
    }

    @Step("Получение заказов без авторизации")
    public Response getOrdersWithoutAuthorization(){
        Response response= given()
                .header("Content-type", "application/json")
                .get(CREATE_ENDPOINT_ORDER);
        return response;
    }

    @Step("Получение заказов после авторизации")
    public Response getOrdersWithAuthorization(String accessToken){
        Response response= given()
                .header("Content-type", "application/json")
                .header("Authorization",accessToken)
                .get(CREATE_ENDPOINT_ORDER);
        return response;
    }

}
