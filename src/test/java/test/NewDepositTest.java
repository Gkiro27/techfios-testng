package test;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NewDepositTest {

	WebDriver driver;

	@BeforeMethod
	public void lauchBrowser() {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver = new ChromeDriver(); // this extentiate the chrome driver and launch the chrome browser which is the
										// construction
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // it should be 60 seconds
		driver.get("http://www.techfios.com/ibilling/?ng=admin/");
	}

	@Test
	public void userShouldBeAbleToAddDeposit() throws InterruptedException {
		driver.findElement(By.xpath("//*[@type='text']")).sendKeys("demo@techfios.com");
		driver.findElement(By.xpath("//input[contains(@placeholder, 'word')]")).sendKeys("abc123");
		driver.findElement(By.xpath("//button[text()='Sign in']")).click();
		String expectedTitle = "Dashboard- iBilling";
		Assert.assertEquals(driver.getTitle(), expectedTitle, "Dashboard Page did not display");

		By TRANSACTION_MENU_LOCATOR = By.xpath("//ul[@id='side-menu']/descendant::span[text()='Transactions']");
		By NEW_DEPOSIT_PAGE_LOCATOR = By.linkText("New Deposit");

		driver.findElement(TRANSACTION_MENU_LOCATOR).click();
		waitForElement(NEW_DEPOSIT_PAGE_LOCATOR, driver, 10);
		driver.findElement(NEW_DEPOSIT_PAGE_LOCATOR).click();

		// Select an account from DropDown
		Select select = new Select(driver.findElement(By.cssSelector("Select#account")));
		select.selectByVisibleText("Mean820");

		String expectedDescription = "AutomationTest" + new Random().nextInt(999);
		System.out.println("Expected: " + expectedDescription);

		driver.findElement(By.id("description")).sendKeys(expectedDescription);
		driver.findElement(By.id("amount")).sendKeys("100,000");
		driver.findElement(By.id("submit")).click();

		//waitForElement(By.linkText(expectedDescription), driver, 30);
		
		//Thread.sleep(3000);
		List<WebElement> descriptionElements = driver.findElements(By.xpath("//table/descendant::a"));
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		  js.executeScript("scroll(0,600)");
		
		//Assert.assertTrue(isDescriptionMatch(expectedDescription, descriptionElements), "Deposit unsucessfull!");
		
		Thread.sleep(5000);
	}
	private boolean isDescriptionMatch(String expectedDescription, List<WebElement> descriptionElements) {
		
		for(int i=0; i < descriptionElements.size(); i++) {
			if(expectedDescription.equalsIgnoreCase(descriptionElements.get(i).getText())) {
				return true;
			}
		}
		return false;
	}

	private void waitForElement(By locator, WebDriver driver, int time) {
		new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

	}

	@AfterMethod
	public void closeEverything() {
		driver.close();
		driver.quit();
	}
}
