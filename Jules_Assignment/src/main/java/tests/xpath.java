package tests;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class xpath {

	ChromeDriver driver;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException {

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get("https://selectorshub.com/iframe-in-shadow-dom/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//wait till shodow-dom element get visibile in the html
		Thread.sleep(10000);

		JavascriptExecutor js=(JavascriptExecutor) driver;
		
		String str = "return document.querySelector('#userName').shadowRoot.querySelector('#app2').shadowRoot.querySelector('input')";
		WebElement shadowDom = (WebElement) js.executeScript(str);
		shadowDom.sendKeys("darshan Test");

		driver.quit();

	}
}
