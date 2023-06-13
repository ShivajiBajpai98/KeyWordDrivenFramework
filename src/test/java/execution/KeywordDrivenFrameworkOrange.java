package execution;

import base.BaseOrange;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;

public class KeywordDrivenFrameworkOrange {
    private WebDriver driver;
    private WebDriverWait wait;
    private BaseOrange base;
    private Properties locators;

    public static void main(String[] args) {
        KeywordDrivenFrameworkOrange framework = new KeywordDrivenFrameworkOrange();
        framework.initialize();
        framework.executeTest();
        framework.closeBrowser();
    }

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

        String filePath = "src/test/resources/scenarios.xlsx";
        String sheetName = "Sheet1";

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip the header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                executeStep(row); // Execute each step in the row
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void executeStep(Row row) {
        String step = row.getCell(1).getStringCellValue();

        switch (step) {
            case "openUrl":
                String url = row.getCell(2).getStringCellValue();
                openUrl(url);
                break;
            case "enterText":
                String text = row.getCell(2).getStringCellValue();
                String locatorKeyText = row.getCell(3).getStringCellValue();
                enterText(text, locatorKeyText);
                break;
            case "clickElement":
                String locatorKeyClick = row.getCell(2).getStringCellValue();
                clickElement(locatorKeyClick);
                break;
            // Add more cases for other steps as needed
        }
    }

    public void openUrl(String url) {
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
        driver.quit();
    }
}
