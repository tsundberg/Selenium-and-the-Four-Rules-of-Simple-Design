package se.thinkcode;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BuyMandatoryBooksTest {

    @Test
    public void shouldBuyWorkingEffectivelyWithLegacyCode() throws Exception {
        URL url = getClass().getResource("/geckodriver");
        String geckoDriverPath = url.getFile();
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        WebDriver driver = new FirefoxDriver();

        driver.get("http://www.amazon.de");

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));

        searchBox.sendKeys("Working Effectively with Legacy Code");
        searchBox.sendKeys(Keys.RETURN);

        WebElement resultList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("s-results-list-atf")));

        WebElement theBook = null;

        List<WebElement> resultListElements = resultList.findElements(By.className("s-access-detail-page"));

        for (WebElement element : resultListElements) {
            String title = element.getAttribute("title");
            if (title.contains("Working Effectively with Legacy Code")) {
                theBook = element;
                break;
            }
        }

        assertThat(theBook).isNotNull();
        theBook.click();

        WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));
        addToCartButton.click();


        WebElement itemInShoppingBag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("huc-v2-order-row-icon")));

        String htmlClass = itemInShoppingBag.getAttribute("class");

        assertThat(htmlClass).containsIgnoringCase("a-alert-success");

        driver.quit();
    }

}
