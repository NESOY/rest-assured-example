package step5;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpecBuilderTest {

    @Test
    @DisplayName("Response Spec을 재사용할 수 있다")
    public void test() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectBody("x.y.size()", is(2));
        ResponseSpecification responseSpec = builder.build();

        // Now you can re-use the "responseSpec" in many different tests:
        when().
                get("/something").
        then().
                spec(responseSpec).
                body("x.y.z", equalTo("something"));
    }

    @Test
    @DisplayName("Request Spec을 재사용할 수 있다")
    public void test2() {
        /* Query Request */
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addParam("parameter1", "parameterValue");
        builder.addHeader("header1", "headerValue");
        RequestSpecification requestSpec = builder.build();

        /* Query Request */
        QueryableRequestSpecification queryable = SpecificationQuerier.query(requestSpec);
        String headerValue = queryable.getHeaders().getValue("header");
        String param = queryable.getFormParams().get("someparam");

        given().
                spec(requestSpec).
                param("parameter2", "paramValue").
        when().
                get("/something").
        then().
                body("x.y.z", equalTo("something"));
    }
}
