package se.thinkcode;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BuyMandatoryBooksTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        URL url = getClass().getResource("/geckodriver");
        String geckoDriverPath = url.getFile();
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        driver = new FirefoxDriver();

        driver.get("http://www.amazon.de");
    }

    @Test
    public void put_working_effectively_with_legacy_code_in_shopping_bag() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));

        String searchString = "Working Effectively with Legacy Code";
        searchBox.sendKeys(searchString);
        WebElement searchButton = driver.findElement(By.id("nav-search-submit-text"));

        searchButton.click();

        WebElement resultList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("s-results-list-atf")));

        WebElement theBook = null;

        List<WebElement> resultListElements = resultList.findElements(By.className("s-access-detail-page"));

        for (WebElement element : resultListElements) {
            String title = element.getAttribute("title");
            if (title.contains(searchString)) {
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
