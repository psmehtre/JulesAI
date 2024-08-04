package tests;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginAutomation {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        try {
            // Open the login page
            driver.get("https://demo.haroldwaste.com/authentication");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            System.out.println(driver.getCurrentUrl());

            // Confirm the user is on the login page
            String expectedUrl = "https://demo.haroldwaste.com/authentication";
            if (driver.getCurrentUrl().equals(expectedUrl)) {
                System.out.println("User is on the login page");

                // 1st test case
                performLogin(driver, "wrong@julesai.com", "QaJULES2023!", "Incorrect email and correct password");
                
                // Reload the page for the next test case
                driver = restartDriver(driver, options);
                performLogin(driver, "qa@julesai.com", "wrongpassword", "Correct email and incorrect password");
                
                // Reload the page for the next test case
                driver = restartDriver(driver, options);
                performLogin(driver, "wrong@julesai.com", "wrongpassword", "Incorrect email and incorrect password");
                
                // Reload the page for the next test case
                driver = restartDriver(driver, options);
                performLogin(driver, "qa@julesai.com", "QaJULES2023!", "Correct email and correct password");
            } else {
                System.out.println("User is not on the login page");
            }
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    @SuppressWarnings("deprecation")
    private static WebDriver restartDriver(WebDriver driver, ChromeOptions options) {
        driver.quit();
        WebDriver newDriver = new ChromeDriver(options);
        newDriver.manage().window().maximize();
        newDriver.get("https://demo.haroldwaste.com/authentication");
        newDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return newDriver;
    }

    private static void performLogin(WebDriver driver, String email, String password, String scenarioDescription) {
        try {
            // Enter the user email
            WebElement emailField = driver.findElement(By.xpath("//input[@name='email']"));
            emailField.clear();
            emailField.sendKeys(email);

            // Enter the user password
            WebElement passwordField = driver.findElement(By.xpath("//input[@name='password']"));
            passwordField.clear();
            passwordField.sendKeys(password);

            // Click on submit
            WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']//span[@class='MuiButton-label']"));
            submitButton.click();

            // Wait for some time to allow login processing (can use WebDriverWait for better handling)
            Thread.sleep(2000);

            // Confirm the login was successful or unsuccessful
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("purchases")) {
                System.out.println(scenarioDescription + ": Login successful");
                driver.quit();
            } else {
                System.out.println(scenarioDescription + ": Login unsuccessful");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
