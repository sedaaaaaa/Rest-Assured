package testCASES;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.response.Response.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class UpdateOneProduct {
	HashMap<String,String> updatePayload;
	SoftAssert softAssertObj;

	public UpdateOneProduct() {

		softAssertObj = new SoftAssert();

	}

	public Map<String, String>  updatepayloadMap() {
	 updatePayload = new HashMap<String,String>();
	 updatePayload.put("id", "3756");
	 updatePayload.put("name", "MD's Amazing Pillow 2.0");
	 updatePayload.put("price", "199");
	 updatePayload.put("description", "The best pillow for amazong programmers.");
	 updatePayload.put("category_id", "2");
	 System.out.println("updatePayload:");
	 return updatePayload;
	}

	@Test
	public void Update_One_Products() {

		Response response =

				given().baseUri("https://techfios.com/api-prod/api/product")
						.header("Content-Type", "application/json; charset=UTF-8").auth().preemptive()
						.basic("demo@techfios.com", "abc123")
						.body(new File("src\\main\\java\\DATA\\createPayload.json")).

						// .log().all()

						when()
						// .log().all()
						.post("/update.php").

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
		softAssertObj.assertEquals(actualHeader, "application/json; charset=UTF-8", "Headers are not matching!");

		String actualResponseBody = response.getBody().asString();
		System.out.println("actualResponseBody" + actualResponseBody);

		JsonPath jsonPathObj = new JsonPath(actualResponseBody);
		String actualProductId = jsonPathObj.get("id");
		System.out.println("actualProductId:" + actualProductId);
		softAssertObj.assertEquals(actualProductId, "3745");

		String actualProductmessage = jsonPathObj.get("message");
		System.out.println("actualProductmessage:" + actualProductmessage);
		softAssertObj.assertEquals(actualProductmessage, "Product was created.", "Product messages are not matching!");

	}

}
