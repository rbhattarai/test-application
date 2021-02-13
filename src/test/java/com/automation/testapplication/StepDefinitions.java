package com.automation.testapplication;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class StepDefinitions {
    public WebDriver driver;
    public String BASE_URL = "http://localhost:8080";

    @AfterAll
    public void tearDown() {
        BrowserFactory.closeAllDrivers();
    }

    @After
    public void logout() {
        driver.get(BASE_URL + "/logout");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div > form > button")));
        driver.findElement(By.cssSelector("body > div > form > button")).click();
        Assert.assertTrue(driver.getPageSource().contains("Please sign in"));
    }

    public WebDriver getBrowserDriver() {
        driver = BrowserFactory.getBrowser(BrowserName.CHROME);
        return (driver == null) ? BrowserFactory.getBrowser(BrowserName.CHROME) : driver;
    }

    public StepDefinitions() {
        driver = getBrowserDriver();
        driver.manage().timeouts().implicitlyWait(20,  TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Given("I am in Landing Page")
    public void i_am_in_landing_page() {
        driver.get(BASE_URL);
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("changeme");
        driver.findElement(By.cssSelector("body > div > form > button")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Test Application"));
    }

    @Then("^I verify Landing Page opens successfully$")
    public void i_verify_landing_page_opens_successfully() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.titleContains("Test Application"));

        Assert.assertTrue(driver.getTitle().contains("Test Application"));
        Assert.assertTrue(driver.getPageSource().contains("Welcome to Test Application"));
    }

    @And("I click Add New User")
    public void i_click_add_new_user() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Add a new user')]")));
        driver.findElement(By.xpath("//a[contains(text(),'Add a new user')]")).click();
    }

    @And("I enter Username as {string} and Email as {string}")
    public void i_enter_username_as_and_email_as(String username, String email) {
        assertTrue(driver.getTitle().contains("User Sign-up"));
        waitForVisibleElementBy("name");
        driver.findElement(By.name("name")).sendKeys(username);
        waitForVisibleElementBy("email");
        driver.findElement(By.name("email")).sendKeys(email);
    }

    @When("I click Add User button")
    public void i_click_add_user_button()  {
        WebElement button = driver.findElement(By.cssSelector("input[type='submit'][value='Add User']"));
        WebDriverWait wait = new WebDriverWait(driver, 45);
        WebElement element = wait.until(ExpectedConditions.visibilityOf(button));
        button.submit();
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

    private WebElement getElement(String by, String value) {
        Map<String, WebElement> elementMap = new HashMap<>();
        elementMap.put("id", driver.findElement(By.id(value)));
        elementMap.put("name", driver.findElement(By.name(value)));
        elementMap.put("cssSelector", driver.findElement(By.cssSelector(value)));
        elementMap.put("linkText", driver.findElement(By.linkText(value)));
        elementMap.put("partialLinkText", driver.findElement(By.partialLinkText(value)));
        elementMap.put("xpath", driver.findElement(By.xpath(value)));
        elementMap.put("tagName", driver.findElement(By.tagName(value)));
        elementMap.put("className", driver.findElement(By.className(value)));
        return elementMap.get(by);

    }



}
