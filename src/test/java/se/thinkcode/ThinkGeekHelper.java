package se.thinkcode;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class ThinkGeekHelper {

    public void buySonicScrewDriver() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get("http://www.thinkgeek.com");
            driver.findElement(By.linkText("Doctor Who")).click();

            WebElement productListing = driver.findElement(By.xpath("html/body/div[3]/div[4]/div/div[2]/div[2]"));
            List<WebElement> products = productListing.findElements(By.tagName("div"));
            for (WebElement product : products) {
                String productDescription = product.getText();
                if (productDescription.startsWith("11th Doctor Sonic Screwdriver")) {
                    product.findElement(By.tagName("a")).click();
                    break;
                }
            }
            driver.findElement(By.id("submitcart")).click();


            driver.findElement(By.id("topnav_cart")).click();
            driver.findElement(By.id("cart-table"));
            List<WebElement> cartRows = driver.findElements(By.className("cart-table-row"));
            assertThat(cartRows.size(), is(1));
            WebElement productRow = cartRows.get(0);

            WebElement productLink = productRow.findElement(By.partialLinkText("Sonic Screwdriver"));
            assertNotNull(productLink);

            WebElement quantityElement = productRow.findElement(By.name("0_qty"));
            String quantity = quantityElement.getAttribute("value");
            assertThat(quantity, is("1"));

        } finally {
            driver.quit();
        }
    }

    public void buyMinecraftUniversityYouthHoodie() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get("http://www.thinkgeek.com");
            driver.findElement(By.linkText("Minecraft")).click();

            WebElement productListing = driver.findElement(By.xpath("html/body/div[3]/div[4]/div/div[2]/div[2]"));
            List<WebElement> products = productListing.findElements(By.tagName("div"));
            for (WebElement product : products) {
                String productDescription = product.getText();
                if (productDescription.startsWith("Minecraft University Youth Hoodie")) {
                    product.findElement(By.tagName("a")).click();
                    break;
                }
            }
            driver.findElement(By.id("submitcart")).click();


            driver.findElement(By.id("topnav_cart")).click();
            driver.findElement(By.id("cart-table"));
            List<WebElement> cartRows = driver.findElements(By.className("cart-table-row"));
            assertThat(cartRows.size(), is(1));
            WebElement productRow = cartRows.get(0);

            WebElement productLink = productRow.findElement(By.partialLinkText("Youth Hoodie"));
            assertNotNull(productLink);

            WebElement quantityElement = productRow.findElement(By.name("0_qty"));
            String quantity = quantityElement.getAttribute("value");
            assertThat(quantity, is("1"));

        } finally {
            driver.quit();
        }

    }

    public void buyBackToTheFutureFluxCapacitor() {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get("http://www.thinkgeek.com/interests/back-to-the-future/");

            WebElement productListing = driver.findElement(By.xpath("html/body/div[3]/div[4]/div/div[2]/div[2]"));
            List<WebElement> products = productListing.findElements(By.tagName("div"));
            for (WebElement product : products) {
                String productDescription = product.getText();
                if (productDescription.startsWith("Back to the Future Flux Capacitor")) {
                    product.findElement(By.tagName("a")).click();
                    break;
                }
            }
            driver.findElement(By.id("submitcart")).click();


            driver.findElement(By.id("topnav_cart")).click();
            driver.findElement(By.id("cart-table"));
            List<WebElement> cartRows = driver.findElements(By.className("cart-table-row"));
            assertThat(cartRows.size(), is(1));
            WebElement productRow = cartRows.get(0);

            WebElement productLink = productRow.findElement(By.partialLinkText("Flux Capacitor"));
            assertNotNull(productLink);

            WebElement quantityElement = productRow.findElement(By.name("0_qty"));
            String quantity = quantityElement.getAttribute("value");
            assertThat(quantity, is("1"));

        } finally {
            driver.quit();
        }
    }
}
