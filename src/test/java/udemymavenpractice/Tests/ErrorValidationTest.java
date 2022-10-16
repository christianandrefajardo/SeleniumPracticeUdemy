package udemymavenpractice.Tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import udemymavenpractice.Pages.CartPage;
import udemymavenpractice.Pages.ProductCatalogPage;
import udemymavenpractice.TestComponents.BaseTest;
import udemymavenpractice.TestComponents.Retry;

public class ErrorValidationTest extends BaseTest{
	
	@Test(groups= {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void loginErrorValidation() throws IOException, InterruptedException {
		String username = "123christianandre@rahulshettyacademy.com";
		String password = "123Password123!";
		//Login to Application
		ProductCatalogPage productPage = login.login(username, password);
		Assert.assertEquals("Incorrect email //or password.", login.getErrorMessage());
	}

	@Test
	public void productErrorValidation() throws IOException, InterruptedException
	{
		String username = "christianandre@rahulshettyacademy.com";
		String password = "Password123!";
		String productName = "ZARA COAT 3";
		ProductCatalogPage productPage = login.login(username, password);
		//Add Product to Cart
		List<WebElement> products = productPage.getProductList();
		productPage.addProduct(productName);
				
		//Check cart and verify products
		CartPage cartPage = productPage.goToCart();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 5");
		Assert.assertFalse(match);
	}

}
