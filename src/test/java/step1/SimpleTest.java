package step1;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleTest {
    /**
     * {
     *     "userId": 1,
     *     "id": 1,
     *     "title": "delectus aut autem",
     *     "completed": false
     * }
     */
    public static final String MOCK_JSON_SERVER = "https://jsonplaceholder.typicode.com/todos/1";

    @BeforeAll
    @DisplayName("실패시 로그를 추가할 수 있다")
    public static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void simpleGuide() {
        get(MOCK_JSON_SERVER).
        then().
                body("userId", equalTo(1));
    }

    @Test
    public void simpleGuide2(){
        given().
        when().
                get(MOCK_JSON_SERVER).
        then().
                statusCode(200).
                body("userId", equalTo(1));
    }

    @Test
    @DisplayName("json-path는 검증할때 Type에 민감하여 테스트는 실패한다")
    public void typeSensitiveGuide() {
        given().
        when().
                get(MOCK_JSON_SERVER).
        then().
                statusCode(200).
                body("userId", equalTo("1"));
    }
}
