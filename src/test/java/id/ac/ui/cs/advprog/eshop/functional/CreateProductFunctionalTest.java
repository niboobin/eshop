package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d/product/create", testBaseUrl, serverPort);
    }

    @Test
    void CreateProductTest(ChromeDriver driver) throws Exception {
        String productName = "Sampo Cap Bambang";
        int productQuantity = 100;

        driver.get(baseUrl);

        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        productNameInput.clear();
        productNameInput.sendKeys(productName);

        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        productQuantityInput.clear();
        productQuantityInput.sendKeys(String.valueOf(productQuantity));

        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()=\"Product' List\"]")));

        assertDoesNotThrow(() -> driver.findElement(By.xpath("//td[text()='"+ productName +"']")));
        assertDoesNotThrow(() -> driver.findElement(By.xpath("//td[text()='"+ productQuantity +"']")));

        WebElement productNameOnPage = driver.findElement(By.xpath("//td[text()='"+ productName +"']"));
        WebElement productQuantityOnPage = driver.findElement(By.xpath("//td[text()='"+ productQuantity +"']"));

        assertEquals(productName, productNameOnPage.getText());
        assertEquals(String.valueOf(productQuantity), productQuantityOnPage.getText());
    }
}
