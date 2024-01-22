package variuosConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ValidateDropdownHomeWork {

	WebDriver driver;
	String browser;
	String environment;

	// Login Data
	String userName;
	String pasword;
	
	// Element List
	By USERNAME_FIELD = By.xpath("//input[@id='user_name']");
	By PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By SIGNIN_BUTTON_FIELD = By.xpath("//button[@id='login_submit']");
	By DASHBOARD_HEADER_FIELD = By.xpath("//strong[text() ='Dashboard' ]");

	By CUSTOMER_MENUE_FIELD = By.xpath("//span[text()='Customers']");
	By ADD_CUSTOMER_FIELD = By.xpath("//span[text() = 'Add Customer']");
	By NEW_CUSTOMER_HEAD_FIELD = By.xpath("//strong[contains(text(), 'New Customer')]");
	By FULL_NAME_FIELD = By.xpath("//input[@name='name']");
	By COMPANY_NAME_DROPDOWN_FIELD = By.xpath("//select[@name='company_name']");
	By EMAIL_FIELD = By.xpath("//input[@name='email']");
	By PHONE_FIELD = By.xpath("//input[@id='phone']");
	By ADDRESS_FIELD = By.xpath("//input[@name='address']");
	By CITY_FIELD = By.xpath("//input[@name='city']");
	By ZIP_CODE_FIELD = By.xpath("//input[@id='port']");
	By COUNTRY_NAME_DROPDOWN_FIELD = By.xpath("//select[@name='country']");
	By GROUP_NAME_DROPDOWN_FIELD = By.xpath("//select[@id='customer_group']");
	By SAVE_BUTTON_FIELD = By.xpath("//button[@id='save_btn']");
	
	// TestData/ Mock data
	String dashboardValidationText = "Dashboard";
	String alertUserValidationText = "Please enter your user name";
	String alertpaswordValidationText = "Please enter your password";
	String newCustomerdValidationText = "New Customer";

	String   fullName = "Sitra yeshaw";
    String   email = "demo@codefios.com";
	String 	 phoneNumber = "9363548443";
	String 	 address = "3551 wilshire Elementary";
	String 	 city = "Richardson";
	String   zipCode = "75082";
	String 	 companyName = "Techfios";
	String   countryName = "United States of America";
	String 	 groupName = "Selenium";   
	
	@BeforeClass
	public void readConfig() {
		// InputStream

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			environment = prop.getProperty("url");
			userName = prop.getProperty("userName");
			pasword = prop.getProperty("pasword");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void setup() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		} else {
			System.out.println("please use a valid browser");
		}

		driver.manage().deleteAllCookies();
		driver.get(environment);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(priority = 1)
	public void loginTest() {

		driver.findElement(USERNAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(pasword);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), dashboardValidationText,
				"Dashboard page not found!!");
	}

 //	@Test(priority = 2)
	public void addCustomer2() throws InterruptedException {
		loginTest();
		driver.findElement(CUSTOMER_MENUE_FIELD).click();
		driver.findElement(ADD_CUSTOMER_FIELD).click();
		Assert.assertEquals(driver.findElement(NEW_CUSTOMER_HEAD_FIELD).getText(), newCustomerdValidationText,
				"new Customer page not found!!");

		driver.findElement(FULL_NAME_FIELD).sendKeys(fullName );
		
		WebElement dropdown1 = driver.findElement(COMPANY_NAME_DROPDOWN_FIELD);
		Select sel = new Select(dropdown1);
		sel.selectByVisibleText(companyName);
		
		driver.findElement(EMAIL_FIELD).sendKeys( email);
		driver.findElement(PHONE_FIELD).sendKeys(phoneNumber);
		driver.findElement(ADDRESS_FIELD).sendKeys(address);
		driver.findElement(CITY_FIELD).sendKeys(city);
		driver.findElement(ZIP_CODE_FIELD).sendKeys(zipCode);
		
		WebElement dropdown2 = driver.findElement(COUNTRY_NAME_DROPDOWN_FIELD);
		Select sel1 = new Select(dropdown2);
		sel1.selectByVisibleText(countryName);
		
		WebElement dropdown3 = driver.findElement(GROUP_NAME_DROPDOWN_FIELD);
		Select sel2 = new Select( dropdown3);
		sel2.selectByVisibleText(groupName);	
		
		
	}
	

	@Test(priority = 3)
	public void validateDropDown() throws InterruptedException {
		addCustomer2();

		String expectedOptions[] = {"", "Manual", "SQL", "Selenium", "Java", "Group 2", "Group 1" };
		WebElement dropdown = driver.findElement(GROUP_NAME_DROPDOWN_FIELD);

		Select sel = new Select(dropdown);
		
		List<WebElement> options = sel.getOptions();
     
		for (int i = 0; i < options.size(); i++) {
			System.out.println(options.get(i).getText()+"-"+expectedOptions[i]);	
			Assert.assertEquals(options.get(i).getText(), expectedOptions[i]);
		}

		
	}
	
	 
	
	// @AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
