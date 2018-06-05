import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SafariTest {

    private static WebDriver driver;
    public Gitlab gitlab;

    @BeforeClass
    public static void setUp() throws Exception {
        driver = new SafariDriver();
        driver.manage().window().setSize(new Dimension(1200,800));
    }

    @Test
    public void safariLogin() throws Exception{

        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(2, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#main-gl-nav > ul > li:nth-child(9) > a")));
        driver.findElement(By.cssSelector("#main-gl-nav > ul > li:nth-child(9) > a")).sendKeys(Keys.ENTER);//submit();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_login")));

        driver.findElement(By.id("user_login")).sendKeys("zgitlabselenium");
        driver.findElement(By.id("user_password")).sendKeys("zgitlabselenium1");
        driver.findElement(By.cssSelector("#new_user > div.submit-container.move-submit-down > input")).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.titleContains("Projects"));
        assertEquals("Projects · Dashboard · GitLab", driver.getTitle());
    }

    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }

}
