package step3;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class HttpParameterTest {

    public static final String MOCK_JSON_SERVER = "https://jsonplaceholder.typicode.com/todos";

    @BeforeAll
    @DisplayName("실패시 로그를 추가할 수 있다")
    public static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("3.0.0이상부터 Request라는 Syntatic Sugar 지원")
    void httpSyntaticSugar() {
        when().
                request(Method.GET, MOCK_JSON_SERVER).
        then().
                statusCode(200);
    }

    @Nested
    @DisplayName("Parameter")
    class DescribeParameter {

        @Test
        @DisplayName("Parameter도 추가 가능하다")
        void httpParameter() {
            given().
                    param("param1", "value1").
                    param("param2", "value2").
                    log().parameters().
            when().
                    get(MOCK_JSON_SERVER);
        }

        @Test
        @DisplayName("Form,, Query Parameter도 추가 가능하다")
        void httpParameter2() {
            given().
                    formParam("formParamName", "value1").
                    queryParam("queryParamName", "value2").
                    log().parameters().
            when().
                    get(MOCK_JSON_SERVER);
        }

        @Test
        @DisplayName("Multi Value Parameter도 추가 가능하다")
        void httpParameter3() {
            List<String> params = new ArrayList<>();
            params.add("value4");
            params.add("value5");

            given().
                    queryParam("queryParamName", "value1", "value2").
                    queryParam("queryParamName", "value3").
                    queryParam("queryParamName", params).
                    log().parameters().
            when().
                    get(MOCK_JSON_SERVER);
        }
    }

    @Nested
    @DisplayName("Path Parameter")
    class DescribePathParameter{
        @Test
        @DisplayName("Path Parameter도 추가 가능하다")
        void pathParameter() {
            when().
                    get(MOCK_JSON_SERVER+"/{todoId}", 1).
                    //get(MOCK_JSON_SERVER+"/{todoId}/{plusId}", 1, 3). 순차적으로 선언 가능
            then().
                    statusCode(200).
                    contentType(ContentType.JSON).
                    body("id", is(1));
        }

        @Test
        @DisplayName("given으로 Path Parameter도 추가 가능하다")
        void pathParameter2() {
            given().
                    pathParam("todoId", 1).
            when().
                    get(MOCK_JSON_SERVER+"/{todoId}").
            then().
                    statusCode(200).
                    contentType(ContentType.JSON).
                    body("id", is(1));
        }

        /**
         * 첫번째 방법과 두번째 방법에 대해 Mixing도 가능하다
         * given().
         *         pathParam("hotelId", "My Hotel").
         * when().
         *         post("/reserve/{hotelId}/{roomNumber}", 23).
         * then().
         */
    }


    @Nested
    @DisplayName("Request Body")
    class DescribeRequestBody {
        @Test
        @DisplayName("RequestBody - String")
        void requestBody() {
            given().
                    // Works for POST, PUT and DELETE requests
                    body("some body").
            given().
                    request().
                    // More explicit (optional)s
                    body("some body").
            then();

            given().
                    // Works for POST, PUT and DELETE requests
                    body(new byte[]{42}).
            given().
                    request().
                    // More explicit (optional)s
                    body(new byte[]{42}).
            then();
        }
        @Test
        @DisplayName("RequestBody - byte")
        void requestBody2() {
            given().
                    // Works for POST, PUT and DELETE requests
                    body(new byte[]{42}).
            given().
                    request().
                    // More explicit (optional)s
                    body(new byte[]{42}).
            then();
        }
    }
}
