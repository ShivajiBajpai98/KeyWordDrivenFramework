package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Base {
    private WebDriver driver;
    private Properties properties;

    public WebDriver initializeBrowser(String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            // Initialize Firefox driver
        } else if (browserName.equalsIgnoreCase("edge")) {
            // Initialize Edge driver
        }
        return driver;
    }

    public Properties initializeProperties() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
        properties = new Properties();
        properties.load(fileInputStream);
        return properties;
    }
}
