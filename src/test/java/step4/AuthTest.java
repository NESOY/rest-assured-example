package step4;

import static io.restassured.RestAssured.given;

import io.restassured.authentication.FormAuthConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AuthTest {

    @Test
    @DisplayName("basic authentication")
    public void test() {
        given()
                .auth().basic("username", "password").
        when()
                .get("www.naver.com").
        then()
                .statusCode(200);
    }

    @Test
    @DisplayName("preemptive basic authentication")
    public void test2() {
        given().
                auth().preemptive().basic("username", "password").
        when().
                get("/secured/hello").
        then().
                statusCode(200);
    }
    @Test
    @DisplayName("Digest authentication")
    public void test3() {
        given().
                auth().digest("username", "password").
        when().
                get("/secured/hello").
        then().
                statusCode(200);
    }

    /**
     * <html>
     *   <head>
     *     <title>Login</title>
     *   </head>
     *
     *   <body>
     *     <form action="j_spring_security_check" method="POST">
     *       <table>
     *         <tr><td>User:&nbsp;</td><td><input type='text' name='j_username'></td></tr>
     *         <tr><td>Password:</td><td><input type='password' name='j_password'></td></tr>
     *           <tr><td colspan='2'><input name="submit" type="submit"/></td></tr>
     *        </table>
     *         </form>
     *       </body>
     *  </html>
     */
    @Test
    @DisplayName("Form authentication")
    public void test4() {
        given().
                auth().form("username", "password").
        when().
                get("/secured/hello").
        then().
                statusCode(200);
    }

    @Test
    @DisplayName("Form authentication 2")
    public void test5() {
        given().
                auth().form("John", "Doe", new FormAuthConfig("/j_spring_security_check", "j_username", "j_password")).
        when().
                get("/secured/hello").
        then().
                statusCode(200);
    }

    @Test
    @DisplayName("Form authentication in Spring Security")
    public void test6() {
        given().
                auth().form("John", "Doe", FormAuthConfig.springSecurity()).
        when().
                get("/secured/hello").
        then().
                statusCode(200);
    }
}
