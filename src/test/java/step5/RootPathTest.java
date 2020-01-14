package step5;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.withArgs;
import static io.restassured.RestAssured.withNoArgs;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RootPathTest {
    @Test
    @DisplayName("Root Path를 적용할 수 있다")
    public void test() {
        // AS-IS
        when().
                get("/something").
        then().
                body("x.y.firstName", is("nesoy")).
                body("x.y.lastName", is("nesoy2")).
                body("x.y.age", is("nesoy")).
                body("x.y.gender", is("nesoy"));

        // TO-BE
        when().
                get("/something").
        then().
                body("firstName", is("nesoy")).
                body("lastName", is("nesoy2")).
                body("age", is("nesoy")).
                body("gender", is("nesoy"));
    }

    @Test
    @DisplayName("Root Path를 추가할 수 있다")
    public void test2() {
        when().
                get("/jsonStore").
        then().
                rootPath("store.%s", withArgs("book")).
                body("category.size()", equalTo(4)).
                appendRootPath("%s.%s", withArgs("author", "size()")).
                body(withNoArgs(), equalTo(4));
    }

    @Test
    @DisplayName("Root Path를 제거할 수 있다")
    public void test3() {
        when().
                get("/jsonStore").
        then().
                rootPath("store.category").
                body("size()", equalTo(4)).
                detachRootPath("category").
                body("size()", equalTo(1));
    }
}
