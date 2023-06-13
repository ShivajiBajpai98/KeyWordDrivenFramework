package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class OrangeHRMLoginTest {
    public static void main(String[] args) throws InterruptedException {
        // Setup WebDriverManager for ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Maximize the browser window

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver(options);

        // Navigate to the OrangeHRM login page
        driver.get("https://opensource-demo.orangehrmlive.com");
        Thread.sleep(2000);
        // Find the username and password fields and enter the login credentials
        driver.findElement(By.xpath("//input[@placeholder='Username']")).sendKeys("admin");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("admin123");
        Thread.sleep(2000);
        // Click on the login button
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

        // Wait for the dashboard page to load
        // You can use WebDriverWait or Thread.sleep() for waiting

        // Perform further actions on the dashboard page if needed

        // Close the browser

    }
}
