import org.jbehave.core.annotations.*;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.Steps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepsRunner1
{
    private static WebDriver driver;


    @Given("Mam $numer")
    public void website(String numer) throws Exception {
        System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
        driver = new ChromeDriver();
    }
    @When("Zaloguje sie wpisujac login $log i haslo $pas")
    public void loginTest(String log, String pas) throws Exception {
        LoginForScenarios login = PageFactory.initElements(driver, LoginForScenarios.class);
        login.Login(log, pas);
    }

    @Then("Otrzymam tytul strony $wynik")
    public void tytulTest(String wynik) {
        assertEquals("Projects · Dashboard · GitLab", driver.getTitle());
        assertTrue(driver.getTitle().contains(wynik));
        driver.quit();
    }

    @Given("nowe2")
    public void website1() throws Exception {
        System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
        driver = new ChromeDriver();
    }
    @When("Zaloguje podajac zly $logi i zle $pasi")
    public void unsuccessfulLoginTest(String logi, String pasi) throws Exception {
        LoginForScenarios login = PageFactory.initElements(driver, LoginForScenarios.class);
        login.Login(logi, pasi);
    }

    @Then("Otrzymam blad $wyniki")
    public void informacjaOBledzieTest(String wyniki) {
       // assertTrue(driver.getTitle().contains(wyniki));
        String unableToLogin = driver.findElement(By.xpath("//span")).getText();
        assertEquals(unableToLogin, wyniki);
        driver.quit();
    }

    @Given("nowe3")
    public void website3() throws Exception {
        System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
        driver = new ChromeDriver();
    }
    @When("Zaloguje ze zlym $loginem i zlym $haslem")
    @Alias("SprobujeZalogowac ze zlym $loginem i zlym $haslem")
    public void login3Test(String loginem, String haslem) throws Exception {
        LoginForScenarios login = PageFactory.initElements(driver, LoginForScenarios.class);
        login.Login(loginem, haslem);
    }

    @Then("Otrzymam bledny $wynikiem")
    public void tytul3Test(String wynikiem) {
        // assertTrue(driver.getTitle().contains(wyniki));
        String unableToLogin = driver.findElement(By.xpath("//span")).getText();
        assertEquals(unableToLogin, wynikiem);
        driver.quit();
    }

    @Given("nowe4")
    public void website4() throws Exception {
        System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
        driver = new ChromeDriver();
        LoginForScenarios login = PageFactory.initElements(driver, LoginForScenarios.class);
        login.Login("zgitlabselenium", "gitlabselenium1");
    }
    @When("Rozpoczne {wyszukiwanie|szukanie|szukac} slowa $search")
    public void szukaj4Test(String search) throws Exception {
        LoginForScenarios searching = PageFactory.initElements(driver, LoginForScenarios.class);
        searching.searchActivity("activity");
    }

    @Then("Otrzymam wynik szukania $result")
    public void tytulZawieraSzukaneSlowo4Test(String result) {
        String dashboard_search = driver.getTitle();
        assertTrue(dashboard_search.contains(result));
        driver.quit();
    }

    @Given("szukanie nowe5")
    public void website5() throws Exception {
        System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
        driver = new ChromeDriver();
        LoginForScenarios login = PageFactory.initElements(driver, LoginForScenarios.class);
        login.Login("zgitlabselenium", "gitlabselenium1");
    }
    @When("Zaczne {wyszukiwanie|szukanie|szukac} slowa $unknownSearch")
    public void szukajDlugiegoWyrazeniaTest(String search) throws Exception {
        LoginForScenarios searching = PageFactory.initElements(driver, LoginForScenarios.class);
        searching.searchActivity("asdasdklnaskflnasklfnsaasf");
    }

    @Then("otrzymam wynik braku znalezienia $unknownResult")
    @Alias("dostane wynik braku znalezienia $unknownResult")
    public void InformacjaOBrakuTest(String result) {
        String dashboard_search = driver.findElement(By.tagName("body")).getText();
        assertTrue(dashboard_search.contains(result));
        driver.quit();
    }

    @Given("bedac zalogowanym nowe6")
    public void profile6() throws Exception {
        System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
        driver = new ChromeDriver();
        LoginForScenarios login = PageFactory.initElements(driver, LoginForScenarios.class);
        login.Login("zgitlabselenium", "gitlabselenium1");
    }
    @When("wejde do edycji profilu")
    public void wejdzDoEdycji() throws Exception {
        driver.findElement(By.cssSelector("body > header > div > div > div.navbar-collapse.collapse > ul > li.header-user.dropdown > a")).click();
        driver.findElement(By.cssSelector("body > header > div > div > div.navbar-collapse.collapse > ul > li.header-user.dropdown.open > div > ul > li:nth-child(4) > a")).click();
    }

    @Then("{sprawdze|zobacze} czy znajduje sie w edycji i widze $edycja")
    public void ZnajdowanieSieElementuTest(String avatar) {
        String body = driver.findElement(By.tagName("body")).getText();
        assertTrue(body.contains(avatar));
        driver.quit();
    }

    @Given("mam podane wartosci: $ranksTable")
    public void givenTable(ExamplesTable ranksTable){
        System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
        driver = new ChromeDriver();
        LoginForScenarios logowanie = PageFactory.initElements(driver, LoginForScenarios.class);
        System.out.println("Test: " + ranksTable.getRow(0).get("login"));
        logowanie.Login(ranksTable.getRow(0).get("llogin"), ranksTable.getRow(0).get("ppassword"));
}

    @Then("dostaje tytul: $table")
    public void thenTable(ExamplesTable table)
    {
        assertTrue(driver.getTitle().contains(table.getRow(0).get("mmessage")));
        driver.quit();
    }
}