package step2;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JsonSchemaTest {
    /**
     *[
     *   {
     *     "userId": 1,
     *     "id": 1,
     *     "title": "delectus aut autem",
     *     "completed": false
     *   },
     *   ...
     *
     *]
     */
    public static final String MOCK_JSON_SERVER = "https://jsonplaceholder.typicode.com/todos";
    public static final String JSON_SCHEMA = "todo-schema.json";

    @BeforeAll
    @DisplayName("실패시 로그를 추가할 수 있다")
    public static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Json Schema를 정의하여 Json형태를 검증할 수 있다")
    public void jsonSchemaTest() {
        given().
        when().
                get(MOCK_JSON_SERVER).
        then().
                statusCode(200).
                assertThat().body(matchesJsonSchemaInClasspath(JSON_SCHEMA));
    }
}
