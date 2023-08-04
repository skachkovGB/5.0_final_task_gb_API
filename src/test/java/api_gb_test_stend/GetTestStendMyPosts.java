package api_gb_test_stend;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


public class GetTestStendMyPosts extends AbstractTest{

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
