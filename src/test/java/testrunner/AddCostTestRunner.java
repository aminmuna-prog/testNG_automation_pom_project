package testrunner;
import config.ItemDataset;
import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AddCostPage;
import pages.LoginPage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AddCostTestRunner extends Setup{
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

   @Test(priority = 1, dataProvider = "itemCSVData", dataProviderClass = ItemDataset.class, description = "Added cost from csv file", groups = {"smoke"})
    public void addCost(String itemname, int quantity, String amount, String purchasedate, String month, String remark) throws InterruptedException {
        AddCostPage addCostPage = new AddCostPage(driver);
        addCostPage.txtaddCostItem.click();
        addCostPage.txtitemName.sendKeys(itemname);
        for(int i = 1; i <=quantity; i++){
            addCostPage.txtplusBtn.click();
        }
        addCostPage.txtamount.sendKeys(amount);
        Thread.sleep(1000);
        addCostPage.datePickerTest(purchasedate);
        Select dropMonth = new Select(driver.findElement(By.id("month")));
        dropMonth.selectByVisibleText(month);
        addCostPage.txtremarks.sendKeys(remark);
        addCostPage.txtsubmitBtn.click();
        Thread.sleep(1000);
        driver.switchTo().alert().accept();
        Thread.sleep(2000);

    }
    @Test(priority = 2, description = "Check expected total cost with actual total cost", groups = {"smoke"})
    public void totalcost() throws IOException {
        AddCostPage addCostPage = new AddCostPage(driver);
        int expectedCost = addCostPage.getamount();
        List<WebElement> summary =driver.findElements(By.xpath("//div[@class='summary']/span"));
        String totalCost = summary.get(1).getText();
        String amount = totalCost.replaceAll("[^0-9]", "");
        int totalCostActual = Integer.parseInt(amount);
        Assert.assertEquals(totalCostActual, expectedCost);

    }
}