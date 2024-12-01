package day5;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import org.testng.annotations.Test;

public class FileUploadAndDownloadTest {

  @Test
  public void singleFileUpload() {
    File file = new File("C:\\0101.txt");

    given()
        .multiPart(file)
        .contentType("multipart/form-data")
    .when()
        .post("http://localhost:3000/upload")
    .then()
        .statusCode(200)
        .body("endpoint", equalTo("/upload"));
  }
}
