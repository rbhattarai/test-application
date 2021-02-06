package com.automation.testapplication.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AmazonSearchPage {

    private static WebElement element = null;

    public static WebElement searchCategoryDropDownElement (WebDriver driver)
    {
        element = driver.findElement(By.id("searchDropdownBox"));
        return element;
    }

    public static WebElement searchTextBox (WebDriver driver)
    {
        element = driver.findElement(By.id("twotabsearchtextbox"));
        return element;
    }

    public static WebElement sortByDD (WebDriver driver)
    {
        element = driver.findElement(By.id("a-autoid-0-announce"));
        return element;
    }

    public static WebElement sortByAvgCustRev (WebDriver driver)
    {
        element = driver.findElement(By.id("s-result-sort-select_3"));
        return element;
    }

    public static WebElement minPrice (WebDriver driver)
    {
        element = driver.findElement(By.id("low-price"));
        return element;
    }

    public static WebElement maxPrice (WebDriver driver)
    {
        element = driver.findElement(By.id("high-price"));
        return element;
    }

    public static WebElement goButton (WebDriver driver)
    {
        element = driver.findElement(By.className("a-button-input"));
        return element;
    }
}

