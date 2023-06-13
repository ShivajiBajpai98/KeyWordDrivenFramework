package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Base1 {
    private WebDriver driver;
    private Properties properties;

    public WebDriver initializeBrowser(String browser) {
        initializeProperties(); // Call initializeProperties method

        String browserName = properties.getProperty("browser");

        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            if (properties.getProperty("headless").equalsIgnoreCase("yes")) {
                options.addArguments("--headless");
            }

            driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();

            if (properties.getProperty("headless").equalsIgnoreCase("yes")) {
                options.addArguments("--headless");
            }

            driver = new FirefoxDriver(options);
        }

        return driver;
    }

    public Properties initializeProperties() {
        properties = new Properties();
        try {
            String filePath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "config.properties";
            FileInputStream fileInputStream = new FileInputStream(filePath);
            try {
                properties.load(fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return properties;
    }

}
