package utils;

public class Constants {

    public static final String BASE_URI="https://stellarburgers.education-services.ru/";
    public static final String CREATE_ENDPOINT_USER="api/auth/register";
    public static final String LOGIN_ENDPOINT_USER="api/auth/login";
    public static final String CHANGES_ENDPOINT_USER="api/auth/user"; // удаление и обновление данных пользователя
    public static final String CREATE_ENDPOINT_ORDER="api/orders";


    public static final Integer OK_STATUS_COD=200;
    public static final Integer BAD_REQUEST_STATUS_COD = 400;
    public static final Integer UNAUTHORIZED_STATUS_COD = 401;
    public static final Integer FORBIDDEN_STATUS_COD=403;
    public static final Integer INTERNAL_SERVER_ERROR_STATUS_COD=500;

    public static final String INGREDIENT="61c0c5a71d1f82001bdaaa6f";
    public static final String INVALID_INGREDIENT_HASH="6hri3889302jed";
}
