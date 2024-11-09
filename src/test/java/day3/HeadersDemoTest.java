package day3;

import static io.restassured.RestAssured.when;

import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class HeadersDemoTest {

    @Test
  public void testHeaders() {
    when()
        .get("https://www.google.com/")
    .then()
        .header("Content-type", "text/html; charset=ISO-8859-1")
        .and()
        .header("Content-encoding", "gzip")
        .and()
        .header("Server", "gws")
        .log().headers();
  }

//  @Test
  public void getHeaders() {
    Response response = when()
        .get("https://www.google.com/");

    String headerValue = response.getHeader("Content-type");
    System.out.println("Content-type is: " + headerValue);

    //Headers headers =  response.getHeaders();
    for (Header header : response.getHeaders()) {
      System.out.println(header.getName() + " is: " + header.getValue());
    }
  }
}
