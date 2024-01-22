package variuosConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CrmTest {

	WebDriver driver;
	String environment;
	String browser;
	
    //Login Data
	String userName;
	String pasword;
	
	// Element List
	By USERNAME_FIELD = By.xpath("//input[@id='user_name']");
	By PASSWORD_FIELD = By.xpath("//input[@id='password']");
	By SIGNIN_BUTTON_FIELD = By.xpath("//button[@id='login_submit']");
	By DASHBOARD_HEADER_FIELD = By.xpath("//strong[text() ='Dashboard' ]");

	
	//TestData/ Mock data
	String dashboardValidationText = "Dashboard";
	String alertUserValidationText = "Please enter your user name";
	String alertpaswordValidationText = "Please enter your password";
	

	@BeforeClass
	public void readConfig() {
		//InputStream
		
	  try {
		      FileInputStream	input = new FileInputStream("src\\main\\java\\config\\config.properties");
		      Properties prop = new Properties();
		      prop.load(input);
		      browser = prop.getProperty("browser");
		      environment = prop.getProperty("url");
		      userName =  prop.getProperty("userName");
		      pasword  = prop.getProperty("pasword");
		      
	 }catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@BeforeMethod
	public void setup() {
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}else if(browser.equalsIgnoreCase("edge")){
			System.setProperty("webdriver.edge.driver","drivers\\msedgedriver.exe");
			driver = new EdgeDriver();	
		}else {
			System.out.println("please use a valid browser");
		}
		
		driver.manage().deleteAllCookies();
		driver.get(environment);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test(priority=2)
	public void loginTest5() {
		driver.findElement(USERNAME_FIELD).sendKeys(userName);
		driver.findElement(PASSWORD_FIELD).sendKeys(pasword);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
        Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FIELD).getText(), dashboardValidationText, "Dashboard page not found!!");
		}
	
	@Test(priority=1)
	public void testAlertLoginPage() {
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		String actualAlertUserText = driver.switchTo().alert().getText();
		Assert.assertEquals(actualAlertUserText, alertUserValidationText, "Alert user txt is not available");
		driver.switchTo().alert().accept();
		
		driver.findElement(USERNAME_FIELD).sendKeys(userName);
		driver.findElement(SIGNIN_BUTTON_FIELD).click();
		Assert.assertEquals(driver.switchTo().alert().getText(),alertpaswordValidationText , "Alert user txt is not available");
		driver.switchTo().alert().accept(); 
	}
	
	
	//@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}
	

	
}
