package day3;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

public class PathAndQueryParametersTest {

  @Test
  public void pathAndQueryParameters() {
    given()
        .pathParams("myPath", "users")
        .queryParam("page", 2)
        .queryParam("id", 5)
    .when()
        .get("https://reqres.in/api/{myPath}")
    .then()
        .statusCode(200)
        .log().all();
  }
}














