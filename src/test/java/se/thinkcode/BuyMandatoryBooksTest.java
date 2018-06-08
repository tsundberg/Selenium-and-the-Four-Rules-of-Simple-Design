package se.thinkcode;

import org.junit.After;
import org.junit.Before;
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

    private WebDriver browser;

    @Before
    public void setUp() {
        URL url = getClass().getResource("/geckodriver");
        String geckoDriverPath = url.getFile();
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        browser = new FirefoxDriver();

        browser.get("http://www.amazon.de");
    }

    @After
    public void tearDown() {
        browser.quit();
    }

    @Test
    public void put_working_effectively_with_legacy_code_in_shopping_bag() {
        WebDriverWait wait = new WebDriverWait(browser, 20);
        String searchString = "Working Effectively with Legacy Code";

        searchProduct(wait, searchString);

        WebElement theBook = locateProduct(wait, searchString);
        WebElement itemInShoppingBag = addProductToShoppingBag(wait, theBook);

        assertThatProductIsInShoppingBag(itemInShoppingBag);
    }

    private void assertThatProductIsInShoppingBag(WebElement itemInShoppingBag) {
        String htmlClass = itemInShoppingBag.getAttribute("class");

        assertThat(htmlClass).containsIgnoringCase("a-alert-success");
    }

    private WebElement addProductToShoppingBag(WebDriverWait wait, WebElement theBook) {
        theBook.click();

        WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));
        addToCartButton.click();


        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("huc-v2-order-row-icon")));
    }

    private WebElement locateProduct(WebDriverWait wait, String searchString) {
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

    private void searchProduct(WebDriverWait wait, String searchString) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));

        searchBox.sendKeys(searchString);
        searchBox.sendKeys(Keys.RETURN);
    }

}
