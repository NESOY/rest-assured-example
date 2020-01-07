package step2;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.path.json.config.JsonPathConfig.NumberReturnType;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled("Mock 서버가 없음")
public class JsonValidationTest {
    /**
     * {
     *   "id": 1,
     *   "name": "Leanne Graham",
     *   "username": "Bret",
     *   "email": "Sincere@april.biz",
     *   "address": {
     *     "street": "Kulas Light",
     *     "suite": "Apt. 556",
     *     "city": "Gwenborough",
     *     "zipcode": "92998-3874",
     *     "geo": {
     *       "lat": "-37.3159",
     *       "lng": "81.1496"
     *     }
     *   },
     *   "phone": "1-770-736-8031 x56442",
     *   "website": "hildegard.org",
     *   "company": {
     *     "name": "Romaguera-Crona",
     *     "catchPhrase": "Multi-layered client-server neural-net",
     *     "bs": "harness real-time e-markets"
     *   }
     * }
     */
    public static final String MOCK_JSON_SERVER = "https://jsonplaceholder.typicode.com/users/1";

    @BeforeAll
    @DisplayName("실패시 로그를 추가할 수 있다")
    public static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    /**
     * {
     *     "price":12.12
     * }
     */
    @Test
    @DisplayName("실수를 검증하고 싶은 경우 f를 추가하면 된다.")
    public void floatTest() {
        given().
        when().
                get(MOCK_JSON_SERVER).
        then().
                statusCode(200).
                body("price", is(12.12f));
    }

    @Test
    @DisplayName("BigDecimal로 검증하고 싶은 경우 config를 추가하면 된다.")
    public void bigDecimalTest() {
        given().
                config(RestAssured.config().jsonConfig(JsonConfig.jsonConfig().numberReturnType(NumberReturnType.BIG_DECIMAL))).
        when().
                get(MOCK_JSON_SERVER).
        then().
                statusCode(200).
                body("price", is(new BigDecimal(12.12)));
    }
}
