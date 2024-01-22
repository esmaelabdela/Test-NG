package variuosConcepts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ValidateDropdown { 
   
	  @Test
	  public void dropDown1() {
    
		 System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
		 WebDriver driver = new ChromeDriver();
		 driver.get("https://seletech.in/practice/");
		 
		 String expectedOptinns [] = {"Select", "Option1", "Option2", "Option3"};
		 
		 WebElement dropDown = driver.findElement(By.xpath("//*[@id=\"dropdown-class-example\"]"));
		 Select sel = new Select(dropDown);
		 List<WebElement> options = sel.getOptions();
		 
		 for (int i = 0; i < options.size(); i++) {
			 System.out.println(options.get(i).getText()+"-"+expectedOptinns[i]);
				Assert.assertEquals(options.get(i).getText(),expectedOptinns[i]);
			}
	}

}
