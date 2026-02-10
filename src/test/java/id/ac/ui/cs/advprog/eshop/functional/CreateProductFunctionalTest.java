package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void setup() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_success_productAppearsInList(ChromeDriver driver) {

        // ========= STEP 1: open list page =========
        driver.get(baseUrl + "/product/list");

        // ========= STEP 2: click Create Product =========
        driver.findElement(By.linkText("+ Create Product")).click();

        // ========= STEP 3: fill form =========
        driver.findElement(By.name("productName")).sendKeys("Laptop Gaming");
        driver.findElement(By.name("productQuantity")).sendKeys("5");

        // ========= STEP 4: submit =========
        driver.findElement(By.tagName("button")).click();

        // ========= STEP 5: verify product exists in table =========
        List<WebElement> rows = driver.findElements(By.tagName("tr"));

        boolean found = rows.stream()
                .anyMatch(row -> row.getText().contains("Laptop Gaming"));

        assertTrue(found);
    }
}
