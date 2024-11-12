package day4;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParsingJSONResponseDataTest {

  @Test
  public void verifyJsonResponse() {
    given()
        .contentType(ContentType.JSON)
    .when()
        .get("http://localhost:3000/store")
    .then()
        .statusCode(200)
        .header("Content-Type", "application/json")
        .body("book[3].title", equalTo("The Lord of the Rings"));
  }

  @Test
  public void getJsonResponse() {
    Response response = given()
        .contentType(ContentType.JSON)
    .when()
        .get("http://localhost:3000/store");

    Assert.assertEquals(response.getStatusCode(), 200);
    Assert.assertEquals(response.getHeader("Content-Type"), "application/json");
    Assert.assertEquals(response.jsonPath().getString("book[3].title"),
        "The Lord of the Rings");
  }

  @Test
  public void verifyJsonResponseBodyData() {
    Response response = given()
        .contentType(ContentType.JSON)
    .when()
        .get("http://localhost:3000/store");

//    System.out.println(response.getBody().asString());

    JSONObject jsonObject = new JSONObject(response.getBody().asString());
    JSONArray books = jsonObject.getJSONArray("book");

    boolean status = false;
    for(int i = 0; i < books.length(); i++) {
      String bookTitle = books.getJSONObject(i).getString("title");
      if (bookTitle.equals("The Lord of the Rings")) {
        status = true;
        break;
      }
    }
    Assert.assertTrue(status);
  }

  @Test
  public void verifyTotalPrice() {
    Response response = given()
        .contentType(ContentType.JSON)
        .when()
        .get("http://localhost:3000/store");

    JSONObject jsonObject = new JSONObject(response.getBody().asString());
    JSONArray books = jsonObject.getJSONArray("book");

    double totalPrice = 0;
    for(int i = 0; i < books.length(); i++) {
      totalPrice += books.getJSONObject(i).getDouble("price");
    }
    Assert.assertEquals(totalPrice, 526);
  }
}
