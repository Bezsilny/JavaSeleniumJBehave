import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OperaTest {

    private static WebDriver driver;
    public Gitlab gitlab;

    @BeforeClass
    public static void setUp() throws Exception {
        System.setProperty("webdriver.opera.driver", "resources/operadriver");
        driver = new OperaDriver();
        driver.manage().window().setSize(new Dimension(1300,850));
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
    public void csearchingWordActivity() throws Exception{
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");
        gitlab.explicitlogin("zgitlabselenium", "gitlabselenium1");

        gitlab.searchActivity("activity");

        String dashboard_search = driver.getTitle();
        Assert.assertThat(dashboard_search, containsString("Search"));

        gitlab.logout();
    }

    @Test
    public void dsearchingRandomWord() throws Exception{
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");
        gitlab.explicitlogin("zgitlabselenium", "gitlabselenium1");

        gitlab.unknownSearchActivity("asdasdklnaskflnasklfnsaasf");

        String dashboard_search = driver.findElement(By.tagName("body")).getText();
        Assert.assertThat(dashboard_search, containsString("We couldn't find any results matching asdasdklnaskflnasklfnsaasf"));

        gitlab.logout();
    }

    @Test
    public void eformularzZmianaProfilu() throws Exception{
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");

        gitlab.login("zgitlabselenium", "gitlabselenium1");
        driver.findElement(By.cssSelector("body > header > div > div > div.navbar-collapse.collapse > ul > li.header-user.dropdown > a")).click();
        driver.findElement(By.cssSelector("body > header > div > div > div.navbar-collapse.collapse > ul > li.header-user.dropdown.open > div > ul > li:nth-child(4) > a")).click();
        //czyszczenie pola
        driver.findElement(By.id("user_name")).clear();
        driver.findElement(By.id("user_name")).sendKeys("Janusz Selenium");

        //rozwinięcie listy z jezykami i wyszukanie niemieckiego
        driver.findElement(By.id("select2-chosen-2")).click();
        new Actions(driver).sendKeys("deut").perform();
        new Actions(driver).sendKeys(Keys.ENTER).perform();

        driver.findElement(By.id("user_skype")).clear();
        driver.findElement(By.id("user_skype")).sendKeys("JanuszSeleniumSkype");

        driver.findElement(By.id("user_organization")).clear();
        driver.findElement(By.id("user_organization")).sendKeys("testerzy selenium");

        driver.findElement(By.id("user_bio")).clear();
        driver.findElement(By.id("user_bio")).sendKeys("Oto biografia Janusza Selenium testera.");
        new Actions(driver).sendKeys(Keys.ENTER).perform();
        new Actions(driver).sendKeys("Nazywam sie Janusz Selenium i robie testy w selenium w javie.").perform();

        driver.findElement(By.name("commit")).click();
        //sprawdzenie czy profil zostal zmieniony
        String updatedProfile = driver.findElement(By.tagName("body")).getText();
        Assert.assertFalse(updatedProfile.contains("Zenon"));

        gitlab.logout();
    }

    @Test
    public void fformularzZmianaJezyka() throws Exception{
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");

        gitlab.login("zgitlabselenium", "gitlabselenium1");
        driver.findElement(By.cssSelector("body > header > div > div > div.navbar-collapse.collapse > ul > li.header-user.dropdown > a")).click();
        driver.findElement(By.cssSelector("body > header > div > div > div.navbar-collapse.collapse > ul > li.header-user.dropdown.open > div > ul > li:nth-child(4) > a")).click();
        //czyszczenie pola

        //rozwinięcie listy z jezykami i wyszukanie niemieckiego
        driver.findElement(By.id("select2-chosen-2")).click();
        new Actions(driver).sendKeys("engl").perform();
        new Actions(driver).sendKeys(Keys.ENTER).perform();

        driver.findElement(By.name("commit")).click();
        //sprawdzenie czy profil zostal zmieniony
        String updatedProfile = driver.findElement(By.tagName("body")).getText();
        Assert.assertFalse(updatedProfile.contains("Zenon"));

        gitlab.logout();
    }

    //inne testy
    @Test
    public void geditMilestone() throws Exception{
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        gitlab = PageFactory.initElements(driver, Gitlab.class);
        gitlab.getDriver(driver, "https://gitlab.com/");

        gitlab.login("zgitlabselenium", "gitlabselenium1");
        driver.findElement(By.xpath("//*[@title='Milestones']")).click();
        driver.findElement(By.cssSelector("#milestone_538411 > div:nth-child(1) > div:nth-child(1) > strong > a")).click();

        driver.findElement(By.cssSelector("#content-body > div.table-holder > table > tbody > tr > td:nth-child(1) > a")).click();
        driver.findElement(By.cssSelector("#content-body > div > aside > div > div.block.start_date.hide-collapsed > div.title > a")).click();

        driver.findElement(By.id("milestone_title")).clear();
        driver.findElement(By.id("milestone_title")).sendKeys("Tytul ulegl zmianie2.");

        driver.findElement(By.id("milestone_description")).clear();
        driver.findElement(By.id("milestone_description")).sendKeys("To jest nowy opis sprintu.");

        driver.findElement(By.id("milestone_start_date")).clear();
        driver.findElement(By.id("milestone_start_date")).sendKeys("2018-06-13");

        driver.findElement(By.name("commit")).click();

        String updatedProfile = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue(updatedProfile.contains("Upcoming"));

    }

    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }

}
