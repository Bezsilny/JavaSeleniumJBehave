

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.WebElement;

import io.github.bonigarcia.SeleniumExtension;

@ExtendWith(SeleniumExtension.class)
public class PhantomJSTest {

	private PhantomJSDriver driver;
	
	public PhantomJSTest(PhantomJSDriver driver) {
		this.driver = driver;
	}

	@BeforeEach
	public void setDefaultPage() {
		driver.get("https://gitlab.com/users/sign_in");
	}

	@Test
	public void testTitlePage() {
    	assertEquals("Sign in · GitLab", driver.getTitle());
	}

	@Test
	public void testLogin(){
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_login")));
		driver.findElement(By.id("user_login")).sendKeys("zgitlabselenium");
		driver.findElement(By.id("user_password")).sendKeys("gitlabselenium1");
		driver.findElement(By.cssSelector("#new_user > div.submit-container.move-submit-down > input")).sendKeys(Keys.ENTER);

		wait.until(ExpectedConditions.titleContains("Projects"));
		assertEquals("Projects · Dashboard · GitLab", driver.getTitle());
	}

	@Test
	public void unableToLogin(){
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("user_login")));
		driver.findElement(By.id("user_login")).sendKeys("zgitlabselenium");
		driver.findElement(By.id("user_password")).sendKeys("gitlabselenium123");

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span")));
		assertTrue(driver.findElement(By.xpath("//span")).getText().contains("Remember"));

	}

}
