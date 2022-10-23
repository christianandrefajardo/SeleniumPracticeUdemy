package udemymavenpractice.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import udemymavenpractice.Pages.LoginPage;

public class BaseTest {

	public WebDriver driver;
	public LoginPage login;

	public WebDriver initializeDriver() throws IOException {
		// invoke Chrome driver
		Properties properties = new Properties();
		FileInputStream inStream = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/udemymaven/practice/Resources/GlobalData.properties");
		properties.load(inStream);
		// Fetching parameters when executing via Command Prompt
		String browserName = System.getProperty("browser") !=null ? System.getProperty("browser") : properties.getProperty("browser");
		//properties.getProperty("browser");

		if (browserName.contains("chrome")) {
		    ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless")) {
			    // Adding options for headless execution
			    options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
			// ensure running in fullscreen via headless
			driver.manage().window().setSize(new Dimension(1440,900));
		} else if(browserName.equalsIgnoreCase("edge")){
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		else {
		    System.out.println("No browsers available");
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}

	@BeforeMethod(alwaysRun = true)
	public LoginPage launchApplication() throws IOException {
		WebDriver driver = initializeDriver();
		login = new LoginPage(driver);
		String url = "https://rahulshettyacademy.com/client/";
		login.goToPage(url);
		return login;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
	}

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		// read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		// String to HashMap- Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
		// {map, map}
	}

	public String screenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}

}
