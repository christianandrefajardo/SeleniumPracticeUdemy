package udemymavenpractice.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import udemymavenpractice.AbstractComponents.AbstractComponent;

public class LoginPage extends AbstractComponent {
	
	WebDriver driver; 
	
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	//WebElement userName = driver.findElement(By.xpath("//input[contains(@id,'Email')]"));
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'Email')]")
	WebElement userName;
	
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'userPassword')]")
	WebElement passwordField;
	
	@FindBy(how = How.XPATH, using = "//input[contains(@id,'login')]")
	WebElement loginButton;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	public ProductCatalogPage login(String username, String password) {
		userName.sendKeys(username);
		System.out.println("=== Username: "+username);
		passwordField.sendKeys(password);
		System.out.println("=== Password: "+password);
		loginButton.click();
		System.out.println("=== Click Login");
		ProductCatalogPage productPage = new ProductCatalogPage(driver);
		return productPage;
	}
	
	public String getErrorMessage()
	{
		waitForWebElementToDisplay(errorMessage);
		return errorMessage.getText();
	}
	
	public void goToPage(String url) {
		System.out.println("=== Navigating to: "+url+" ===");
		driver.get(url);
	}
	
	
}
