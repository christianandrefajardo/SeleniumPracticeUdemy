package udemymavenpractice.Tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import udemymavenpractice.Pages.CartPage;
import udemymavenpractice.Pages.CheckoutPage;
import udemymavenpractice.Pages.ConfirmationPage;
import udemymavenpractice.Pages.OrderPage;
import udemymavenpractice.Pages.ProductCatalogPage;
import udemymavenpractice.TestComponents.BaseTest;

public class SubmitOrderTest extends BaseTest {

//	String username = "christianandre@rahulshettyacademy.com";
//	String password = "Password123!";
//	String productName = "ZARA COAT 3";
	String countryName = "india";

	@Test(dataProvider="getData", groups="Purchase")
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException {
		// Login to Application
		ProductCatalogPage productPage = login.login(input.get("username"), input.get("password"));

		// Add Product to Cart
		List<WebElement> products = productPage.getProductList();
		productPage.addProduct(input.get("productName"));

		// Check cart and verify products
		CartPage cartPage = productPage.goToCart();
		Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);

		// Going to Checkout
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry(countryName);
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}

	@Test(dependsOnMethods ={"submitOrder"}, dataProvider="getData")
	public void OrderHistoryTest(HashMap<String,String> input) {
		// "ZARA COAT 3";
		ProductCatalogPage productCatalogue = login.login(input.get("username"), input.get("password"));
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(input.get("productName")));
	}

	@DataProvider
	public Object[][] getData() throws IOException {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("username","christianandre@rahulshettyacademy.com");
		map.put("password", "Password123!");
		map.put("productName", "ZARA COAT 3");
		
		HashMap<String,String> map2 = new HashMap<String,String>();
		map2.put("username","shetty@gmail.com");
		map2.put("password", "Iamking@000");
		map2.put("productName", "ADIDAS ORIGINAL");
		return new Object[][] {{map},{map2}};
		
//		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//udemymavenpractice//Data//PurchaseOrder.json");
//		return new Object[][] {{data.get(0)},{data.get(1)}};
	}

}
