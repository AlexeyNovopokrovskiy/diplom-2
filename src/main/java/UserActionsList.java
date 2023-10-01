import io.restassured.response.Response;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
public class UserActionsList {

    private static final String BASE_URL = "/api/auth/register";
    private static final String BASE_URL_LOGIN = "api/auth/login";

    private static final String BASE_URL_UPDATE = "api/auth/user";

    private static final String BASE_URL_ORDER_LIST = "api/ingredients";

    private static final String BASE_URL_ORDER = "api/orders";


public Response createUser(JUser testUser){
    return given()
            .header("Content-type", "application/json")
            .and()
            .body(testUser)
            .when()
            .post(BASE_URL);
}

    public Response loginUser(UserCreds creds) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(creds)
                .when()
                .post(BASE_URL_LOGIN);
    }

    public Response userDelete(String token){
        return given()
                .header("Authorization",  token)
                .delete(BASE_URL_UPDATE);
    }

    public String getToken(Response response){
    String token = response.then().extract().path("accessToken");
    return token;
    }

    public Response changeUserCreds(UserCreds creds, String token) {
        return given()
                .header("Content-type", "application/json" )
                .and()
                .header("Authorization", token)
                .and()
                .body(creds)
                .when()
                .patch(BASE_URL_UPDATE);
    }

    public Response getUserCreds(UserCreds creds, String token) {
        return given()
                .header("Content-type", "application/json" )
                .and()
                .header("Authorization", token)
                .and()
                .body(creds)
                .when()
                .get(BASE_URL_UPDATE);
    }

    public Response changeUserCredsWOLogin(UserCreds creds) {
        return given()
                .header("Content-type", "application/json" )
                .and()
                .body(creds)
                .when()
                .patch(BASE_URL_UPDATE);
    }

    public ArrayList<String> getIngridientsList(){
        Response res = given()
                .header("Content-type", "application/json" )
                .when()
                .get(BASE_URL_ORDER_LIST);
        ArrayList<String> s  = res.then().extract().path("data._id");
        return s;
    }

    public Response makeOrderNoLogin(Ingridients ingrids){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(ingrids)
                .when()
                .post(BASE_URL_ORDER);

    }

    public Response makeOrderWithLogin(Ingridients ingrids, String token){
        return given()
                .header("Content-type", "application/json")
                .and()
                .header("Authorization", token)
                .and()
                .body(ingrids)
                .when()
                .post(BASE_URL_ORDER);

    }

    public Response getUserOrdersWithLogin( String token){
        return given()
                .header("Content-type", "application/json")
                .and()
                .header("Authorization", token)
                .when()
                .get(BASE_URL_ORDER);

    }

    public Response getUserOrdersWithOutLogin(){
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(BASE_URL_ORDER);

    }


}
