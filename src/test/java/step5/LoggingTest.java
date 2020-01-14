package step5;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoggingTest {
    @Test
    @DisplayName("로깅을 할 수 있다")
    public void test() {
        given().
                contentType("application/json").
                log().all().
                log().parameters().
                log().params().
                log().body().
                log().headers().
                log().cookies().
                log().method().
                log().uri().
        when().
                post("/message");
    }

    @Test
    @DisplayName("로깅에 조건을 걸 수 있다")
    public void test2() {
        get("/x").then().log().ifStatusCodeIsEqualTo(302); // Only log if the status code is equal to 302
        get("/x").then().log().ifStatusCodeMatches(null); // Only log if the status code matches the supplied Hamcrest matcher
    }
}
