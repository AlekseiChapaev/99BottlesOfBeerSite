import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestSite{

    private WebDriver driver;

    @BeforeMethod
    WebDriver beforeMethod() {
        System.setProperty("webdriver.chrome.driver", "C:/QA/4_stream/chromedriver.exe");
        return driver = new ChromeDriver();
    }

    @AfterMethod
    protected void afterMethod() {
        driver.quit();
    }

    private static final String URL = "http://www.99-bottles-of-beer.net/";
    private static final String URL_BROWSE_LANGUAGE_BUTTON = "//li/a[contains(text(), 'Browse Languages')]";

    @Test
    public void testCheckLanguage(){
        driver.get(URL);
        driver.findElement(By.xpath(URL_BROWSE_LANGUAGE_BUTTON)).click();
        driver.findElement(By.xpath("//a[@href = 'j.html']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//div[@id = 'main']/p[1]")).getText(),
                "All languages starting with the letter J are shown, sorted by Language.");
    }
}
