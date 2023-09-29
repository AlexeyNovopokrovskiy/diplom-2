import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class UserTest {
    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }


    @Test
    public void changeUserCredsEmail(){
        UserActionsList create = new UserActionsList();
        JUser testUser = GenerateUser.randomUser();
        create.createUser(testUser);
        Response userLogin = create.loginUser(UserCreds.credsFrom(testUser));
        UserCreds creds = UserCreds.changeUserCredsEmail(testUser);
        create.changeUserCreds(creds,create.getToken(userLogin));
        Response secondLogin = create.loginUser(creds);
        assertEquals(200,secondLogin.statusCode());

        Response delete = create.userDelete(create.getToken(secondLogin));
        assertEquals(202, delete.statusCode());

    }

    @Test
    public void changeUserCredsPassword(){
        UserActionsList create = new UserActionsList();
        JUser testUser = GenerateUser.randomUser();
        create.createUser(testUser);
        Response userLogin = create.loginUser(UserCreds.credsFrom(testUser));
        UserCreds creds = UserCreds.changeUserCredsPassword(testUser);
        create.changeUserCreds(creds,create.getToken(userLogin));
        Response secondLogin = create.loginUser(creds);
        assertEquals(200,secondLogin.statusCode());

        Response delete = create.userDelete(create.getToken(secondLogin));
        assertEquals(202, delete.statusCode());
    }

    @Test
    public void changeUserCredsName(){
        UserActionsList create = new UserActionsList();
        JUser testUser = GenerateUser.randomUser();
        create.createUser(testUser);
        Response userLogin = create.loginUser(UserCreds.credsFrom(testUser));
        UserCreds creds = UserCreds.changeUserCredsName(testUser);
        create.changeUserCreds(creds,create.getToken(userLogin));
        Response secondLogin = create.loginUser(creds);
        Response getCreds = create.getUserCreds(creds, create.getToken(secondLogin));
        getCreds.then().assertThat().body("user.name", equalTo(creds.getName()));

        Response delete = create.userDelete(create.getToken(secondLogin));
        assertEquals(202, delete.statusCode());
    }

    @Test
    public void changeUserCredsEmailWOLogin(){
        UserActionsList create = new UserActionsList();
        JUser testUser = GenerateUser.randomUser();
        create.createUser(testUser);
        UserCreds creds = UserCreds.changeUserCredsEmail(testUser);
        Response change = create.changeUserCredsWOLogin(creds);
        assertEquals(401,change.statusCode());
        change.then().assertThat().body("message", equalTo("You should be authorised"));

        Response userLoginForDelete = create.loginUser(UserCreds.credsFrom(testUser));
        Response delete = create.userDelete(create.getToken(userLoginForDelete));
        assertEquals(202, delete.statusCode());
    }

    @Test
    public void changeUserCredsPasswordWOLogin(){
        UserActionsList create = new UserActionsList();
        JUser testUser = GenerateUser.randomUser();
        create.createUser(testUser);
        UserCreds creds = UserCreds.changeUserCredsPassword(testUser);
        Response change = create.changeUserCredsWOLogin(creds);
        assertEquals(401,change.statusCode());
        change.then().assertThat().body("message", equalTo("You should be authorised"));

        Response userLoginForDelete = create.loginUser(UserCreds.credsFrom(testUser));
        Response delete = create.userDelete(create.getToken(userLoginForDelete));
        assertEquals(202, delete.statusCode());
    }

    @Test
    public void changeUserCredsNameWOLogin(){
        UserActionsList create = new UserActionsList();
        JUser testUser = GenerateUser.randomUser();
        create.createUser(testUser);
        UserCreds creds = UserCreds.changeUserCredsName(testUser);
        Response change = create.changeUserCredsWOLogin(creds);
        assertEquals(401,change.statusCode());
        change.then().assertThat().body("message", equalTo("You should be authorised"));

        Response userLoginForDelete = create.loginUser(UserCreds.credsFrom(testUser));
        Response delete = create.userDelete(create.getToken(userLoginForDelete));
        assertEquals(202, delete.statusCode());
    }
}