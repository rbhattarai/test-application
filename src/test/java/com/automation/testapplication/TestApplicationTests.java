package com.automation.testapplication;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestApplicationTests {

	String BASE_URL = "http://localhost:8080";
	@Autowired
	private ConfigUtility configUtility;

	@Test
	void contextLoads() {
		RestAssured.baseURI = BASE_URL;
	}

	@Test
	public void testAppStatusOnly() {
		given().auth().basic(configUtility.username, configUtility.password).when().request("HEAD", BASE_URL).then().statusCode(200);
	}

	@Test
	public void testAppStatusBasic() {
		given().auth().basic(configUtility.username, configUtility.password).get()
				.then().assertThat().statusCode(200);
	}

	@Test
	public void testAppStatusPreemptive() {
		given().auth().preemptive()
				.basic(configUtility.username, configUtility.password).when().request("HEAD", BASE_URL).then().statusCode(200);

	}

	@Test
	public void testAppStatusDigest() {
		given().auth()
				.digest(configUtility.username, configUtility.password).when().request("HEAD", BASE_URL).then().statusCode(200);

	}

	@BeforeAll
	public static void addCsrfCookieFilter() {
		RestAssured.filters(new Filter() {
			@Override
			public Response filter(FilterableRequestSpecification requestSpec,
								   FilterableResponseSpecification responseSpec, FilterContext ctx) {
				String csrfToken = requestSpec.getCookies().getValue("XSRF-TOKEN");
				if (csrfToken == null) {
					csrfToken = RestAssured.given().noFilters().get("/login").cookie("XSRF-TOKEN");
				}
				requestSpec.replaceHeader("X-XSRF-TOKEN", csrfToken);
				return ctx.next(requestSpec, responseSpec);
			}
		});
	}

	protected SessionData login(String username, String password) {
		SessionFilter sessionFilter = new SessionFilter();
		Response getLoginResponse =
				given()
						.filter(sessionFilter)
				.when()
						.get("/login")
				.then()
						.extract().response();

		String token = getLoginResponse.header("XSRF-TOKEN");

		RestAssured.given().log().all()
				.filter(sessionFilter)
				.header("X-XSRF-TOKEN", token)
				.param("username", username)
				.param("password", password)
				.when()
				.post("/login");


		Response tokenResponse =
				given().log().all().
						filter(sessionFilter).
						when().get("/login").
						then().log().all().
						extract().response();

		return new SessionData(tokenResponse.header("XSRF-TOKEN"), sessionFilter.getSessionId());
	}

	@Test
	public void addUser() throws JSONException {
		FormAuthConfig formAuthConfig = new FormAuthConfig("login","username","password");
		formAuthConfig.withAutoDetectionOfCsrf();
		formAuthConfig.withCsrfFieldName("_csrf");
		System.out.println("ff: " + formAuthConfig.getCsrfFieldName());

		SessionFilter sessionFilter = new SessionFilter();
//		SessionData sessionData = login("admin", "changeme");
//
//		final Response responseAddUser = RestAssured.given()
//				.header("X-XSRF-TOKEN", sessionData.getCsrf())
//				.filter(sessionFilter)
//				.queryParam("name","test5")
//				.queryParam("email", "test5@gmail.com")
//				.post( "/adduser");

		given().auth().form(configUtility.username, configUtility.password, formAuthConfig)
				.when().get("/login").then().log().all().extract().response();

		given()
					.auth().form(configUtility.username, configUtility.password, FormAuthConfig.springSecurity().withCsrfFieldName("_csrf").sendCsrfTokenAsFormParam())
				.when()
						.queryParam("name","test5")
						.queryParam("email", "test5@gmail.com")
						.post("/adduser")
				.then()
					.log().all().extract().response();

//		Response response = given()
//				.auth().form(configUtility.username, configUtility.password, new FormAuthConfig("login", "username", "password"))
//				.when()
//				.get( "/login")
//				.then().log().all().extract().response();

//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("name", "test1");
//		jsonObject.put("email", "test1@gmail.com");
//
//		//1) get sessionId
//		Response response = given().auth().basic(configUtility.username, configUtility.password).contentType(ContentType.JSON)
//				.when().get().then().log().all().extract().response();
//		String jsessionidId =  response.getSessionId();//or response.cookie("JSESSIONID");
//
//		//2) get XSRF-TOKEN using new/real sessionId
//		response = given().sessionId("JSESSIONID", jsessionidId).contentType(ContentType.JSON)
//				.when().get().then().log().all().extract().response();
////		response = given().sessionId(jsessionidId).contentType(ContentType.JSON)
////				.when().get().then().log().all().extract().response();
//		Map<String, String> xsrf = response.getCookies();
//		xsrf.forEach((k,v) -> System.out.println(k + ":" + v));
//		//3) post data using XSRF-TOKEN
//		given().log().all()
//				.sessionId(jsessionidId)
//				.header("X-XSRF-TOKEN", response.cookie("XSRF-TOKEN"))
//				.queryParam("name", "test5")
//				.queryParam("email", "test5@gmail.com")
//				.post(BASE_URL + "/adduser")
//				.then()
//				.log().all().assertThat().statusCode(200);

//		Response loginResponse = given().contentType(ContentType.JSON)
//				.param(configUtility.username)
//				.param(configUtility.password)
//				.when().post(BASE_URL + "/login").then().log().all().extract().response();

//		given().contentType(APPLICATION_JSON).
//				cookie("XSRF-TOKEN", loginResponse.cookie("XSRF-TOKEN")).
//				header("X-XSRF-TOKEN", loginResponse.cookie("XSRF-TOKEN")).
//				sessionId(loginResponse.getSessionId()).
//				when().post(USER_PATH).
//				then().log().all().statusCode(CREATED.value());

//		given().auth().basic(configUtility.username, configUtility.password)
//				.log().all()
//				.header("Accept", ContentType.JSON.getAcceptHeader())
//				.cookie("XSRF-TOKEN", loginResponse.cookie("XSRF-TOKEN"))
//				.header("X-XSRF-TOKEN", loginResponse.cookie("XSRF-TOKEN"))
//				.sessionId(loginResponse.getSessionId())
////				.body(jsonObject.toString())
//				.urlEncodingEnabled(true)
//				.param("name", "test4")
//				.param("email", "test4@gmail.com")
//				.post(BASE_URL + "/adduser")
//				.then().statusCode(302);

	}

	@Test
	public void getUser() {
		given().auth().basic(configUtility.username, configUtility.password).get(BASE_URL + "/users").then().statusCode(200)
				.body("name", Matchers.equalTo("rohan"));
	}

}
