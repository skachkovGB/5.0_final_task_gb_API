package api_gb_test_stend;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AbstractTest {

    static Properties prop = new Properties();
    private static InputStream configFile;

    private static String baseUrl;
    private static String userName;
    private static String password;
    private static String token;


    private static RequestSpecification requestSpecificationForPost;
    private static RequestSpecification requestSpecificationForGet;

    private static ResponseSpecification responseSpecificationForPost;
    private static ResponseSpecification responseSpecificationForGet;
    private static ResponseSpecification responseSpecificationForGet401;


    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/main/resources/my.properties");
        prop.load(configFile);

        baseUrl = prop.getProperty("baseUrl");
        userName = prop.getProperty("userName");
        password = prop.getProperty("password");
        token = prop.getProperty("token");

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        requestSpecificationForPost = new RequestSpecBuilder()
                .setContentType("application/x-www-form-urlencoded")
                .addFormParam("username", getUserName())
                .addFormParam("password", getPassword())
                .log(LogDetail.ALL)
                .build();

        responseSpecificationForPost = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectResponseTime(Matchers.lessThan(2000L))
                .build();

        requestSpecificationForGet = new RequestSpecBuilder()
                .addHeader("X-Auth-Token", getToken())
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        responseSpecificationForGet = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectResponseTime(Matchers.lessThan(2000L))
                .build();

        responseSpecificationForGet401 = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectResponseTime(Matchers.lessThan(2000L))
                .build();

        //RestAssured.requestSpecification = requestSpecificationForPost;
        //RestAssured.responseSpecification = responseSpecificationForPost;

    }

    public static String getBaseUrl() {
        return baseUrl;
    }
    public static String getUserName() {
        return userName;
    }

    public static String getPassword() {
        return password;
    }

    public static String getToken() {
        return token;
    }

    public static RequestSpecification getRequestSpecificationForPost() {return requestSpecificationForPost;}
    public static ResponseSpecification getResponseSpecificationForPost() {return responseSpecificationForPost;}
    public static RequestSpecification getRequestSpecificationForGet() {return requestSpecificationForGet;}
    public static ResponseSpecification getResponseSpecificationForGet() {return responseSpecificationForGet;}
    public static ResponseSpecification getResponseSpecificationForGet401() {return responseSpecificationForGet401;}
}
