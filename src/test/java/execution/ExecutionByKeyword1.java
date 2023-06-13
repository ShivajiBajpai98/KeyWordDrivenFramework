package execution;

import base.Base1;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ExecutionByKeyword1 {
    private WebDriver driver;
    private Properties properties;
    private final String scenarioSheetPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "scenarios.xlsx";


    public void startExecution(String scenarioName) throws IOException {
        Base1 base = new Base1();
        driver = base.initializeBrowser("chrome");
        properties = base.initializeProperties();





        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(scenarioSheetPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sheet sheet = workbook.getSheet("Scenarios");
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            String currentScenario = row.getCell(0).getStringCellValue();
            if (currentScenario.equalsIgnoreCase(scenarioName)) {
                executeScenario(row);
                break;
            }
        }

        driver.quit();
    }

    private void executeScenario(Row scenarioRow) throws IOException {
        int colCount = scenarioRow.getLastCellNum() - scenarioRow.getFirstCellNum();
        for (int j = 1; j <= colCount; j++) {
            Cell cell = scenarioRow.getCell(j);
            if (cell != null) {
                String keyword = cell.getStringCellValue();
                executeKeyword(keyword);
            }
        }
    }

  /*  private void executeKeyword(String keyword) {
        switch (keyword.toLowerCase()) {
            case "openbrowser":
                // Open Browser Keyword
                String url = properties.getProperty("url");
                driver.get(url);
                break;
            case "inputtext":
                // Input Text Keyword
                String[] inputTextData = properties.getProperty("input_text_data").split("\\|");
                String inputTextElementId = inputTextData[0];
                String inputTextValue = inputTextData[1];
                WebElement inputTextElement = driver.findElement(By.id(inputTextElementId));
                inputTextElement.sendKeys(inputTextValue);
                break;
            case "clickbutton":
                // Click Button Keyword
                String buttonElementId = properties.getProperty("button_element_id");
                WebElement buttonElement = driver.findElement(By.id(buttonElementId));
                buttonElement.click();
                break;
            case "closebrowser":
                // Close Browser Keyword
                driver.quit();
                break;
            default:
                System.out.println("Invalid keyword: " + keyword);
                break;
        }
    }


    private void executeKeyword2(String keyword) {
        switch (keyword.toLowerCase()) {
            case "openbrowser":
                // Open Browser Keyword
                String url = properties.getProperty("url");
                driver.get(url);
                break;
            case "navigateto":
                // Navigate To Keyword
                String navigateToUrl = properties.getProperty("navigate_to_url");
                driver.get(navigateToUrl);
                break;
            case "clickelement":
                // Click Element Keyword
                String elementId = properties.getProperty("element_id");
                WebElement element = driver.findElement(By.id(elementId));
                element.click();
                break;
            case "entertext":
                // Enter Text Keyword
                String[] inputTextData = properties.getProperty("input_text_data").split("\\|");
                String inputTextElementId = inputTextData[0];
                String inputTextValue = inputTextData[1];
                WebElement inputTextElement = driver.findElement(By.id(inputTextElementId));
                inputTextElement.sendKeys(inputTextValue);
                break;
            case "cleartext":
                // Clear Text Keyword
                String clearTextElementId = properties.getProperty("clear_text_element_id");
                WebElement clearTextElement = driver.findElement(By.id(clearTextElementId));
                clearTextElement.clear();
                break;
            case "selectoptionbytext":
                // Select Option By Text Keyword
                String[] selectByTextData = properties.getProperty("select_by_text_data").split("\\|");
                String selectByTextElementId = selectByTextData[0];
                String selectByTextValue = selectByTextData[1];
                WebElement selectByTextElement = driver.findElement(By.id(selectByTextElementId));
                Select selectByText = new Select(selectByTextElement);
                selectByText.selectByVisibleText(selectByTextValue);
                break;
            case "selectoptionbyvalue":
                // Select Option By Value Keyword
                String[] selectByValueData = properties.getProperty("select_by_value_data").split("\\|");
                String selectByValueElementId = selectByValueData[0];
                String selectByValue = selectByValueData[1];
                WebElement selectByValueElement = driver.findElement(By.id(selectByValueElementId));
                Select selectByValueDropdown = new Select(selectByValueElement);
                selectByValueDropdown.selectByValue(selectByValue);
                break;
            case "verifytextpresent":
                // Verify Text Present Keyword
                String expectedText = properties.getProperty("expected_text");
                String actualText = driver.findElement(By.tagName("body")).getText();
                if (actualText.contains(expectedText)) {
                    System.out.println("Text '" + expectedText + "' is present on the page.");
                } else {
                    System.out.println("Text '" + expectedText + "' is not present on the page.");
                }
                break;
            case "verifyelementpresent":
                // Verify Element Present Keyword
                String verifyElementId = properties.getProperty("verify_element_id");
                WebElement verifyElement = driver.findElement(By.id(verifyElementId));
                if (verifyElement.isDisplayed()) {
                    System.out.println("Element with ID '" + verifyElementId + "' is present on the page.");
                } else {
                    System.out.println("Element with ID '" + verifyElementId + "' is not present on the page.");
                }
                break;
            case "verifyelementvisible":
                // Verify Element Visible Keyword
                String verifyVisibleElementId = properties.getProperty("verify_visible_element_id");
                WebElement verifyVisibleElement = driver.findElement(By.id(verifyVisibleElementId));
                if (verifyVisibleElement.isDisplayed()) {
                    System.out.println("Element with ID '" + verifyVisibleElementId + "' is visible on the page.");
                } else {
                    System.out.println("Element with ID '" + verifyVisibleElementId + "' is not visible on the page.");
                }
                break;
            case "waitelementvisible":
                // Wait For Element Visible Keyword
                String waitForVisibleElementId = properties.getProperty("wait_for_visible_element_id");
                WebDriverWait wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(waitForVisibleElementId)));
                break;
            case "waitelementclickable":
                // Wait For Element Clickable Keyword
                String waitForClickableElementId = properties.getProperty("wait_for_clickable_element_id");
                WebDriverWait waitClickable = new WebDriverWait(driver, 10);
                waitClickable.until(ExpectedConditions.elementToBeClickable(By.id(waitForClickableElementId)));
                break;
            case "capturescreenshot":
                // Capture Screenshot Keyword
                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotPath = "path/to/save/screenshot.png";
                try {
                    FileUtils.copyFile(screenshotFile, new File(screenshotPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Screenshot captured and saved at: " + screenshotPath);
                break;
            case "assertequals":
                // Assert Equals Keyword
                String expectedValue = properties.getProperty("expected_value");
                String actualValue = driver.findElement(By.id("some_element_id")).getText();
                Assert.assertEquals(actualValue, expectedValue);
                break;
            case "asserttrue":
                // Assert True Keyword
                String conditionElementId = properties.getProperty("condition_element_id");
                WebElement conditionElement = driver.findElement(By.id(conditionElementId));
                Assert.assertTrue(conditionElement.isDisplayed());
                break;
            case "closebrowser":
                // Close Browser Keyword
                driver.quit();
                break;
            default:
                System.out.println("Invalid keyword: " + keyword);
                break;
        }
    }

*/
    private void executeKeyword(String keyword) throws IOException {
        switch (keyword.toLowerCase()) {
            case "openbrowser":
                // Open Browser Keyword
                String url = properties.getProperty("url");
                driver.get(url);
                break;
            case "navigateto":
                // Navigate To Keyword
                String navigateToUrl = properties.getProperty("navigate_to_url");
                driver.get(navigateToUrl);
                break;
            case "clickelement":
                // Click Element Keyword
                String elementLocator = properties.getProperty("element_locator");
                WebElement element = driver.findElement(getLocator(elementLocator));
                element.click();
                break;
            case "entertext":
                // Enter Text Keyword
                String[] inputTextData = properties.getProperty("input_text_data").split("\\|");
                String inputTextLocator = inputTextData[0];
                String inputTextValue = inputTextData[1];
                WebElement inputTextElement = driver.findElement(getLocator(inputTextLocator));
                inputTextElement.sendKeys(inputTextValue);
                break;
            case "cleartext":
                // Clear Text Keyword
                String clearTextLocator = properties.getProperty("clear_text_locator");
                WebElement clearTextElement = driver.findElement(getLocator(clearTextLocator));
                clearTextElement.clear();
                break;
            case "selectoptionbytext":
                // Select Option By Text Keyword
                String[] selectByTextData = properties.getProperty("select_by_text_data").split("\\|");
                String selectByTextLocator = selectByTextData[0];
                String selectByTextValue = selectByTextData[1];
                WebElement selectByTextElement = driver.findElement(getLocator(selectByTextLocator));
                Select selectByText = new Select(selectByTextElement);
                selectByText.selectByVisibleText(selectByTextValue);
                break;
            case "selectoptionbyvalue":
                // Select Option By Value Keyword
                String[] selectByValueData = properties.getProperty("select_by_value_data").split("\\|");
                String selectByValueLocator = selectByValueData[0];
                String selectByValue = selectByValueData[1];
                WebElement selectByValueElement = driver.findElement(getLocator(selectByValueLocator));
                Select selectByValueDropdown = new Select(selectByValueElement);
                selectByValueDropdown.selectByValue(selectByValue);
                break;
            case "verifytextpresent":
                // Verify Text Present Keyword
                String expectedText = properties.getProperty("expected_text");
                String actualText = driver.findElement(By.tagName("body")).getText();
                if (actualText.contains(expectedText)) {
                    System.out.println("Text '" + expectedText + "' is present on the page.");
                } else {
                    System.out.println("Text '" + expectedText + "' is not present on the page.");
                }
                break;
            case "verifyelementpresent":
                // Verify Element Present Keyword
                String verifyElementLocator = properties.getProperty("verify_element_locator");
                List<WebElement> verifyElements = driver.findElements(getLocator(verifyElementLocator));
                if (!verifyElements.isEmpty()) {
                    System.out.println("Element with locator '" + verifyElementLocator + "' is present on the page.");
                } else {
                    System.out.println("Element with locator '" + verifyElementLocator + "' is not present on the page.");
                }
                break;
            case "verifyelementvisible":
                // Verify Element Visible Keyword
                String verifyVisibleLocator = properties.getProperty("verify_visible_locator");
                WebElement verifyVisibleElement = driver.findElement(getLocator(verifyVisibleLocator));
                if (verifyVisibleElement.isDisplayed()) {
                    System.out.println("Element with locator '" + verifyVisibleLocator + "' is visible on the page.");
                } else {
                    System.out.println("Element with locator '" + verifyVisibleLocator + "' is not visible on the page.");
                }
                break;
            case "waitelementvisible":
                // Wait For Element Visible Keyword
                String waitForVisibleLocator = properties.getProperty("wait_for_visible_locator");
                WebDriverWait wait = new WebDriverWait(driver, 10);
                wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(waitForVisibleLocator)));
                break;
            case "waitelementclickable":
                // Wait For Element Clickable Keyword
                String waitForClickableLocator = properties.getProperty("wait_for_clickable_locator");
                WebDriverWait waitClickable = new WebDriverWait(driver, 10);
                waitClickable.until(ExpectedConditions.elementToBeClickable(getLocator(waitForClickableLocator)));
                break;
            case "capturescreenshot":
                // Capture Screenshot Keyword
                File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotPath = "path/to/save/screenshot.png";
                FileUtils.copyFile(screenshotFile, new File(screenshotPath));
                System.out.println("Screenshot captured and saved at: " + screenshotPath);
                break;
            case "assertequals":
                // Assert Equals Keyword
                String expectedValue = properties.getProperty("expected_value");
                String actualValue = driver.findElement(getLocator("some_element_locator")).getText();
                Assert.assertEquals(actualValue, expectedValue);
                break;
            case "asserttrue":
                // Assert True Keyword
                String conditionLocator = properties.getProperty("condition_locator");
                WebElement conditionElement = driver.findElement(getLocator(conditionLocator));
                Assert.assertTrue(conditionElement.isDisplayed());
                break;
            case "closebrowser":
                // Close Browser Keyword
                driver.quit();
                break;
            default:
                System.out.println("Invalid keyword: " + keyword);
                break;
        }
    }

    private By getLocator(String locatorString) {
        String[] locatorParts = locatorString.split("=", 2);
        String locatorType = locatorParts[0].trim().toLowerCase();
        String locatorValue = locatorParts[1].trim();

        switch (locatorType) {
            case "id":
                return By.id(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "class":
            case "classname":
                return By.className(locatorValue);
            case "tag":
            case "tagname":
                return By.tagName(locatorValue);
            case "link":
            case "linktext":
                return By.linkText(locatorValue);
            case "partiallink":
            case "partiallinktext":
                return By.partialLinkText(locatorValue);
            case "css":
            case "cssselector":
                return By.cssSelector(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            default:
                throw new IllegalArgumentException("Invalid locator type: " + locatorType);
        }
    }


}
