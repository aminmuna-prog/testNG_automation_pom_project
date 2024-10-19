package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchItemPage {
    @FindBy(className = "search-input")
    public WebElement searchInput;


    public SearchItemPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

}

