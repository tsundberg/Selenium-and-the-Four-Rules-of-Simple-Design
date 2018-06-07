# Selenium and the four rules of simple design

Live coding session cheat sheet.

## Prepare

Clone the project 

Create a new branch for the event

    git checkout -b [name_of_your_new_branch]

## Test passes
Build once and see the execution

`./gradlew build`

## Express intent
Rename the test method to

`put_working_effectively_with_legacy_code_in_shopping_bag`

### Commit
Express intent – let the name describe what the method really does


## Duplication
Extract 

`String searchString = "Working Effectively with Legacy Code";`

### Commit
Duplication – extracted the search string


## Small
Extract the driver setup

```
URL url = getClass().getResource("/geckodriver");
String geckoDriverPath = url.getFile();
System.setProperty("webdriver.gecko.driver", geckoDriverPath);
WebDriver driver = new FirefoxDriver();

driver.get("http://www.amazon.de");
```

So it looks like this:


```
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
public void shouldBuyWorkingEffectivelyWithLegacyCode() throws Exception {
    WebDriverWait wait = new WebDriverWait(driver, 20);
    WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));

...
```


### Commit
Small - extract the driver to setup

## Test passes
Run the test and see that it still passes


## Express intent
Better name for the browser abstraction – rename driver to browser

**Do not rename the string webdriver.gecko.driver**

### Commit
Express intent - call the driver browser

## Express intent
Extract closing the browser

```
@After
public void tearDown() {
    browser.quit();
}
```

### Commit
Express intent - hide the details for shutting down the browser


## Duplication
If there is time...
 - Extract waiting to a method
 - Extract the element name to a variable called id
 - Extract the method

```
@Test
public void put_working_effectively_with_legacy_code_in_shopping_bag() {
    WebDriverWait wait = getWebDriverWait();
    WebElement searchBox = getWebElement(wait);

    String searchString = "Working Effectively with Legacy Code";
    searchBox.sendKeys(searchString);
    searchBox.sendKeys(Keys.RETURN);

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
}

private WebElement getWebElement(WebDriverWait wait) {
    String id = "twotabsearchtextbox";
    return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
}

private WebDriverWait getWebDriverWait() {
    return new WebDriverWait(browser, 20);
}
```

Look at it and notice that it didn't really give anything positive

**Revert**

## Small
We are mixing concepts
 - Navigation
 - Testing

Can we do something about that? Maybe, let's give it a try.

Extract searching for a product

```
WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));

searchBox.sendKeys("Working Effectively with Legacy Code");
searchBox.sendKeys(Keys.RETURN);
```

Into:

```
private void searchProduct(String searchString, WebDriverWait wait) {
    WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
    
    searchBox.sendKeys(searchString);
    searchBox.sendKeys(Keys.RETURN);
}
```

### Commit
Small - extracted searching for product


## Small
Extract locate product

```
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
```

Into:

```
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
```

### Commit
Small - extracted locating the product

## Small
Add product to shopping bag

Extract addToShoppingBag


```
theBook.click();

WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));
addToCartButton.click();

WebElement itemInShoppingBag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("huc-v2-order-row-icon")));
```

Into:

```
private String addProductToShoppingBag(WebDriverWait wait, WebElement addToCartButton) {
    WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));

    addToCartButton.click();    
    
    WebElement itemInShoppingBag = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("huc-v2-order-row-icon")));
    
    return itemInShoppingBag.getAttribute("class");
}
```


### Commit
Small - add product to shopping bag

## Express intent
Extract assertThatProductIsInShoppingBag

```
String htmlClass = addProductToShoppingBag(wait, addToCartButton);

assertThat(htmlClass).containsIgnoringCase("a-alert-success");

```

Into:

```
private void assertThatProductIsInShoppingBag() {
    String htmlClass = addProductToShoppingBag(wait, addToCartButton);

    assertThat(htmlClass).containsIgnoringCase("a-alert-success");
}
```

### Commit
Express intent - assert that an item was added to the shopping bag


## Express intent
Extract wait to an instance variable

```
private WebDriverWait wait;
```

Create wait in `setUp()`

Setup should look like:
```
    @Before
    public void setUp() {
        URL url = getClass().getResource("/geckodriver");
        String geckoDriverPath = url.getFile();
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        browser = new FirefoxDriver();

        browser.get("http://www.amazon.de");
        wait = new WebDriverWait(browser, 20);
    }
```

Remove `wait` as argument to all method calls in the test  

### Commit
Express intent – extracted wait and hide waiting from the test

## Express intent

Hide the details for searching and extracting the book
Extract to findProduct

Extract

```
searchProduct(searchString);

WebElement theBook = locateProduct(searchString);
```

to 

```
private WebElement findProduct(String searchString) {
    searchProduct(searchString);
    
    return locateProduct(searchString);
}
```

### Commit
Express intent - hide the details for adding and searching

## Clarity 

Format the test so 
* Arrange
* Act
* Assert

is clearly separated

The test should now look like:

```
@Test
public void put_working_effectively_with_legacy_code_in_shopping_bag() throws Exception {
    String searchString = "Working Effectively with Legacy Code";
    WebElement theBook = findProduct(searchString);

    WebElement itemInShoppingBag = addProductToShoppingBag(theBook);

    assertThatProductIsInShoppingBag(itemInShoppingBag);
}
```

### Commit
Express intent - make 
* Arrange
* Act
* Assert

explicit


## Small
Extract a page object
 - Create a new class AmazonHelper - no need to make it public
 - Move all methods to it
 - Get the test to compile


The `AmazonHelper` should look like this:

```
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

    WebElement addProductToShoppingBag(WebElement theBook) {
        theBook.click();

        WebElement addToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));
        addToCartButton.click();


        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("huc-v2-order-row-icon")));
    }

    void assertThatProductIsInShoppingBag(WebElement itemInShoppingBag) {
        String htmlClass = itemInShoppingBag.getAttribute("class");

        assertThat(htmlClass).containsIgnoringCase("a-alert-success");
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
```

The test class now becomes:

```
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
    private AmazonHelper amazonHelper;

    @Before
    public void setUp() {
        URL url = getClass().getResource("/geckodriver");
        String geckoDriverPath = url.getFile();
        System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        browser = new FirefoxDriver();

        browser.get("http://www.amazon.de");
        amazonHelper = new AmazonHelper(browser);
    }

    @After
    public void tearDown() {
        browser.quit();
    }

    @Test
    public void put_working_effectively_with_legacy_code_in_shopping_bag() {
        String searchString = "Working Effectively with Legacy Code";
        WebElement theBook = amazonHelper.findProduct(searchString);

        WebElement itemInShoppingBag = amazonHelper.addProductToShoppingBag(theBook);

        amazonHelper.assertThatProductIsInShoppingBag(itemInShoppingBag);
    }
}
```

### Commit
Small - extract a page object and hide all navigation

## Express intent - Replace the test with Cucumber and Gherkin

Lets use Cucumber to specify how the system should behave.

Start with adding Cucumber as a dependency


```
testCompile 'io.cucumber:cucumber-java:3.0.2'
testCompile 'io.cucumber:cucumber-junit:3.0.2'
```

Then add a Cucumber runner

```
package se.thinkcode;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
public class RunExamplesTest {
}
```

Add a feature file in `src/test/resources/se/thinkcode/mandatory-books.feature`


```
Feature: Buy mandatory books

  You need to study if you want to improve

  Scenario: Buy Working effectively with legacy systems
    Given I search for Working Effectively with Legacy Code
    When I find it
    Then I should put it in my shopping bag
```

Run `RunExamplesTest` from Idea

Copy the snippets and add them to a new class in `src/test/java/se/thinkcode/MandatoryBooksSteps.java`

```
package se.thinkcode;

import cucumber.api.PendingException;
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

    @Given("I search for (.*)")
    public void i_search(String searchName) {
        theBook = helper.findProduct(searchName);
    }

    @When("I find it")
    public void i_find_it() {
        itemInShoppingBag = helper.addToShoppingBag(theBook);
    }

    @Then("^I should put it in my shopping bag$")
    public void i_should_put_it_in_my_shopping_bag() {
        helper.assertThatProductIsInShoppingBag(itemInShoppingBag);
    }
}
```

### Commit
Express intent - make it easier to read and validate for non technical people


## Duplication

Remove the test class `src/test/java/se/thinkcode/BuyMandatoryBooksTest.java`

### Commit
Duplication - no need to test the same thing twice


