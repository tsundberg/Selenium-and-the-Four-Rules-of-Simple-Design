package se.thinkcode;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.URL;

public class MandatoryBooksSteps {
    private WebDriver browser;
    private AmazonHelper helper;
    private WebElement theBook;
    private WebElement itemInShoppingBag;

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

    @Given("^I search for (.*)$")
    public void i_search_for(String searchName) {
        theBook = helper.findProduct(searchName);
    }

    @When("^I find it$")
    public void i_find_it() {
        itemInShoppingBag = helper.addToBag(theBook);
    }

    @Then("^I should put it in my shopping bag$")
    public void i_should_put_it_in_my_shopping_bag() {
        helper.assertThatProductIsInBag(itemInShoppingBag);
    }
}
