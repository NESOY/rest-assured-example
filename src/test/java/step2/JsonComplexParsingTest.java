package step2;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JsonComplexParsingTest {
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
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @DisplayName("Json 검증할 때 JsonPath의 Expression을 사용할 수 있다")
    public void jsonSchemaTest() {
        given().
        when().
                get(MOCK_JSON_SERVER).
        then().
                statusCode(200).
                body("findAll { it.id < 2 }.title", contains("delectus aut autem"));
                // body("store.book.author.collect { it.length() }.sum()", greaterThan(50));
                // body("store.book.author*.length().sum()", greaterThan(50)).
    }
}
