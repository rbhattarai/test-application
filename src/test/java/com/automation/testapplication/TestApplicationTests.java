package com.automation.testapplication;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestApplicationTests {

	String BASE_URL = "http://localhost:8080";

	@Test
	void contextLoads() {
	}

	@Test
	public void testApplicationStatus() {
		given().get(BASE_URL)
				.then().assertThat().statusCode(200);
	}

	@Test
	public void testUserList() {
		get(BASE_URL + "/users").then().assertThat().statusCode(200)
				.body("name", Matchers.equalTo("test1"));
	}

}
