package execution;

import base.Base;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ExecutionByKeyword {
    private WebDriver driver;
    private Properties properties;
    private SoftAssert softAssert;
    private final String scenarioSheetPath = "src/test/resources/scenarios.xlsx";

    public void startExecution(String scenarioName) throws IOException {
        Base base = new Base();
        driver = base.initializeBrowser("chrome"); // Change the browser name if needed
        properties = base.initializeProperties();
        softAssert = new SoftAssert();

        FileInputStream fileInputStream = new FileInputStream(scenarioSheetPath);
        Workbook workbook = WorkbookFactory.create(fileInputStream);

        Sheet sheet = workbook.getSheet(properties.getProperty("sheet_name")); // Read the sheet name from properties
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            String currentScenario = row.getCell(0).getStringCellValue();
            if (currentScenario.equalsIgnoreCase(scenarioName)) {
                executeScenario(row);
                break;
            }
        }

        softAssert.assertAll();
        driver.quit();
    }

    private void executeScenario(Row scenarioRow) {
        int colCount = scenarioRow.getLastCellNum() - scenarioRow.getFirstCellNum();
        for (int j = 1; j <= colCount; j++) {
            Cell cell = scenarioRow.getCell(j);
            if (cell != null) {
                String step = scenarioRow.getCell(j).getStringCellValue();
                executeStep(step);
            }
        }
    }

    public void executeStep(String step) {
        String[] stepParts = step.split("\\|");
        String keyword = stepParts[0].trim();
        String locator = stepParts[1].trim();
        String value = stepParts[2].trim();

        switch (keyword.toLowerCase()) {
            case "openbrowser":
                driver.get(value); // Open the browser and navigate to the given URL
                break;
            case "navigate":
                driver.navigate().to(value); // Navigate to the given URL
                break;
            case "entertext":
                WebElement elementToEnterText = findElement(locator);
                elementToEnterText.sendKeys(value); // Enter text into the specified element
                break;
            case "clickelement":
                WebElement elementToClick = findElement(locator);
                elementToClick.click(); // Click on the specified element
                break;
            case "verifyelementpresent":
                boolean isElementPresent = isElementPresent(locator);
                softAssert.assertTrue(isElementPresent, "Element is not present: " + locator); // Verify if the element is present
                break;
            case "capturescreenshot":
                // Capture a screenshot
                break;
            default:
                System.out.println("Invalid keyword: " + keyword);
                break;
        }
    }

    private WebElement findElement(String locator) {
        By by;
        if (locator.startsWith("id=")) {
            by = By.id(locator.substring(3));
        } else if (locator.startsWith("name=")) {
            by = By.name(locator.substring(5));
        } else if (locator.startsWith("class=")) {
            by = By.className(locator.substring(6));
        } else if (locator.startsWith("xpath=")) {
            by = By.xpath(locator.substring(6));
        } else if (locator.startsWith("css=")) {
            by = By.cssSelector(locator.substring(4));
        } else if (locator.startsWith("linktext=")) {
            by = By.linkText(locator.substring(9));
        } else if (locator.startsWith("partiallinktext=")) {
            by = By.partialLinkText(locator.substring(16));
        } else {
            throw new IllegalArgumentException("Invalid locator: " + locator);
        }
        return driver.findElement(by);
    }

    private boolean isElementPresent(String locator) {
        try {
            findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
