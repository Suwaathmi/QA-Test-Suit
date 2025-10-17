package com.learn.demo.ui;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignInTest extends BaseTest {

    @Test
    public void testSignIn() {
        driver.get("http://localhost:5173/login");

        driver.findElement(By.id("email")).sendKeys("testuser@gmail.com");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // Wait for dashboard or welcome message
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.or(
                        ExpectedConditions.urlContains("/dashboard"),
                        ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Welcome testuser')]"))
                ));

        boolean success = driver.getCurrentUrl().contains("/dashboard") ||
                driver.getPageSource().contains("Welcome testuser");

        assertTrue(success, "Login should redirect to dashboard or show welcome message");
    }
}
