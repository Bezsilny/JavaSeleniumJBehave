import org.jbehave.core.model.ExamplesTable;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginForScenarios
{
    public WebDriver driver;

    @FindBy(id = "user_login")
    private WebElement login;

    @FindBy(id = "user_password")
    private WebElement password;

    @FindBy(xpath = "//*[@type='submit']")
    private WebElement submit;

    @FindBy(id = "search")
    private WebElement search;

    private List<WebElement> liItems;

    private WebElement invalidPassword;

    public LoginForScenarios(WebDriver driver)
    {
        this.driver = driver;
        driver.get("https://gitlab.com/users/sign_in");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void Login(String Login, String Password)
    {
        login.sendKeys(Login);
        password.sendKeys(Password);
        password.submit();
    }


    public void searchActivity(String input) throws Exception{
        search.sendKeys(input);
        search.sendKeys(Keys.ENTER);
    }
}