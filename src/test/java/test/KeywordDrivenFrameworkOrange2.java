package test;

import base.BaseOrange;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class KeywordDrivenFrameworkOrange2 {
    private WebDriver driver;
    private WebDriverWait wait;
    private BaseOrange base;
    private Properties locators;



    public void initialize() {
        base = new BaseOrange();
        WebDriverManager.chromedriver().setup();
        driver = base.initializeBrowser();
        wait = new WebDriverWait(driver, 10); // Initialize WebDriverWait with a timeout of 10 seconds
    }

    public void executeTest() {
        try {
            base.initializeProperties("src/test/resources/config.properties");
            locators = new Properties();
            FileInputStream locatorsFile = new FileInputStream("src/test/resources/locators.properties");
            locators.load(locatorsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Retrieve login credentials from properties file
        String username = base.getProperties().getProperty("username");
        String password = base.getProperties().getProperty("password");

        // Navigate to the OrangeHRM login page
        openUrl(base.getProperties().getProperty("url"));

        // Enter the login credentials
        enterText(username, "usernameInput");
        enterText(password, "passwordInput");

        // Click on the login button
        clickElement("loginButton");

        // Wait for the dashboard page to load
        wait.until(ExpectedConditions.urlContains("dashboard"));
    }

    public void openUrl(String url)
    {
        driver.get(url);
    }

    public void enterText(String text, String locatorKey) {
        if (text != null && !text.isEmpty()) {
            String locator = locators.getProperty(locatorKey);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            element.sendKeys(text);
        } else {
            System.out.println("Invalid text input: " + text);
        }
    }

    public void clickElement(String locatorKey) {
        String locator = locators.getProperty(locatorKey);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
        element.click();
    }

    public void closeBrowser() {
        //driver.quit();
    }
}
