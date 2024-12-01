package day5;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class ParsingXMLResponseTest {

  private static final Logger log = LoggerFactory.getLogger(ParsingXMLResponseTest.class);

  @Test
  public void testXMLResponse() {
    when()
        .get("http://localhost:3000/123")
    .then()
        .statusCode(200)
        .header("Content-type", "application/xml; charset=utf-8")
        .body("TravellerInformationResponse.page", equalTo("1"))
        .body("TravellerInformationResponse.travellers.TravellerInformation[0].name",
            equalTo("Name One"));
  }

  @Test
  public void saveXMLResponse() {
    Response response = when()
        .get("http://localhost:3000/123");

    assertEquals(response.getStatusCode(), 200);
    assertEquals(response.getHeader("Content-type"),
        "application/xml; charset=utf-8");
    assertEquals(response
        .xmlPath()
        .get("TravellerInformationResponse.page")
        .toString(), "1");
    assertEquals(response
        .xmlPath()
        .get("TravellerInformationResponse.travellers.TravellerInformation[0].name")
        .toString(), "Name One");
  }

  @Test
  public void saveInXmlPathObject() {
    Response response = when()
        .get("http://localhost:3000/123");

    XmlPath xmlPath = new XmlPath(response.asString());
    List<String> travellers = xmlPath.getList("TravellerInformationResponse.travellers.TravellerInformation");

    assertEquals(travellers.size(), 3);

    List<String> travellerNames = xmlPath.getList("TravellerInformationResponse.travellers.TravellerInformation.name");
    boolean status = false;
    for (String travellerName : travellerNames) {
      if (travellerName.equals("Name One")) {
        status = true;
        break;
      }
    }
    assertTrue(status);
  }
}
