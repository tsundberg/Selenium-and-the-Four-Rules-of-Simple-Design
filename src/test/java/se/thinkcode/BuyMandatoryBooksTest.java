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

    private WebDriver browser;

    @Before
    public void setUp() {
        URL url = getClass().getResource("/geckodriver");
        String geckoDriverPath = url.getFile();
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        browser = new FirefoxDriver();

        browser.get("http://www.amazon.de");
    }

    @Test
    public void put_working_effectively_with_legacy_code_in_shopping_bag() throws Exception {
        String searchString = "Working Effectively with Legacy Code";

        WebDriverWait wait = new WebDriverWait(browser, 20);

        searchProduct(searchString, wait);

        WebElement theBook = locateProduct(searchString, wait);
        theBook.click();

        WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));

        String htmlClass = addProductToShoppingBag(wait, addToCartButton);

        assertThat(htmlClass).containsIgnoringCase("a-alert-success");

        browser.quit();
    }

    private String addProductToShoppingBag(WebDriverWait wait, WebElement addToCartButton) {
        addToCartButton.click();


        WebElement itemInShoppingBag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("huc-v2-order-row-icon")));

        return itemInShoppingBag.getAttribute("class");
    }

    private WebElement locateProduct(String searchString, WebDriverWait wait) {
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
        return theBook;
    }

    private void searchProduct(String searchString, WebDriverWait wait) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));

        searchBox.sendKeys(searchString);
        WebElement searchButton = browser.findElement(By.id("nav-search-submit-text"));

        searchButton.click();
    }

}
