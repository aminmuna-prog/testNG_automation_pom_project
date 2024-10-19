package testrunner;

import net.bytebuddy.build.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import config.Setup;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AdminDashboardTestRunner extends Setup{
    @BeforeTest
    public void doLogin(){
        LoginPage loginPage = new LoginPage(driver);
        if(System.getProperty("username") !=null && System.getProperty("password") !=null){
            loginPage.doLogin(System.getProperty("username"), System.getProperty("password"));
        }else {
            loginPage.doLogin("admin@test.com", "admin123" );
        }


    }

    @Test(priority = 1, description = "Check last register user and dashboard first row user is same")
    public void checkUser() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("./src/test/resources/users.json"));
        JSONObject userObj = (JSONObject) jsonArray.get(jsonArray.size()-1);
        String firstName = (String) userObj.get("firstName");
        System.out.println(firstName);
        String email =(String) userObj.get("email");
        String password = (String) userObj.get("password");
        String phoneNumber = (String) userObj.get("phonenum");
//        get data from dashboard
        List<WebElement> firstRowData = driver.findElements(By.xpath("//tbody/tr[1]/td"));
         String dashboardFirrstname =firstRowData.get(0).getText();
         System.out.println(dashboardFirrstname);
        Assert.assertTrue(dashboardFirrstname.contains(firstName));
        String dashboardEmail =firstRowData.get(2).getText();
        System.out.println(dashboardEmail);
        Assert.assertTrue(dashboardEmail.contains(email));
        String dashboardphonenum =firstRowData.get(3).getText();
        System.out.println(dashboardphonenum);
        Assert.assertTrue(dashboardphonenum.contains(phoneNumber));
    }

}

//"//tbody/tr[1]/td"
