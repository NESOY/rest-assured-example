package step3;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.config.HeaderConfig.headerConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class HttpItemTest {
    public static final String MOCK_JSON_SERVER = "https://jsonplaceholder.typicode.com/todos";

    @BeforeAll
    @DisplayName("실패시 로그를 추가할 수 있다")
    public static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    @DisplayName("Header")
    class DescribeHeader{
        @Test
        @DisplayName("Headers 검증할 수 있다")
        void headersTest() {
            Response response = get(MOCK_JSON_SERVER);

            Headers allHeaders = response.getHeaders(); // All Header
            String headerName = response.getHeader("server"); // Single Header

            assertThat(headerName, is("cloudflare"));
        }

        @Test
        @DisplayName("Multi-value Headers도 가능하다")
        void multiHeadersTest() {
            Response response = get(MOCK_JSON_SERVER);

            List<String> multiHeaders = response.getHeaders().getValues("multiHeaders");
        }

        @Test
        @DisplayName("Header 추가하는 방법")
        void settingHeaders() {
            given()
                    // Single Header
                    .header("MyHeader", "Something")
                    // Multi Header
                    .headers("MyHeader", "Something", "MyOtherHeader", "SomethingElse")
                    // Multi-Value Header
                    .header("headerName", "value1", "value2").and().
                    // Over write Header
                    config(RestAssuredConfig.config().headerConfig(headerConfig().overwriteHeadersWithName("x"))).
                    header("x", "1").
                    header("x", "2").
            then();
        }

        @Test
        @Disabled("No Mock Server")
        @DisplayName("Header 검증하는 방법")
        void headerassertTest() {
            get(MOCK_JSON_SERVER).
            then().
                    assertThat().header("headerName", "headerValue").
                    assertThat().headers("headerName1", "headerValue1", "headerName2", "headerValue2").
                    assertThat().headers("headerName1", "headerValue1", "headerName2", containsString("Value2")).
                    assertThat().header("Content-Length", Integer::parseInt, lessThan(1000));
        }

        @Test
        @DisplayName("ContentType")
        void contentType() {
            given().
                    contentType(ContentType.TEXT).
                    contentType("application/json").
            then();
        }

        @Test
        @DisplayName("ContentType 검증하는 방법")
        void contentTypeassertTest() {
            get(MOCK_JSON_SERVER).
            then().
                    assertThat().contentType(ContentType.JSON);
        }
    }

    @Nested
    @DisplayName("Cookie")
    class DescribeCookie{
        @Test
        @DisplayName("Cookie 검증할 수 있다")
        void cookieTest() {
            Response response = get(MOCK_JSON_SERVER);

            Map<String, String> allCookies = response.getCookies(); // All Cookie
            String cookieValue = response.getCookie("cookieName"); // Single Cookie
        }

        @Test
        @DisplayName("Multi-value Cookie도 가능하다")
        void multiHeadersTest() {
            Response response = get(MOCK_JSON_SERVER);

            List<String> multiHeaders = response.getDetailedCookies().getValues("multiCookie");
        }
        /**
         * Detail Cookie는 아래의 정보가 필요한 경우 사용한다
         * the comment, path or expiry date etc
         */

        @Test
        @Disabled("No Mock Server")
        @DisplayName("쿠키를 설정하는 방법도 있다")
        void settingCookie() {
            Cookie cookie1 = new Cookie.Builder("username", "John").setComment("comment 1").build();
            Cookie cookie2 = new Cookie.Builder("token", "1234").setComment("comment 2").build();
            Cookies cookies = new Cookies(cookie1, cookie2);

            given().
                    cookies(cookies).
            when().
                    get("/cookie").
            then().
                    body(equalTo("username, token"));

        }

        @Test
        @Disabled("No Mock Server")
        @DisplayName("쿠키 검증하는 방법")
        void cookieAssertTest() {
            get(MOCK_JSON_SERVER).
            then().
                    assertThat().cookie("cookieName", "cookieValue").
                    assertThat().cookies("cookieName1", "cookieValue1", "cookieName2", "cookieValue2").
                    assertThat().cookies("cookieName1", "cookieValue1", "cookieName2", containsString("Value2"));
        }
    }

    @Nested
    @DisplayName("Status")
    class DescribeStatus {
        @Test
        @DisplayName("Status 검증할 수 있다")
        void statusTest() {
            Response response = get(MOCK_JSON_SERVER);
            // Get status line
            String statusLine = response.getStatusLine();
            assertThat(statusLine, is("HTTP/1.1 200 OK"));
            // Get status code
            int statusCode = response.getStatusCode();
            assertThat(statusCode, is(200));
        }

        @Test
        @DisplayName("Status 검증하는 방법")
        void statusAssertTest() {
            get(MOCK_JSON_SERVER).
            then().
                    assertThat().statusCode(200).
                    assertThat().statusLine("HTTP/1.1 200 OK").
                    assertThat().statusLine(containsString("HTTP/1.1"));
        }
    }
}
