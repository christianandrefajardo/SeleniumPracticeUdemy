package udemymavenpractice.Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import udemymavenpractice.AbstractComponents.AbstractComponent;

public class ProductCatalogPage extends AbstractComponent {

	WebDriver driver;

	public ProductCatalogPage(WebDriver driver) {
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.CSS, using = ".mb-3")
	List<WebElement> products;
	
	@FindBy(css = ".ng-animating")
	WebElement spinner;

	By productsBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");

	public List<WebElement> getProductList() {
		waitForElementToDisplay(productsBy);
		return products;
	}

	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);
		return prod;
	}

	public void addProduct(String productName) throws InterruptedException {
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToDisplay(toastMessage);
		waitForElementToDisappear(spinner);
	}

}
