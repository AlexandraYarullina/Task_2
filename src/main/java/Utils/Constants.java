package Utils;

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

    public static final String EMAIL="new_viki@yandex.ru";
    public static final String PASSWORD="viki_password";
    public static final String NAME ="Viktoria";
}
