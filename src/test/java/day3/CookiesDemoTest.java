package day3;

import static io.restassured.RestAssured.when;

import io.restassured.response.Response;
import java.util.Map;
import org.testng.annotations.Test;

public class CookiesDemoTest {

  @Test
  public void testCookies() {
    when()
        .get("https://www.google.com/")
    .then()
        .cookie("AEC", "AZ6Zc-VpRZnFiMUMbyGp4tQUgznhuoPEtf4NvQDMeRupUY0QmBMt4czMyg")
        .log().all();
  }

  @Test
  public void getCookiesInformation() {
    Response response = when()
        .get("https://www.google.com/");

    String cookieValue = response.getCookie("AEC");
    System.out.println("Value of cookie is: " + cookieValue);

    Map<String, String> cookieValues = response.getCookies();
    for (String key : cookieValues.keySet()) {
      System.out.println("Value of cookie " + key + " is: " + response.getCookie(key));
    }
  }
}
