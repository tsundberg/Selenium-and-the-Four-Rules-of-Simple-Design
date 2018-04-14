package se.thinkcode;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.URL;

public class BuyMandatoryBooksTest {
    private WebDriver browser;
    private AmazonHelper helper;

    @Before
    public void setUp() {
        URL url = getClass().getResource("/geckodriver");
        String geckoDriverPath = url.getFile();
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        browser = new FirefoxDriver();

        browser.get("http://www.amazon.de");
        helper = new AmazonHelper(browser);
    }

    @After
    public void tearDown() {
        browser.quit();
    }

    @Test
    public void put_working_effectively_with_legacy_code_in_shopping_bag() {
        String searchString = "Working Effectively with Legacy Code";
        WebElement theBook = helper.findProduct(searchString);

        WebElement itemInShoppingBag = helper.addToBag(theBook);

        helper.assertThatProductIsInBag(itemInShoppingBag);
    }


}
