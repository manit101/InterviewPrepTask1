package interviewPrepFiles.BasicAutomationTask;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasicAutomationTask {
	
	WebDriver driver;
	
	@BeforeMethod
	  public void setup() {
        WebDriverManager.chromedriver().setup(); 
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
	
	@Test(priority=1)
	public void checkoutTest() throws InterruptedException {
		driver.get("https://bookcart.azurewebsites.net/");
		Assert.assertTrue(driver.getTitle().contains("Home"));
		
		WebElement cartIcon = driver.findElement(By.id("mat-badge-content-0"));
		Assert.assertEquals(cartIcon.getText(), "0");
		
		WebElement inputBox = driver.findElement(By.xpath("//input[@type=\"search\"]"));
		inputBox.sendKeys("The Martian");
		
		WebElement suggestion = driver.findElement(By.xpath("//span[contains(text(), 'The Martian')]"));
		Assert.assertEquals(suggestion.getText(), "The Martian");
		suggestion.click();

		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		
		WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class=\"mdc-button mdc-button--raised mat-mdc-raised-button mat-primary mat-mdc-button-base\"]")));
		addToCartBtn.click();
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("mat-badge-content-0"), "1"));
		Assert.assertEquals(cartIcon.getText(), "1");

		WebElement cart = driver.findElement(By.xpath("//button[@class=\"mdc-icon-button mat-mdc-icon-button mat-unthemed mat-mdc-button-base ng-star-inserted\"]"));
		cart.click();
		WebElement itemTitle = driver.findElement(By.xpath("//td[@class=\"mat-mdc-cell mdc-data-table__cell cdk-cell cdk-column-title mat-column-title ng-star-inserted\"]//a"));
		Assert.assertEquals(itemTitle.getText(), "The Martian");

		WebElement checkOutBtn = driver.findElement(By.xpath("//button[@class=\"my-2 mdc-button mdc-button--raised mat-mdc-raised-button mat-warn mat-mdc-button-base\"]"));
		checkOutBtn.click();
		wait.until(ExpectedConditions.titleContains("Login"));
		Assert.assertTrue(driver.getTitle().contains("Login"));
	}
	
	@AfterMethod
	 public void tearDown () {
		 driver.quit();
	 } 
}
