package com.davmt.motivatr.integration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.time.Duration;

import com.github.javafaker.Faker;

@RunWith(SpringJUnit4ClassRunner.class)

public class SignUpIntegrationTest {

  WebDriver driver;
  Faker faker;

  @Before
  public void setup() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    driver = new ChromeDriver(options);
    faker = new Faker();
  }

  @After
  public void tearDown() {
    if (driver != null) {
      driver.close();
    }
    driver.quit();
  }

  @Test
  public void successfulSignUpRedirectsToLogin() {
    driver.get("http://localhost:8080");
    driver.findElement(By.id("btn_signup")).click();
    WebElement h2Element = driver.findElement(By.tagName("h2"));
    Assert.assertEquals("New account", h2Element.getText());
    driver.findElement(By.id("firstName")).sendKeys(faker.name().firstName());
    driver.findElement(By.id("lastName")).sendKeys(faker.name().lastName());
    driver.findElement(By.id("email")).sendKeys(faker.internet().emailAddress());
    driver.findElement(By.id("username")).sendKeys(faker.name().username());
    String passwd = faker.internet().password();
    driver.findElement(By.id("password")).sendKeys(passwd);
    driver.findElement(By.id("passwordConfirm")).sendKeys(passwd);
    driver.findElement(By.id("submit")).click();
    WebElement loginButton = new WebDriverWait(driver, Duration.ofSeconds(3))
        .until(driver -> driver.findElement(By.id("btnLogin")));
    Assert.assertEquals("Log in", loginButton.getText());
    h2Element = driver.findElement(By.tagName("h2"));
    Assert.assertEquals("Login", h2Element.getText());
  }
}