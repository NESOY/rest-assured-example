package step4;

import static io.restassured.RestAssured.given;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.mapper.ObjectMapperType;
import java.io.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @see "https://github.com/rest-assured/rest-assured/wiki/Usage#multi-part-form-data"
 */
public class MultiPartTest {
    @Test
    @DisplayName("multi part test")
    public void test() {
        given()
                .multiPart(new File("/path/to/file")).
        when()
                .post("www.naver.com").
        then()
                .statusCode(200);
    }

    @Test
    @DisplayName("multi part test in file builder")
    public void test2() {
        Greeting greeting = new Greeting();
        greeting.setFirstName("John");
        greeting.setLastName("Doe");


        given().
                multiPart(new MultiPartSpecBuilder(greeting, ObjectMapperType.JACKSON_2)
                        .fileName("greeting.json")
                        .controlName("text")
                        .mimeType("application/vnd.custom+json").build()).
        when().
                post("/multipart/json").
        then().
                statusCode(200);

    }

    class Greeting {

        private String firstName;
        private String lastName;

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    }
}
