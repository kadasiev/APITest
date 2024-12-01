package day2;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

public class DifferentWaysToCreatePostRequestBodyTest {

//  @Test()
  public void testPostUsingHashMap() {
    HashMap<String, Object> data = new HashMap<>();
    data.put("name", "Scott");
    data.put("location", "France");
    data.put("phone", "512485954");
    data.put("courses", new String[] {"C", "C++"});

//    System.out.println(data);

    given()
        .contentType("application/json")
        .body(data)
    .when()
        .post("http://localhost:3000/students")
    .then()
        .statusCode(201)
        .body("name", equalTo("Scott"))
        .body("location", equalTo("France"))
        .body("phone", equalTo("512485954"))
        .body("courses[0]", equalTo("C"))
        .body("courses[1]", equalTo("C++"))
        .header("Content-Type", "application/json")
        .log().all();
  }

//  @Test()
  public void testPostUsingJSONLibrary() {
      JSONObject data = new JSONObject();
      data.put("name", "Scott");
      data.put("location", "France");
      data.put("phone", "512485954");
      data.put("courses", new String[] {"C", "C++"});

    given()
        .contentType("application/json")
        .body(data.toString())
    .when()
        .post("http://localhost:3000/students")
    .then()
        .statusCode(201)
        .body("name", equalTo("Scott"))
        .body("location", equalTo("France"))
        .body("phone", equalTo("512485954"))
        .body("courses[0]", equalTo("C"))
        .body("courses[1]", equalTo("C++"))
        .header("Content-Type", "application/json")
        .log().all();
  }

//  @Test()
  public void testPostUsingPOJO() {
    POJOPostRequest data = new POJOPostRequest();
    data.setName("Scott");
    data.setLocation("France");
    data.setPhone("512485954");
    data.setCourses(new String[] {"C", "C++"});

    given()
        .contentType("application/json")
        .body(data)
    .when()
        .post("http://localhost:3000/students")
    .then()
        .statusCode(201)
        .body("name", equalTo("Scott"))
        .body("location", equalTo("France"))
        .body("phone", equalTo("512485954"))
        .body("courses[0]", equalTo("C"))
        .body("courses[1]", equalTo("C++"))
        .header("Content-Type", "application/json")
        .log().all();
  }

  @Test()
  public void testPostUsingExternalJSONFile() throws FileNotFoundException {
    JSONObject jsonObject = new JSONObject(new JSONTokener(new FileReader(".\\src\\test\\resources\\body.json")));

    given()
        .contentType("application/json")
        .body(jsonObject.toString())
    .when()
        .post("http://localhost:3000/students")
    .then()
        .statusCode(201)
        .body("name", equalTo("Scott"))
        .body("location", equalTo("France"))
        .body("phone", equalTo("512485954"))
        .body("courses[0]", equalTo("C"))
        .body("courses[1]", equalTo("C++"))
        .header("Content-Type", "application/json")
        .log().all();
  }

  //@Test()
  public void testDelete() {
    when()
        .delete("http://localhost:3000/students/4")
    .then()
        .statusCode(200);
  }
}
