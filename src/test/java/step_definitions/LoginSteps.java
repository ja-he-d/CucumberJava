package step_definitions;

import command_providers.ActOn;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public class LoginSteps {
    private static  final By FullName = By.id("name");
    private static final By Password = By.id("password");
    private static final By Login = By.id("login");
    private static final By Logout = By.id("Logout");
    private static final By InvalidPassword =By.xpath("//*[@id='pageLogin']/form//div[text()='Password is invalid']");

    private static Logger Logger = LogManager.getLogger(LoginSteps.class);
    WebDriver driver = Hooks.driver;

    @Given("^a user is on the login page$")
    public void navigateToLoginPage() {
        ActOn.browser(driver).openBrowser("https://example.testproject.io/web/");
        Logger.info("user is in the login page");
    }

    @When("^user enters username \"(.+?)\" and password \"(.+?)\"$")
    public void enterUserCredentials(String userName, String password) {
        ActOn.element(driver, FullName).setValue(userName);
        ActOn.element(driver, Password).setValue(password);
        Logger.info("user has entered Credentials");
    }

    @And("^click on login button$")
    public void clickOnLogin() {
        ActOn.element(driver, Login).click();
        Logger.info("User clicked on login button");
    }

    @Then("^user is navigated to home page$")
    public void validateUserIsLoggedInSuccessfully() {
        boolean logoutDisplayed = driver.findElement(Logout).isDisplayed();
        Assert.assertTrue("Logout button is not displayed", logoutDisplayed);
        Logger.info("user is on the home page");
    }

    @Then("^user is failed to login$")
    public void validateUserIsFailedToLogin() {
        boolean invalidPassword = driver.findElement(InvalidPassword).isDisplayed();
        Assert.assertTrue("Invalid Password is not displayed", invalidPassword);
        Logger.info("user is is still in the login page");

    }

    @When("user click on login button upon entering credentials")
    public void userClickOnLoginButtonUponEnteringCredentials(DataTable table) {
        List<Map<String, String>> datatable = table.asMaps(String.class, String.class);
        for (Map<String, String> cells:datatable) {
            ActOn.element(driver, FullName).setValue(cells.get("username"));
            ActOn.element(driver, Password).setValue(cells.get("password"));
            Logger.info("user has entered Credentials");

            ActOn.element(driver, Login).click();
            Logger.info("User clicked on login button");
        }
    }
}
