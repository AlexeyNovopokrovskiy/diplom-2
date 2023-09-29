import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class LoginTest {

    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }



    @Test
    public void loginUser(){

        UserActionsList create = new UserActionsList();
        JUser testUser = GenerateUser.randomUser();
        create.createUser(testUser);
        Response userLogin = create.loginUser(UserCreds.credsFrom(testUser));
        assertEquals(200, userLogin.statusCode());


        Response delete = create.userDelete(create.getToken(userLogin));
        assertEquals(202, delete.statusCode());


    }
    @Test
    public void loginUserWrongEmail(){
        UserActionsList create = new UserActionsList();
        JUser testUser = GenerateUser.randomUser();
        create.createUser(testUser);
        Response userLogin = create.loginUser(UserCreds.credsChangedEmail(testUser));
        assertEquals(401, userLogin.statusCode());
        userLogin.then().assertThat().body("message", equalTo("email or password are incorrect"));

        Response userLoginForDelete = create.loginUser(UserCreds.credsFrom(testUser));
        Response delete = create.userDelete(create.getToken(userLoginForDelete));
        assertEquals(202, delete.statusCode());
    }
    @Test
    public void loginUserWrongPassword(){
        UserActionsList create = new UserActionsList();
        JUser testUser = GenerateUser.randomUser();
        create.createUser(testUser);
        Response userLogin = create.loginUser(UserCreds.credsChangedPassword(testUser));
        assertEquals(401, userLogin.statusCode());
        userLogin.then().assertThat().body("message", equalTo("email or password are incorrect"));

        Response userLoginForDelete = create.loginUser(UserCreds.credsFrom(testUser));
        Response delete = create.userDelete(create.getToken(userLoginForDelete));
        assertEquals(202, delete.statusCode());
    }

}