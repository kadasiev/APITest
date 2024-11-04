package day1;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import org.testng.annotations.Test;

public class HTTPRequestsTest {

  private int id;

  @Test(priority = 1)
  public void getUsers() {
    when()
        .get("https://reqres.in/api/users?page=2")
    .then()
        .statusCode(200)
        .body("page", equalTo(2))
        .log().all();
  }

  @Test(priority = 2)
  public void createUser() {
    HashMap<String, String> data = new HashMap<>();
    data.put("name", "pavan");
    data.put("job", "trainer");

    id = given()
        .contentType("application/json")
        .body(data)
    .when()
        .post("https://reqres.in/api/users")
        .jsonPath()
        .getInt("id");
  }

  @Test(priority = 3, dependsOnMethods = {"createUser"})
  public void updateUser() {
    HashMap<String, String> data = new HashMap<>();
    data.put("name", "john");
    data.put("job", "teacher");

    given()
        .contentType("application/json")
        .body(data)
    .when()
        .put("https://reqres.in/api/users/" + id)
    .then()
        .statusCode(200)
        .log()
        .all();
  }

  @Test(priority = 4, dependsOnMethods = {"createUser"})
  public void deleteUser() {
    when()
        .delete("https://reqres.in/api/users/" + id)
    .then()
        .statusCode(204);
  }
}
