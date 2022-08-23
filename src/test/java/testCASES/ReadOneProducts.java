package testCASES;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.response.Response.*;

import static io.restassured.RestAssured.*;

public class ReadOneProducts {

	SoftAssert softAssertObj;

	public ReadOneProducts() {

		softAssertObj = new SoftAssert();

	}

	@Test
	public void Read_One_Products() {
		
		SoftAssert softAssert = new SoftAssert();

		Response response =

				given().baseUri("https://techfios.com/api-prod/api/product")
						.header("Content-Type", "application/json; charset=UTF-8").auth().preemptive()
						.basic("demo@techfios.com", "abc123").queryParam("id", "3745").
						// .log().all()

						when()
						// .log().all()
						.get("/read_one.php").

						then()

						// .log().all()
						.extract().response();

		int actualStatusCode = response.getStatusCode();
		System.out.println("actualStatusCode" + actualStatusCode);
		// Assert.assertEquals(actualStatusCode, 200);
//		.statusCode(200)
//		.header("Content-Type", "application/json; charset=UTF-9")

		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actualHeader" + actualHeader);
		softAssertObj.assertEquals(actualHeader, "application/json");

		String actualResponseBody = response.getBody().asString();
		System.out.println("actualResponseBody" + actualResponseBody);

		JsonPath jsonPathObj = new JsonPath(actualResponseBody);
		String actualProductId = jsonPathObj.get("id");
		System.out.println("actualProductId:" + actualProductId);
		softAssertObj.assertEquals(actualProductId, "3745");

		String actualProductName = jsonPathObj.get("name");
		System.out.println("actualProductName:" + actualProductName);
		softAssertObj.assertEquals(actualProductName, "MD's Amazing Pillow 2.0");

		String actualProductPrice = jsonPathObj.get("price");
		System.out.println("actualProductPrice:" + actualProductPrice);
		softAssertObj.assertEquals(actualProductPrice, "199");
	}

}
