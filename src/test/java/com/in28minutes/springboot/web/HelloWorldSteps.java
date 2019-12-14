package com.in28minutes.springboot.web;

import org.junit.Assert;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class HelloWorldSteps {

    private HelloWorld helloWorld = new HelloWorld();


    private String name = "";

    private String output = "";

    @Given("^A String name (.*)$")
    public void givenInput(String name) {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        WebDriver driver = new ChromeDriver();
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
        this.name = name;
    }
    @When("^sayHello method of HelloWorld.java is called$")
    public void whenBusinessLogicCalled() {
        output = helloWorld.sayHello(name);
    }
    @Then("^It should return (.*)$")
    public void thenCheckOutput(String response) {
        Assert.assertEquals(output, response);
    }


    public static void main(String[] args) {
    }
}
