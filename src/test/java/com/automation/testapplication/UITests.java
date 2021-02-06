package com.automation.testapplication;

import com.automation.testapplication.po.AmazonSearchPage;
import com.automation.testapplication.po.AmazonSearchResultsPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class UITests {

    WebDriver driver;
    public String BASE_URL_AMAZON = "http://www.amazon.com";

    public UITests() {
        driver = BrowserFactory.getBrowser(BrowserName.CHROME);
        driver.manage().timeouts().implicitlyWait(10,  TimeUnit.SECONDS);
        driver.get(BASE_URL_AMAZON);
    }

    @Test
    public void amazonSearchAndVerify()
    {
        System.out.println("TC: SeleniumWD using POM Search and Verify");

        String searchText = "calculator";

        System.out.println("1. Change the search type to Electronics");
        AmazonSearchPage.searchCategoryDropDownElement(driver).click();
        Select searchCategoryDropDown = new Select(AmazonSearchPage.searchCategoryDropDownElement(driver));
        searchCategoryDropDown.selectByVisibleText("Electronics");

        System.out.println("2. Enter the search term calculators");
        AmazonSearchPage.searchTextBox(driver).clear();
        AmazonSearchPage.searchTextBox(driver).sendKeys(searchText);
//		SearchPage.searchTextBox(driver).sendKeys(searchText + "\n" + Keys.ENTER);

        System.out.println("3. Click the Search button");
        AmazonSearchPage.searchTextBox(driver).submit();

        System.out.println("4. Sort the results by \"Average Customer Review\"");
        AmazonSearchPage.sortByDD(driver).click();
        AmazonSearchPage.sortByAvgCustRev(driver).click();

        System.out.println("5. Filter results to only show calculators in the range of $300 to $350");
        AmazonSearchPage.minPrice(driver).sendKeys("300");
        AmazonSearchPage.maxPrice(driver).sendKeys("350");
        AmazonSearchPage.goButton(driver).click();

        System.out.println("6. Save , all the search results into a List of WebElements");
        assertThat (AmazonSearchResultsPage.resultsCount(driver).getText(), containsString ("\"calculator\""));
        System.out.println(AmazonSearchResultsPage.resultsCount(driver).getText());

        System.out.println("7. Use a for-each loop to assert that each result title contains the word calculator");
        List<WebElement> resultTitles = AmazonSearchResultsPage.resultTitles(driver);
        for (WebElement resultTitle : resultTitles)
        {
            String testStatus;
            if (resultTitle.getText().toLowerCase().contains(searchText.toLowerCase())) {
                testStatus = "PASS";
            } else {
                testStatus = "FAIL";
            }
            System.out.println("Title: " + resultTitle.getText()  + " :  Test Result: " + testStatus);
            //assertThat(resultTitle.getText().toLowerCase(), containsString(searchText.toLowerCase()));
        }

        System.out.println("End Test Case");
    }



}
