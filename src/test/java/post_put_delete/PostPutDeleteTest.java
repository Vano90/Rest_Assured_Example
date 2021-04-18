package post_put_delete;

import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PostPutDeleteTest {

    @Test
    public void post_request() {

        File file = new File("resources/create_employee.json");

        int id = given()
                .baseUri("http://dummy.restapiexample.com/api/v1")
                .contentType(ContentType.JSON)
                .body(file).
                        when()
                .post("/create").
                        then()
                .statusCode(200)
                .body("data.name", equalTo("Boris"))
                .extract().path("data.id");

        System.out.println(id);
    }

    @Test
    public void post_request_using_json_object() {

        JSONObject body = new JSONObject();
        body.put("name", "Alex");
        body.put("salary", "98000");
        body.put("age", "35");

        int id = given()
                .baseUri("http://dummy.restapiexample.com/api/v1")
                .contentType(ContentType.JSON)
                .body(body.toString()).
                        when()
                .post("/create").
                        then()
                .statusCode(200)
                .body("data.name", equalTo("Alex"))
                .extract().path("data.id");

        System.out.println(id);
    }

    @Test
    public void put_request_using_json_object() {

        JSONObject body = new JSONObject();
        body.put("name", "Sam");
        body.put("salary", "1000");
        body.put("age", "24");

        given()
                .baseUri("http://dummy.restapiexample.com/api/v1")
                .contentType(ContentType.JSON)
                .body(body.toString()).
                when()
                .put("/update/25").
                then()
                .statusCode(200);
    }

    @Test
    public void delete_request() {

        String msg = given()
                .baseUri("http://dummy.restapiexample.com/api/v1")
                .contentType(ContentType.JSON).
                        when()
                .delete("/delete/25").
                        then()
                .statusCode(200)
                .extract().path("message");

        System.out.println(msg);
    }


}
