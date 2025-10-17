package com.learn.demo.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Point to manually installed EdgeDriver
        System.setProperty("webdriver.edge.driver", "D:\\WebDriver\\msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--window-size=1920,1080");
        // Remove headless for local testing unless needed
        // options.addArguments("--headless");

        driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
