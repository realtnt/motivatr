package com.davmt.motivatr.integration;

import java.time.Duration;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.davmt.motivatr.MotivatrApplication;
import com.davmt.motivatr.model.User;
import com.davmt.motivatr.service.UserService;
import com.github.javafaker.Faker;

@SuppressWarnings("unused")
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MotivatrApplication.class)
public class ProfileTest {

  @Autowired
  public UserService userService;

  WebDriver driver;
  Faker faker;

  @Before
  public void setup() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    driver = new ChromeDriver(options);
    faker = new Faker();
    User user = new User(
        "first",
        "last",
        "user",
        "user@email.com",
        "password");
    userService.createUser(user);
  }

  @After
  public void tearDown() {
    if (driver != null) {
      driver.close();
    }
    driver.quit();
  }

  @Test
  public void profileButtonNavigatesToCorrectURL() throws InterruptedException {
    driver.get("http://localhost:8080");
    driver.findElement(By.id("username")).sendKeys("user");
    driver.findElement(By.id("password")).sendKeys("password");
    driver.findElement(By.id("btnLogin")).click();
    WebElement profileButton = new WebDriverWait(driver, Duration.ofSeconds(3))
        .until(driver -> driver.findElement(By.id("btn_profile")));
    driver.findElement(By.id("btn_profile")).click();
    String title = driver.getTitle();
    Assert.assertNotEquals("Expect title to equal Profile", "HTTP Status 404 – Not Found", title);
    WebElement firstName = new WebDriverWait(driver, Duration.ofSeconds(3))
        .until(driver -> driver.findElement(By.id("first_name")));
    Assert.assertEquals("First name should be displayed", "firstName", firstName.getText());
  }
}
