import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class RegisterTest {

    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void createUserTest(){
        GenerateUser newUser = new GenerateUser();
        UserActionsList create = new UserActionsList();
        JUser testUser = newUser.randomUser();
        Response response = create.createUser(testUser);
        assertEquals(200, response.statusCode());


        Response userLogin = create.loginUser(UserCreds.credsFrom(testUser));
        Response delete = create.userDelete(create.getToken(userLogin));
        assertEquals(202, delete.statusCode());
    }

    @Test
    public void recreateUserTest(){
        GenerateUser newUser = new GenerateUser();
        UserActionsList create = new UserActionsList();
        JUser testUser = new JUser();
        testUser = newUser.randomUser();
        Response response = create.createUser(testUser);
        Response response1 = create.createUser(testUser);
        assertEquals(403, response1.statusCode());
        response1.then().assertThat().body("message", equalTo("User already exists"));

        Response userLogin = create.loginUser(UserCreds.credsFrom(testUser));
        Response delete = create.userDelete(create.getToken(userLogin));
        assertEquals(202, delete.statusCode());

    }

    @Test
    public void createUserWOEmail(){
        GenerateUser newUser = new GenerateUser();
        UserActionsList create = new UserActionsList();
        Response response = create.createUser(newUser.randomUserWOEmail());
        assertEquals(403, response.statusCode());
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void createUserWOPassword(){
        GenerateUser newUser = new GenerateUser();
        UserActionsList create = new UserActionsList();
        Response response = create.createUser(newUser.randomUserWOPassword());
        assertEquals(403, response.statusCode());
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    public void createUserWOName(){
        GenerateUser newUser = new GenerateUser();
        UserActionsList create = new UserActionsList();
        Response response = create.createUser(newUser.randomUserWOName());
        assertEquals(403, response.statusCode());
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

}