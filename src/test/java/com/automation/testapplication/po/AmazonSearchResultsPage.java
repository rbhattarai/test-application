package com.automation.testapplication.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AmazonSearchResultsPage {
    private static WebElement element = null;
    private static List<WebElement> elements = null;

    public static WebElement resultsCount (WebDriver driver)
    {
        element = driver.findElement(By.className("sg-col-inner"));
        return element;
    }

    public static List<WebElement> resultTitles (WebDriver driver)
    {
        elements = driver.findElements(By.cssSelector("h2 a span"));
        return elements;
    }
}
