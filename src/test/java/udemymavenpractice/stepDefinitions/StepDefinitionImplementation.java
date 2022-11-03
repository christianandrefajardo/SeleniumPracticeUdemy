package udemymavenpractice.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.*;
import udemymavenpractice.Pages.CartPage;
import udemymavenpractice.Pages.CheckoutPage;
import udemymavenpractice.Pages.ConfirmationPage;
import udemymavenpractice.Pages.LoginPage;
import udemymavenpractice.Pages.ProductCatalogPage;
import udemymavenpractice.TestComponents.BaseTest;

public class StepDefinitionImplementation extends BaseTest {

    public LoginPage loginPage;
    public ProductCatalogPage productPage;
    ConfirmationPage confirmationPage;

    @Given("I landed on Login Page")
    public void i_landed_on_login_page() throws IOException {
        loginPage = launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_with_username_and_password(String username, String password) {
        productPage = login.login(username, password);
    }

    @When("^I add the product (.+) to the Cart$")
    public void add_product_to_the_cart(String productName) throws InterruptedException {
        List<WebElement> products = productPage.getProductList();
        productPage.addProduct(productName);
    }

    @And("^Checkout (.+) and submit the order$")
    public void checkout_and_submit(String productName) {
        CartPage cartPage = productPage.goToCart();
        Boolean match = cartPage.VerifyProductDisplay(productName);
        Assert.assertTrue(match);

        // Going to Checkout
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");
        confirmationPage = checkoutPage.submitOrder();
    }

    @Then("{string} message is displayed on ConfirmationPage")
    public void message_is_displayed_on_confirmation_page(String string) {
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
        driver.close();
    }
    
    @Then("{string} is displayed")
    public void error_message_is_displayed(String string) {
        Assert.assertEquals(string, login.getErrorMessage());
        driver.close();
    }
}
