package api_gb_test_stend;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PostTestStendLogin extends AbstractTest{

    @Test
    void postAuthToken200() {
        JsonPath response = given()
                .spec(getRequestSpecificationForPost())
                .when()
                .post(getBaseUrl()+ "gateway/login")
                .then()
                .spec(getResponseSpecificationForPost())
                .extract()
                .jsonPath();
                //если времени хватит вернусь к десериализации
        assertThat(response.get("username"), is("skachok"));
     }

    @Test
    void postAuthTokenBadUrl404() {
        given()
                .spec(getRequestSpecificationForPost())
                .when()
                .post(getBaseUrl()+ "gateway/login"+"NN")
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void postAuthTokenBadPass401() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", getUserName())
                //+1 для значения пароля
                .formParam("password","ec6855b8b4")
                .when()
                .post(getBaseUrl()+ "gateway/login")
                .prettyPeek()
                .then()
                .statusCode(401);
    }
}
