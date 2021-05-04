package headers_and_cookies;

import io.restassured.config.HeaderConfig;
import io.restassured.http.Cookie;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import java.util.Map;

public class Headers_and_Cookies {

    /*
     * What are Headers?
     *
     * Headers contain important information in the form of meta-data associated with:
     *
     * 		1. Request Body
     * 		2. Response Body
     * 		3. Caching of Response
     * 		4. Authentication
     * 		5. Cookies
     */


    /*
     * Adding Request Headers
     *
     * http://data.fixer.io/api/latest
     *
     * 1. If-None-Match: 03a4545f530000f3491fbca200000001
     * 2. If-Modified-Since: Tue, 30 Jun 2020 01:01:25 GMT
     *
     */


    @Test
    public void sending_request_headers() {
        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
                .queryParam("Symbols", "USD")
                .header("If-None-Match", "03a4545f530000f3491fbca200000001")
                .header("If-Modified-Since", "Tue, 30 Jun 2020 01:01:25 GMT").
                when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void sending_headers_object() {
        HashMap<String, Object> headers = new HashMap<String, Object>();
        headers.put("If-None-Match", "03a4545f530000f3491fbca200000001");
        headers.put("If-Modified-Since", "Tue, 30 Jun 2020 01:01:25 GMT");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Connection", "keep-alive");

        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
                .queryParam("Symbols", "USD")
                .headers(headers).
                when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void sending_request_cookies() {
        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
                .queryParam("Symbols", "USD")
                .cookie("user","ajhds","egj","qegfh").
                when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void sending_cookies_usinhg_builder() {
        Cookie cookie = new Cookie.Builder("usertype","int").setSecured(true).setComment("test cookie").build();
        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
                .queryParam("Symbols", "USD")
                .cookie(cookie).
                when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void validate_response_header() {
        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
                .queryParam("Symbols", "USD").
                when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200)
                .header("transfer-encoding", "chunked");
    }

    @Test
    public void extract_response_header() {
        Headers headers = given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
                .queryParam("Symbols", "USD").
                        when()
                .get("/latest").
                        then()
                .log().all()
                .statusCode(200)
                .extract().headers();

        System.out.println(headers.getValue("CF-RAY"));
        System.out.println(headers.getValue("access-control-allow-methods"));
        System.out.println(headers.getValue("Transfer-Encoding"));
    }

    @Test
    public void extract_response_cookies() {
        Map<String,String> cookies = given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
                .queryParam("Symbols", "USD").
                        when()
                .get("/latest").
                        then()
                .log().all()
                .statusCode(200)
                .extract().cookies();

        System.out.println(cookies.get("__cfduid"));

    }
}