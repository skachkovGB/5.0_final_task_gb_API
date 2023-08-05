package api_gb_test_stend;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


public class GetTestStendMyPosts extends AbstractTest{

    @Test
    void getMyPostsSimple200() {
        given()
                .spec(getRequestSpecificationForGet())
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .prettyPeek()
                .then()
                .spec(getResponseSpecificationForGet());
    }
    @Test
    void getMyPostsASC200() {
        JsonPath response = given()
                .spec(getRequestSpecificationForGet())
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
    void getMyPostsDESC200() {
        JsonPath response = given()
                .spec(getRequestSpecificationForGet())
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
    void getMyPostsBigPage200() {
        JsonPath response = given()
                .spec(getRequestSpecificationForGet())
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .queryParam("page", "100")
                .when()
                .get(getBaseUrl()+"api/posts")
                .then()
                .spec(getResponseSpecificationForGet())
                .extract()
                .jsonPath();

        assertThat(response.get("meta.nextPage"), equalTo(null));
    }

    @Test
    void getMyPostsZeroPage200() {
        JsonPath response = given()
                .spec(getRequestSpecificationForGet())
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
    void getMyPostNoToken() {
        given()
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "1")
                .when()
                .get(getBaseUrl()+"api/posts")
                .prettyPeek()
                .then()
                .statusCode(401);
    }
}
