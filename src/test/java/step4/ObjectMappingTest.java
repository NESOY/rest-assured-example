package step4;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.parsing.Parser;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @see "https://github.com/rest-assured/rest-assured/wiki/Usage#object-mapping"
 * JSON Serializer Ordering
 * JSON using Jackson 2 (Faster Jackson (databind))
 * JSON using Jackson (databind)
 * JSON using Gson
 * JSON using Johnzon
 * XML using JAXB
 */
public class ObjectMappingTest {

    @BeforeEach
    public void setUp() {
        RestAssured.defaultParser = Parser.JSON;
        ObjectMapper objectMapper = new ObjectMapper();
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig((io.restassured.mapper.ObjectMapper) objectMapper));
    }

    @Test
    @DisplayName("객체로 JSON값으로 Serialize가 가능하다")
    public void test() {
        Message message = new Message();
        message.setMessage("My messagee");

        given().
                contentType("application/json").
                body(message).
                log().all().
        when().
                post("/message");
    }
    @Test
    @DisplayName("Map 객체로 JSON값으로 Serialize가 가능하다.")
    public void test2() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("firstName", "John");
        jsonAsMap.put("lastName", "Doe");

        given().
                contentType("application/json").
                body(jsonAsMap).
                log().all().
        when().
                post("/somewhere").
        then().
                statusCode(200);
    }

    @Test
    @DisplayName("원하는 Json Serializer를 사용할 수 있다")
    public void test3() {
        Message message = new Message();
        message.setMessage("My messagee");
        given().
                body(message, ObjectMapperType.GSON).
        when().
                post("/message");
    }


    @Test
    @DisplayName("원하는 객체로 De-serialize할 수 있다")
    public void test4() {
        Message message = get("/message").as(Message.class);
        Message xmlMessage = expect().parser("application/something", Parser.XML).when().get("/message").as(Message.class);
        Message defaultParserMessage = expect().defaultParser(Parser.XML).when().get("/message").as(Message.class);
    }

    @Test
    @DisplayName("원하는 객체, 원하는 de-serializer로 De-serialize할 수 있다")
    public void test5() {
        Message message = get("/message").as(Message.class, ObjectMapperType.GSON);
    }


    class Message {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
