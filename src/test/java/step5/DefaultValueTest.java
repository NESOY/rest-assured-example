package step5;

import static io.restassured.RestAssured.basic;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

public class DefaultValueTest {

    @BeforeEach
    public void  setUp() {
        RestAssured.baseURI = "http://myhost.org";
        RestAssured.port = 80;
        RestAssured.basePath = "/resource";
        RestAssured.authentication = basic("username", "password");
        RestAssured.rootPath = "x.y.z";


        RestAssured.filters(null); // List of default filters
        RestAssured.requestSpecification = null; // Default request specification
        RestAssured.responseSpecification = null;// Default response specification
        RestAssured.urlEncodingEnabled = true; // Specify if Rest Assured should URL encoding the parameters
        RestAssured.defaultParser =  null; // Specify a default parser for response bodies if no registered parser can handle data of the response content-type
        RestAssured.registerParser( null, null); // Specify a parser for the given content-type
        RestAssured.unregisterParser(null); // Unregister a parser for the given content-type

        // Default Value
        // baseURI (localhost)
        // basePath (empty)
        // standard port (8080)
        // standard root path ("")
        // default authentication scheme (none)
        // url encoding enabled (true)
        RestAssured.reset();
    }
}
