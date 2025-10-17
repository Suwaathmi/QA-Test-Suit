package com.learn.demo.ui;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignUpTest extends BaseTest {

    @Test
    public void testSignUp() {
        driver.get("http://localhost:5173/register");

        driver.findElement(By.id("name")).sendKeys("test user");
        driver.findElement(By.id("email")).sendKeys("testuser@gmail.com");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.id("confirmPassword")).sendKeys("password123");

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        // Wait for either welcome message or redirect
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.or(
                        ExpectedConditions.urlContains("/login"),
                        ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Welcome')]"))
                ));

        boolean success = driver.getPageSource().contains("Welcome") ||
                driver.getPageSource().contains("Sign in") ||
                driver.getCurrentUrl().contains("/login");

        assertTrue(success, "Sign-up should redirect or show welcome message");
    }
}
