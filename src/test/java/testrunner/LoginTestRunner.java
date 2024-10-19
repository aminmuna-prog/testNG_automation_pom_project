package testrunner;
import config.Setup;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTestRunner extends Setup{
    LoginPage loginPage;
    @Test(priority = 1, description = "admin login with correct creds", groups = {"smoke"})
    public void adminLogin(){
        loginPage = new LoginPage(driver);
        loginPage.doLogin("admin@test.com", "admin123" );
        String headerActual = driver.findElement(By.tagName("h2")).getText();
        String headerExpected = "Admin Dashboard";
        Assert.assertTrue(headerActual.contains(headerExpected));
    }
}
