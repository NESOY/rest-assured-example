package step4;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.lessThan;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TimeTest {
    public static final String MOCK_JSON_SERVER = "https://jsonplaceholder.typicode.com/todos";

    @Test
    @DisplayName("Response 시간을 측정하고 검증할 수 있다")
    void timeTest() {
        when().
                get(MOCK_JSON_SERVER).
        then().
                time(lessThan(2000L)).
                time(lessThan(2L), TimeUnit.SECONDS);
    }
}
