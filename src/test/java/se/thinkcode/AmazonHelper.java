package se.thinkcode;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AmazonHelper {
    private WebDriverWait wait;

    AmazonHelper(WebDriver browser) {
        wait = new WebDriverWait(browser, 20);
    }

    WebElement findProduct(String searchString) {
        searchProduct(searchString);

        return locateProduct(searchString);
    }

    void assertThatProductIsInBag(WebElement itemInShoppingBag) {
        String htmlClass = itemInShoppingBag.getAttribute("class");

        assertThat(htmlClass).containsIgnoringCase("a-alert-success");
    }

    WebElement addToBag(WebElement theBook) {
        theBook.click();

        WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));
        addToCartButton.click();


        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("huc-v2-order-row-icon")));
    }

    private WebElement locateProduct(String searchString) {
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

    private void searchProduct(String searchString) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));

        searchBox.sendKeys(searchString);
        searchBox.sendKeys(Keys.RETURN);
    }
}
