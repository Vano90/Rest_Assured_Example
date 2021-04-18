package getting_started;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SimpleTest {

    @Test
    public void simple_get_request() {

        given()
                .baseUri("https://restcountries.eu/rest/v2").
                when()
                .get("/all").
                then()
                .statusCode(200);
    }


    @Test
    public void validate_json_response() {

        given()
                .baseUri("https://restcountries.eu/rest/v2").
                when()
                .get("/alpha/USA").
                then()
                .statusCode(200)
                .body(
                        "name", equalTo("United States of America"),
                        "alpha3Code", equalTo("USA"),
                        "altSpellings", hasItem("US"),
                        "currencies[0].symbol", equalTo("$"),
                        "languages[0].name", equalTo("English")
                );
    }

    @Test
    public void extract_response_data() {
        Response res = given()
                .baseUri("https://restcountries.eu/rest/v2").
                        when()
                .get("/alpha/USA").
                        then()
                .extract().response();
        System.out.println(res.asString());
    }

    @Test
    public void extract_single_value() {
        String currencies_name =  given().baseUri("https://restcountries.eu/rest/v2").
                when()
                .get("/alpha/USA").
                        then()
                .statusCode(200)
                .body(
                        "name", equalTo("United States of America"),
                        "alpha3Code", equalTo("USA"),
                        "altSpellings", hasItem("US"),
                        "currencies[0].symbol", equalTo("$"),
                        "languages[0].name", equalTo("English")
                )
                .extract().path("currencies[0].name");

        System.out.println(currencies_name);
    }

    @Test
    public void verify_status_line() {
        given()
                .baseUri("https://api.printful.com").
                when()
                .get("store/products").
                then()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
    }


}
