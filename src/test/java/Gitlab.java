import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Gitlab {

    public WebDriver driver;
    private Wait<WebDriver> wait;

    @FindBy(id = "user_login")
    private WebElement login;

    @FindBy(id = "user_password")
    private WebElement password;

    @FindBy(xpath = "//*[@type='submit']")
    private WebElement submit;

    @FindBy(xpath ="/html/body/header/nav/div/div[3]/ul/li[9]/a")
    private  WebElement signin;

    @FindBy(id = "search")
    private WebElement search;

    @FindBy(xpath = "/html/body/div/div/div[3]/div/div/div[1]/div[3]/a")
    private WebElement newProject;

    @FindBy(id = "project_path")
    private WebElement projectName;

    @FindBy(id = "project_description")
    private WebElement projectDescription;

    @FindBy(id ="project_visibility_level_10")
    private WebElement visibilityLevel;

    @FindBy(name = "commit")
    private WebElement create;

    @FindBy(xpath = "//span[contains(text(),'ProjektDoEdycji')]")
    private WebElement chooseProject;

    @FindBy(xpath = "/html/body/div[1]/div[2]/div[3]/div/div[3]/div[2]/div/div[3]/div/table/tbody/tr/td[1]/a/span")
    private WebElement plik;

    @FindBy(css = ".js-edit-blob")
    private WebElement edytuj;

    @FindBy(css = "#editor > div.ace_gutter > div.ace_layer.ace_gutter-layer.ace_folding-enabled")
    private WebElement zaznacz;

    @FindBy(xpath = "/html/body/div/div[2]/div[3]/div/div/div[2]/form/div[4]/button")
    private WebElement commitChanges;

    @FindBy(xpath = "//span[contains(text(),'Usuniecia')]")
    private WebElement chooseProjectUsun;

    @FindBy(css =".btn-inverted")
    private WebElement clickDelete;

    @FindBy(css ="body > header > div > div > div.navbar-collapse.collapse > ul > li.header-user.dropdown > a")
    private WebElement logout1;

    @FindBy(css ="body > header > div > div > div.navbar-collapse.collapse > ul > li.header-user.dropdown.open > div > ul > li:nth-child(7) > a")
    private WebElement logout2;

    public void getDriver(WebDriver driver, String dr){
        this.driver = driver;
        driver.get(dr);
        wait = new WebDriverWait(driver,10);
    }

    public void login(String log, String pas) throws Exception{
        signin.click();
        login.sendKeys(log);
        password.sendKeys(pas);
        submit.click();
    }

    public void explicitlogin(String log, String pas) throws Exception{
        signin.click();
        wait.until(ExpectedConditions.titleIs("Sign in · GitLab"));
        login.sendKeys(log);
        password.sendKeys(pas);
        submit.click();
        wait.until(ExpectedConditions.titleIs("Projects · Dashboard · GitLab"));
    }


    public void searchActivity(String input) throws Exception{
        search.sendKeys(input);
        search.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.titleIs("activity · Search · GitLab"));
    }

    public void unknownSearchActivity(String input) throws Exception{
        search.sendKeys(input);
        search.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.titleIs("asdasdklnaskflnasklfnsaasf · Search · GitLab"));
    }

    public void adding(String text) throws Exception{
        newProject.click();
        projectName.sendKeys("nazwa8");
        projectDescription.sendKeys("Opis projektu pokazujacego dodawanie8");
        visibilityLevel.click();
        create.click();

    }

    public void edit() throws Exception{
        chooseProject.click();
        plik.click();
        edytuj.click();
        //zaznaczenie, zeby zmienic zawartosc
        zaznacz.click();
        //dodanie tekstu
        new Actions(driver).sendKeys("Added new text").perform();
        //zapisanie
        commitChanges.click();

    }

    public void delete() throws Exception{
        chooseProjectUsun.click();
        clickDelete.click();


        Alert alert = driver.switchTo().alert();

        // pobranie wiadomosci z alerta
        String alertMessage= driver.switchTo().alert().getText();

        // wyswietlenie alerta - nie jest konieczne
        System.out.println(alertMessage);

        // akcept
        alert.accept();
        driver.switchTo().defaultContent();

    }

    public void logout() throws Exception{
        logout1.click();
        logout2.click();
    }
}
