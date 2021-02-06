package com.automation.testapplication;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class StepDefinitions {
    public WebDriver driver;
    public String BASE_URL = "http://localhost:8080";

    @After
    public void tearDown() {
        BrowserFactory.closeAllDrivers();
    }

    @Given("I am in Landing Page")
    public void i_am_in_landing_page() {
        driver = BrowserFactory.getBrowser(BrowserName.CHROME);
        driver.manage().timeouts().implicitlyWait(20,  TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @Then("^I verify Landing Page opens successfully$")
    public void i_verify_landing_page_opens_successfully() {
        driver.getTitle().contains("Test Application");
        driver.getPageSource().contains("Welcome to Test Application");
    }


    @And("I click Add New User")
    public void i_click_add_new_user() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.linkText("Add a new user")));
        driver.findElement(By.linkText("Add a new user")).click();
    }

    @Given("I enter Username as {string} and Email as {string}")
    public void i_enter_username_as_and_email_as(String username, String email) {
        waitForVisibleElementBy("name");
        waitForVisibleElementBy("email");
        assertTrue(driver.getTitle().contains("User Sign-up"));
        driver.findElement(By.name("name")).sendKeys(username);
        driver.findElement(By.name("email")).sendKeys(email);
    }

    @When("I click Add User button")
    public void i_click_add_user_button() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='submit'][value='Add User']")));
        driver.findElement(By.cssSelector("input[type='submit'][value='Add User']")).click();

    }

    @And("I verify Username as {string} and Email as {string} are added successfully")
    public void i_verify_username_as_and_email_as_are_added_successfully(String username, String email) {
        assertTrue(driver.getPageSource().contains(username));
        assertTrue(driver.getPageSource().contains(email));
    }

    private void waitForVisibleElementBy(String elementName) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name(elementName)));
    }



}
