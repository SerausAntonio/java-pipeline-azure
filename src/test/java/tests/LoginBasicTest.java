package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginBasicTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp(){

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @DataProvider(name = "userAccounts")
    public Object[][] userAccounts() {
        return new Object[][]{
                {"standard_user"},
             //   {"locked_out_user"},
                {"problem_user"},
                {"performance_glitch_user"},
                {"error_user"},
                {"visual_user"}
        };
    }
    @Test(dataProvider = "userAccounts")

    public void loginTest(String username){
          driver.get("https://www.saucedemo.com/");
          Assert.assertTrue(driver.getTitle().equals("Swag Labs"));
          WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
          wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@class='login_logo']"))));

          driver.findElement(By.id("user-name")).sendKeys(username);
          driver.findElement(By.id("password")).sendKeys("secret_sauce");
          driver.findElement(By.id("login-button")).click();
          wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@class='title']"))));
          String page_title = driver.findElement(By.xpath("//*[@class='title']")).getText();
          Assert.assertEquals(page_title, "Products");
    }
}
