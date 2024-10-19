package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AdminDashboardPage {
  public AdminDashboardPage(WebDriver driver){
      PageFactory.initElements(driver, this);
  }
}
