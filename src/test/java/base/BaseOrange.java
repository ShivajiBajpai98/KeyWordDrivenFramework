package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseOrange {
    private WebDriver driver;
    private Properties properties;

    public WebDriver initializeBrowser() {
        try {
            initializeProperties("src/test/resources/config.properties");
            String browserName = properties.getProperty("browser");
            boolean headlessMode = Boolean.parseBoolean(properties.getProperty("headless"));
            if (browserName.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup(); // Use WebDriverManager to manage ChromeDriver
                ChromeOptions options = new ChromeOptions();
                if (headlessMode) {
                    options.addArguments("--headless"); // Run in headless mode
                }
                driver = new ChromeDriver(options);
            }
            // ...
        } catch (IOException e) {
            e.printStackTrace();
        }
        return driver;
    }
    public Properties initializeProperties(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        properties = new Properties();
        properties.load(fileInputStream);
        return properties;
    }

    public Properties getProperties() {
        return properties;
    }
}
