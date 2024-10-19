package testrunner;
import config.Setup;
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
import pages.SearchItemPage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SearchItemTestRunner extends Setup{

    @BeforeTest(groups = {"smoke"})
    public void doLogin() throws IOException, ParseException {
        LoginPage loginPage = new LoginPage(driver);
//        get last user from json file
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("./src/test/resources/users.json"));
        JSONObject userObj = (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email =(String) userObj.get("email");
        String password = (String) userObj.get("password");
        loginPage.doLogin(email, password);
    }
    @Test(description = "search an item and assert the item's price", groups = {"smoke"})
    public void searchItem() throws InterruptedException {
        SearchItemPage item = new SearchItemPage(driver);
        driver.findElement(By.className("search-input")).sendKeys("egg");
        List<WebElement> allData = driver.findElements(By.xpath("//tbody/tr[1]/td"));
        String itemAmount = allData.get(2).getText();
        int expectedItemAmount = Integer.parseInt(itemAmount);
        List<WebElement> summary =driver.findElements(By.xpath("//div[@class='summary']/span"));
        String totalCosttxt = summary.get(1).getText();
        String amount = totalCosttxt.replaceAll("[^0-9]", "");
        int int_totalCost = Integer.parseInt(amount);
        String itemquantityrow = summary.get(0).getText();
        String itemQuantity = itemquantityrow.replaceAll("[^0-9]", "");
        int int_itemQuantity = Integer.parseInt(itemQuantity);
        int actualTotalCost = int_totalCost/int_itemQuantity;
        Assert.assertEquals(actualTotalCost, expectedItemAmount);

    }
}
