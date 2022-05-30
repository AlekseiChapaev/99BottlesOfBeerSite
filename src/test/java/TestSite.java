import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

public class TestSite {

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

    public String setLinkName(String letter) {
        return "//a[@href = '" + letter.toLowerCase() + ".html']";
    }

    public void inputValueGuestBookPage(String field, String value){
        driver.findElement(By.xpath("//input[@name = '" + field.toLowerCase() + "']")).sendKeys(value);
    }

    private static final String URL = "http://www.99-bottles-of-beer.net/";
    private static final String URL_BROWSE_LANGUAGE_BUTTON = "//li/a[contains(text(), 'Browse Languages')]";

    @Test
    public void testCheckLanguage_TC_12_01() {
        driver.get(URL);
        driver.findElement(By.xpath(URL_BROWSE_LANGUAGE_BUTTON)).click();
        driver.findElement(By.xpath(setLinkName("J"))).click();

        Assert.assertEquals(driver.findElement(By.xpath("//div[@id = 'main']/p[1]")).getText(),
                "All languages starting with the letter J are shown, sorted by Language.");
    }

    @Test
    public void testCheckLanguage_TC_12_02() {
        driver.get(URL);
        driver.findElement(By.xpath(URL_BROWSE_LANGUAGE_BUTTON)).click();
        driver.findElement(By.xpath(setLinkName("M"))).click();
        List<WebElement> languages = driver.findElements(By.xpath("//table[@id = 'category']/tbody/tr"));
        String[] result = languages.get(languages.size() - 1).getText().split(" ");

        Assert.assertEquals(result[0], "MySQL");
    }

    @Test
    public void testCheckHeaders_TC_12_03() {
        driver.get(URL);
        driver.findElement(By.xpath(URL_BROWSE_LANGUAGE_BUTTON)).click();
        List<WebElement> headers = driver.findElements(By.cssSelector("#category tbody tr th"));
        String[] expectedResult = new String[]{"Language", "Author", "Date", "Comments", "Rate"};

        for (int i = 0; i < headers.size(); i++) {
            Assert.assertEquals(headers.get(i).getText(), expectedResult[i]);
        }
    }

    @Test
    public void testSolutionMathematica_TC_12_04() {
        driver.get(URL);
        driver.findElement(By.xpath(URL_BROWSE_LANGUAGE_BUTTON)).click();
        driver.findElement(By.xpath(setLinkName("M"))).click();
        driver.findElement(By.xpath("//a[. = 'Mathematica']")).click();

        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("Comments", "1");
        expectedResult.put("Date", "03/16/06");
        expectedResult.put("Author", "Brenton Bostick");

        List<WebElement> tDs = driver.findElements(By.xpath("//div[@id = 'main']/table/tbody/tr/td"));
        Map<String, String> actualResult = new LinkedHashMap<>();
        for (int i = 0; i < tDs.size() - 1; i += 2) {
            actualResult.put(tDs.get(i).getText(), tDs.get(i + 1).getText());
        }

        Assert.assertEquals(actualResult.get("Author:"), expectedResult.get("Author"));
        Assert.assertEquals(actualResult.get("Date:"), expectedResult.get("Date"));
        Assert.assertEquals(actualResult.get("Comments:"), expectedResult.get("Comments"));
    }

    @Test
    public void testCheckNumbersLanguages_TC_12_05() {
        driver.get(URL);
        driver.findElement(By.xpath(URL_BROWSE_LANGUAGE_BUTTON)).click();
        driver.findElement(By.xpath(setLinkName("0"))).click();
        List<WebElement> tDs = driver.findElements(By.cssSelector("#category tbody tr"));

        int count = 0;
        for(WebElement w : tDs){
            if(w.getText().charAt(0) >= '0' && w.getText().charAt(0) <= '9' ){
                count++;
            }
        }

        Assert.assertEquals(count, 10);
    }

    @Test
    public void testCheckGuestBook_TC_12_06() throws InterruptedException {
        driver.get("http://www.99-bottles-of-beer.net/signv2.html");
        inputValueGuestBookPage("Name", "Aleksei");
        inputValueGuestBookPage("Location", "Perm");
        inputValueGuestBookPage("email", "test@gmail.com");
        inputValueGuestBookPage("homepage", "test.com");
        inputValueGuestBookPage("captcha", String.valueOf((int)(Math.random() * 1000)));
        driver.findElement(By.xpath("//textarea[@name = 'comment']")).sendKeys("Hi all!");
        driver.findElement(By.xpath("//input[@type = 'submit']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector("#main p")).getText(),
                "Error: Error: Invalid security code.");
    }
}
