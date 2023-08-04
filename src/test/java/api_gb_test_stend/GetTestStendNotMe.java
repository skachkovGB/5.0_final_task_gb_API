package api_gb_test_stend;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class GetTestStendNotMe extends AbstractTest{

    @Test
    void getPostNotMeSimple200() {
        given()
                .spec(getRequestSpecificationForGet())
                .queryParam("owner", "notMe")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .spec(getResponseSpecificationForGet());
    }

    @Test
    void getPostNotAllParamsASC200() {
        JsonPath response = given()
                .spec(getRequestSpecificationForGet())
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .spec(getResponseSpecificationForGet())
                .extract()
                .jsonPath();

        assertThat(response.get("meta.nextPage"), is(2));
    }

    @Test
    void getPostNotMeAllParamsDESC200() {
        JsonPath response = given()
                .spec(getRequestSpecificationForGet())
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .spec(getResponseSpecificationForGet())
                .extract()
                .jsonPath();

        assertThat(response.get("meta.nextPage"), is(2));
    }
    @Test
    void getPostNotMeZeroPage200() {
        JsonPath response = given()
                .spec(getRequestSpecificationForGet())
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", "0")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .spec(getResponseSpecificationForGet())
                .extract()
                .jsonPath();

        assertThat(response.get("meta.nextPage"), is(1));
    }

    @Test
    void getPostNotMeBigPage200() {
        JsonPath response = given()
                .spec(getRequestSpecificationForGet())
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", "10000")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .spec(getResponseSpecificationForGet())
                .extract()
                .jsonPath();

        assertThat(response.get("meta.nextPage"), equalTo(null));
    }

    @Test
    void getPostNotMePagesALL200() {
        given()
                .spec(getRequestSpecificationForGet())
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ALL")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .spec(getResponseSpecificationForGet());
    }

    @Test
    void getPostNotMeNoToken401() {
        given()
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .prettyPeek()
                .then()
                .statusCode(401);
    }

    @Test
    void getPostNotMeBadToken401() {
        JsonPath response = given()
                .header("X-Auth-Token", "b5c7bb8399d0238e001594585a9d65ae"+1)
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .prettyPeek()
                .then()
                .statusCode(401)
                .extract()
                .jsonPath();

        assertThat(response.get("message"), is("No API token provided or is not valid"));
    }





}
