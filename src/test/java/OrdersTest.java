import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class OrdersTest {
    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }



    @Test
    public void makeOrderNoLogin(){

        UserActionsList create = new UserActionsList();
        GenerateBurger generateBurger = new GenerateBurger();
        Ingridients ingridients = new Ingridients(generateBurger.generateIngridientsList(create.getIngridientsList()));
        Response res = create.makeOrderNoLogin(ingridients);
        assertEquals(200, res.statusCode());

    }

    @Test
    public void makeOrderWithLogin(){

        UserActionsList create = new UserActionsList();
        JUser testUser = GenerateUser.randomUser();
        create.createUser(testUser);
        Response userLogin = create.loginUser(UserCreds.credsFrom(testUser));
        GenerateBurger generateBurger = new GenerateBurger();
        generateBurger.generateIngridientsList(create.getIngridientsList());
        Ingridients ingridients = new Ingridients(generateBurger.generateIngridientsList(create.getIngridientsList()));
        Response res = create.makeOrderWithLogin(ingridients, create.getToken(userLogin));
        assertEquals(200, res.statusCode());

        Response userLoginForDelete = create.loginUser(UserCreds.credsFrom(testUser));
        Response delete = create.userDelete(create.getToken(userLoginForDelete));
        assertEquals(202, delete.statusCode());

    }

    @Test
    public void makeOrderWOIngridients(){

        UserActionsList create = new UserActionsList();
        GenerateBurger generateBurger = new GenerateBurger();
        generateBurger.generateNullBurger();
        Ingridients ingridients = new Ingridients(generateBurger.generateNullBurger());
        Response res = create.makeOrderNoLogin(ingridients);
        assertEquals(400, res.statusCode());
        res.then().assertThat().body("message", equalTo("Ingredient ids must be provided"));

    }

    @Test
    public void makeOrderWrongIngridients(){

        UserActionsList create = new UserActionsList();
        GenerateBurger generateBurger = new GenerateBurger();
        Ingridients ingridients = new Ingridients(generateBurger.generateWrongIngridient(create.getIngridientsList()));
        Response res = create.makeOrderNoLogin(ingridients);
        assertEquals(500, res.statusCode());

    }

    @Test
    public void getUserOrdersWithLogin(){
        UserActionsList create = new UserActionsList();
        JUser testUser = GenerateUser.randomUser();
        create.createUser(testUser);
        Response userLogin = create.loginUser(UserCreds.credsFrom(testUser));
        Response orders = create.getUserOrdersWithLogin(create.getToken(userLogin));
        assertEquals(200, orders.statusCode());

        Response delete = create.userDelete(create.getToken(userLogin));
        assertEquals(202, delete.statusCode());
    }

    @Test
    public void getUserOrdersWithOutLogin(){

        UserActionsList create = new UserActionsList();
        Response orders = create.getUserOrdersWithOutLogin();
        assertEquals(401, orders.statusCode());
        orders.then().assertThat().body("message", equalTo("You should be authorised"));
    }
}