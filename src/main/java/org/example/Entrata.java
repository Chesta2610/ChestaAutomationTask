
package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class Entrata {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\bh\\IdeaProjects\\untitled\\src\\test\\resources\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Fix the variable scope here
    }

    @Test
    public void testHomePageTitle() {
        driver.get("https://www.entrata.com");
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Property Management Software | Entrata", "Page title does not match.");
    }

    @Test
    public void testClickElement() {
        driver.get("https://www.entrata.com/");

        WebElement summitLink = driver.findElement(By.xpath("//a[contains(text(), 'Summit')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", summitLink);

        // Switch to the new tab
        String originalWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();

        for (String handle : windowHandles) {
            if (!handle.equals(originalWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        // Wait for the new page to load and verify its title or URL
        wait.until(webDriver -> webDriver.getTitle().contains("Summit"));

        // Verify the page URL
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("https://summit.entrata.com"), "Did not navigate to the Summit page.");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
