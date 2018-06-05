import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SouceLabsTest {

    public static final String USERNAME = "zgitlabselenium";
    public static final String ACCESS_KEY = "123";
    public static final String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

    @Test
    public void loginWrongInputFirefoxWindows10() throws Exception {
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("version", "latest");

        WebDriver driver = new RemoteWebDriver(new URL(URL), caps);



        driver.get("https://gitlab.com/users/sign_in");
        driver.findElement(By.id("user_login")).sendKeys("zlyLogin");
        driver.findElement(By.id("user_password")).sendKeys("zleHaslo");
        driver.findElement(By.xpath("//*[@type='submit']")).submit();

        assertEquals("Sign in · GitLab", driver.getTitle());

        driver.quit();
    }

    @Test
    public void loginWrongInputHighSierra() throws Exception {
        DesiredCapabilities caps = DesiredCapabilities.firefox();
        caps.setCapability("platform", "macOS 10.13");
        caps.setCapability("version", "60.0");

        WebDriver driver = new RemoteWebDriver(new URL(URL), caps);



        driver.get("https://gitlab.com/users/sign_in");
        driver.findElement(By.id("user_login")).sendKeys("HighSierra");
        driver.findElement(By.id("user_password")).sendKeys("HighSierra");
        driver.findElement(By.xpath("//*[@type='submit']")).submit();

        assertEquals("Sign in · GitLab", driver.getTitle());

        driver.quit();
    }
}
