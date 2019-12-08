package com.in28minutes.springboot.web;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assume;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginIntegrationTestCase
        extends SeleniumIntegrationTestBase {

    private static final Logger logger = LoggerFactory.getLogger(LoginIntegrationTestCase.class);

    @Test
    public void testFirefox() throws MalformedURLException, IOException {

        //Assume.assumeTrue(RUN_FIREFOX);

        logger.info("executing test in firefox");

        WebDriver driver = null;
        try {
            Capabilities browser = new FirefoxOptions();
            driver = new RemoteWebDriver(new URL(SELENIUM_HUB_URL), browser);
            testNewPetFirstVisit(driver, TARGET_SERVER_URL);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    @Test
    public void testChrome() throws MalformedURLException, IOException {

        //Assume.assumeTrue(RUN_CHROME);

        logger.info("executing test in chrome");

        WebDriver driver = null;
        try {
            Capabilities browser = new ChromeOptions();
            driver = new RemoteWebDriver(new URL(SELENIUM_HUB_URL), browser);
            testNewPetFirstVisit(driver, TARGET_SERVER_URL);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }


    public void testNewPetFirstVisit(final WebDriver driver, final String baseUrl) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            driver.get("http://localhost:8081");
            driver.findElement(By.id("username")).sendKeys("in28minutes");
            driver.findElement(By.id("password")).sendKeys("dummy");
            driver.findElement(By.xpath("/html/body/div[@class='container']/form[@class='form-signin']/button[@class='btn btn-lg btn-primary btn-block']")).click();
            WebElement firstResult = wait.until(presenceOfElementLocated(By.xpath("/html/body/nav[@class='navbar navbar-default']/div[1]/a[@class='navbar-brand']")));
            System.out.println(firstResult.getText());
        } finally {
            driver.quit();
        }

    }
}

