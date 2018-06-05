

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class HtmlUnitTest {

	@Test
	public void testTitlePage(HtmlUnitDriver driver) {
		driver.get("https://about.gitlab.com/");
    	assertEquals("The only single product for the complete DevOps lifecycle - GitLab | GitLab", driver.getTitle());
	}

	@Test
	public void clickProduct(HtmlUnitDriver driver){
		driver.get("https://about.gitlab.com/");
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.titleContains("single product for the complete DevOps"));

		driver.findElement(By.xpath("/html/body/header/nav/div/div[3]/ul/li[1]/a")).click();
		wait.until(ExpectedConditions.titleIs("Product | GitLab"));

		assertTrue(driver.findElement(By.xpath("//h4[contains(text(),'Regardless of your process')]")).getText().contains("provides powerful planning tools to keep everyone synchronized"));

	}
}
