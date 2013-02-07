package itw.ucl.restassured.ITW_restassured;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.matcher.RestAssuredMatchers.matchesXsd;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasXPath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.ResponseSpecification;

public class RestAssuredSchoolware {

	/*
	 * Resources
	 */
	private static final String baseURI = "http://schoolware.cs.ucl.ac.uk:9999/";
	private static final String categoriesResource = baseURI
			+ "aad-ws/api/categories/";
	private static final String storeAppResource = baseURI
			+ "aad-ws/api/application/upload/";
	private static final String appDetailsResource = baseURI
			+ "aad-ws/api/application/";
	private static final String submitResultsResource = baseURI
			+ "aad-ws/api/result/submit/";
	private static final String testDetailsResource = baseURI
			+ "aad-ws/api/test/";
	private static final String getTestResource = baseURI + "aad-ws/api/tests/";
	private static final String getTypesResource = baseURI + "aad-ws/api/types";
	private static final String getTestQuestionResource = baseURI
			+ "aad-ws/api/questions/";
	private static final String getQuestionDetails = baseURI
			+ "aad-ws/api/question/";

	// Change that line to point to your File System
	// Used for POST Methods
	private static final String pathToFileStoreApp = "C:\\Users\\MConstantinides\\workspace\\ITW-restassured\\TestData\\storeApp\\Group4.zip\\";
	private static final String pathToFileSubmitResults = "C:\\Users\\MConstantinides\\workspace\\ITW-restassured\\TestData\\submitResults\\result.json";

	private static final String appForCategoryResource = baseURI
			+ "aad-ws/api/applications/";

	// Used in testGetAppsForCategory
	private static final String categoryID = "1";

	// Used in testGetApplicationDetails
	private static final String appIDForAD = "1";

	// Used in testGetTestDetails
	private static final String testIDForGTD = "1";

	// Used in testGetTests
	private static final String appIDForGT = "1";

	// Used in testGetTestQuestions
	private static final String testIDForGTQ = "1";

	// Used in testGetQuestionsDetails
	private static final String questionID = "1";

	@Test
	public void testStoreApp() {
		System.out
				.println("*********************** Testing StoreApp ***************************");
		given().multiPart(new File(pathToFileStoreApp))
				.parameters("name", "Group4 Testing", "description",
						"This is Group4 testing", "type", "1", "category", "1",
						"size", "500 KB", "developer", "Group4 Dev Team")
				.expect().statusCode(201).when().post(storeAppResource);
	}

	@Test
	public void testGetApplicationDetails() {
		System.out
				.println("*********************** Testing GetAppDetails ***************************");
		given().contentType("application/json; charset=UTF-16");
		expect().statusCode(200).when().get(appDetailsResource + appIDForAD);
	}

	@Test
	public void testGetAppsForCategory() {
		System.out
				.println("*********************** Testing GetAppsForCategory ***************************");
		given().contentType("application/json; charset=UTF-16");
		expect().statusCode(200).when()
				.get(appForCategoryResource + categoryID);
	}

	@Test
	public void testGetCategories() {
		System.out
				.println("*********************** Testing GetCategories ***************************");

		given().contentType("application/json; charset=UTF-16");
		expect().statusCode(200).
		// body(hasXPath("//person[@id='20']/email[.='dev@hascode.com'] and firstName='Sara' and lastName='Stevens'")).
		// body("categories.findAll { it.categType = Mathematics }.categId",
		// equalTo("1")).
				when().get(categoriesResource);
	}

	@Test
	public void testSubmitResult() {

		System.out
				.println("*********************** Testing SubmitResults  ********************************");

		try {

			BufferedReader br = new BufferedReader(new FileReader(
					pathToFileSubmitResults));
			String buffer;
			StringBuilder rawJson = new StringBuilder();
			while ((buffer = br.readLine()) != null) {
				rawJson.append(buffer);
			}

			given().contentType("application/json; charset=UTF-16")
					.body(rawJson.toString()).expect().statusCode(201).when()
					.post(submitResultsResource);

			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTestDetails() {
		System.out
				.println("*********************** Testing GetTestDetails ***************************");
		given().contentType("application/json; charset=UTF-16");
		expect().statusCode(200).when().get(testDetailsResource + testIDForGTD);
	}

	@Test
	public void testGetTests() {
		System.out
				.println("*********************** Testing GetTests ***************************");
		given().contentType("application/json; charset=UTF-16");
		expect().statusCode(200).when().get(getTestResource + appIDForGT);
	}

	@Test
	public void testGetTypes() {
		System.out
				.println("*********************** Testing GetTypes ***************************");
		given().contentType("application/json; charset=UTF-16");
		expect().statusCode(200).when().get(getTypesResource);
	}

	@Test
	public void testGetTestQuestions() {
		System.out
				.println("*********************** Testing GetTestQuestions ***************************");
		given().contentType("application/json; charset=UTF-16");
		expect().statusCode(200).when()
				.get(getTestQuestionResource + testIDForGTQ);
	}

	@Test
	public void testGetQuestionsDetails() {
		System.out
				.println("*********************** Testing GetQuestionsDetails ***************************");
		given().contentType("application/json; charset=UTF-16");
		expect().statusCode(200).when().get(getQuestionDetails + questionID);
	}

}
