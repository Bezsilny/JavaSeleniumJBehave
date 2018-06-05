import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GitlabTest {

    private static WebDriver driver;
    public Gitlab gitlab;

    @BeforeClass
    public static void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
        driver = new FirefoxDriver();
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

        gitlab.logout();
    }

    @Test
    public void dsearchingRandom() throws Exception{
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");
        gitlab.explicitlogin("zgitlabselenium", "gitlabselenium1");

        gitlab.unknownSearchActivity("asdasdklnaskflnasklfnsaasf");

        String dashboard_search = driver.findElement(By.tagName("body")).getText();
        Assert.assertThat(dashboard_search, containsString("We couldn't find any results matching asdasdklnaskflnasklfnsaasf"));

        gitlab.logout();
    }

    @Test
    public void ecrudAdd() throws Exception{
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//implicitly
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");
        gitlab.login("zgitlabselenium", "gitlabselenium1");

        gitlab.adding("tak");

        String existence = driver.findElement(By.tagName("body")).getText();
        Assert.assertThat(existence, containsString("Opis projektu pokazujacego dodawanie8"));

        gitlab.logout();
    }

    @Test
    public void fcrudEdit() throws Exception{

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");
        gitlab.login("zgitlabselenium", "gitlabselenium1");

        gitlab.edit();

        String editedFile = driver.findElement(By.tagName("body")).getText();
        Assert.assertThat(editedFile, containsString("Your changes have been successfully committed."));

        gitlab.logout();
    }

    @Test
    public void gcrudDelete() throws Exception{
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");
        gitlab.login("zgitlabselenium", "gitlabselenium1");

        gitlab.delete();

        gitlab.getDriver(driver, "https://gitlab.com/");
        String deletedFile = driver.findElement(By.tagName("body")).getText();
        Assert.assertFalse(deletedFile.contains("usun"));
    }
    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }

}
