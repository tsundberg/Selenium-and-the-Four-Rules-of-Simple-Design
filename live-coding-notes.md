# Selenium and the four rules of simple design

Live coding session cheat sheet.


## Test passes
Build once and see the execution

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
Small - extracted product from result

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

***Missing code snippet!***

```

```


### Commit
Express intent - hide the details for adding and searching


## Small
Extract a page object
 - Create a new class AmazonHelper - no need to make it public
 - Move all methods to it
 - Get the test to compile


```
class AmazonHelper {
    private WebDriverWait wait;
    private WebDriver browser;

    AmazonHelper(WebDriver browser) {
        this.browser = browser;
        wait = new WebDriverWait(browser, 20);
    }

    WebElement findProduct(String searchString) {
        searchProduct(searchString);

        WebElement theBook = locateProduct(searchString);
        return addBookToShoppingBag(theBook);
    }

    void assertThatShoppingBagContainsBook(WebElement itemInShoppingBag) {
        String htmlClass = itemInShoppingBag.getAttribute("class");

        assertThat(htmlClass).containsIgnoringCase("a-alert-success");
    }

    private WebElement addBookToShoppingBag(WebElement theBook) {
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
```

```
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
    public void put_working_effectively_with_legacy_code_in_shopping_bag() throws Exception {
        String searchString = "Working Effectively with Legacy Code";

        WebElement itemInShoppingBag = amazonHelper.findProduct(searchString);

        amazonHelper.assertThatShoppingBagContainsBook(itemInShoppingBag);
    }
}
```

### Commit
Small - extract a page object and hide all navigation

## Replace the test with Cucumber and Gherkin

***Todo***



