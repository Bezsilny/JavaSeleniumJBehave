import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;//import
import static org.hamcrest.CoreMatchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChromeTest {

    private static WebDriver driver;
    public Gitlab gitlab;

    @BeforeClass
    public static void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
        driver = new ChromeDriver();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @Test
    public void asuccessfulLogin() throws Exception{
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//implicitly
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");
        gitlab.login("zgitlabselenium", "gitlabselenium1");
        assertEquals("Projects · Dashboard · GitLab", driver.getTitle());

        gitlab.logout();
    }

    @Test
    public void bunsuccessfulLogin() throws Exception{
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//implicitly
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");
       gitlab.login("zgitlabselenium", "gitlabselenium");

        String unableToLogin = driver.findElement(By.xpath("//span")).getText();
        assertEquals("Invalid Login or password.", unableToLogin);
    }

    @Test
    public void csearchingActivity() throws Exception{
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");
        gitlab.explicitlogin("zgitlabselenium", "gitlabselenium1");

        gitlab.searchActivity("activity");

        String dashboard_search = driver.getTitle();
        Assert.assertThat(dashboard_search, containsString("Search"));

    }

    @Test
    public void dsearchingRandom() throws Exception{
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");
        //gitlab.explicitlogin("zgitlabselenium", "gitlabselenium1");

        gitlab.unknownSearchActivity("asdasdklnaskflnasklfnsaasf");

        String dashboard_search = driver.findElement(By.tagName("body")).getText();
        Assert.assertThat(dashboard_search, containsString("We couldn't find any results matching asdasdklnaskflnasklfnsaasf"));
    }
    @Test
    public void einneSprawdzenieCzyZmienilURL() throws Exception{
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");

        driver.findElement(By.cssSelector("#content-body > div > div.nav-block > ul > li:nth-child(2) > a")).click();
        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://gitlab.com/?personal=true&sort=latest_activity_desc" );
    }

    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }


}
