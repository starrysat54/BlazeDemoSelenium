package Demoblaze;

import java.time.Duration;
import java.util.List;
import org.testng.Assert;
import java.util.Random;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import java.lang.Thread;


public class Demoblaze extends RandomNames {
  WebDriver driver = new ChromeDriver();
  Assertion Assertion = new Assertion();

  @BeforeTest
   public void MyBeforeTest() {
     driver.get("https://www.demoblaze.com/index.html");
     driver.manage().window().maximize();	
   }

  @Test(priority = 1, description = "Register")
  public void Register() throws InterruptedException {
    driver.findElement(By.id("signin2")).click();
    Thread.sleep(3000);
    driver.findElement(By.id("sign-username")).sendKeys(names[randNames]);
    driver.findElement(By.id("sign-password")).sendKeys("123");
    driver.findElement(By.cssSelector("button[onclick='register()']")).click();
    Thread.sleep(2000);		
    driver.switchTo().alert().accept();
    driver.findElement(By.xpath("//div[@id='signInModal']//button[@type='button'][normalize-space()='Close']")).click();
  }

  @Test(priority = 2, description = "Login")
  public void Login() throws InterruptedException {
    driver.findElement(By.xpath("//a[@id='login2']")).click();
    Thread.sleep(2000);	
    driver.findElement(By.xpath("//input[@id='loginusername']")).sendKeys(names[randNames]);
    driver.findElement(By.cssSelector("#loginpassword")).sendKeys("123");
    driver.findElement(By.cssSelector("button[onclick='logIn()']")).click();
    Thread.sleep(2000);	
  }

 @Test(priority = 3, description ="Check the listed Categories has Items")
  public void HasItems() throws InterruptedException {
    driver.findElement(By.linkText("Laptops")).click();
    List<WebElement> itemElements = driver.findElements(By.className("col-lg-4"));
    Assertion.assertTrue(itemElements.size() > 0 ,"Items were found");
  }

 @Test(priority = 4, description = "Add random item to the cart")
 public void AddRandToCart() throws InterruptedException {
     // Wait until the product elements are visible
     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
     List<WebElement> itemElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".col-lg-4.col-md-6.mb-4")));
     
     int itemsnum = itemElements.size();
     System.out.println("Number of items found: " + itemsnum);

     if (itemsnum > 0) {
         Random random = new Random();
         int randomProduct = random.nextInt(itemsnum);
         
         // Click on the randomly selected product
         itemElements.get(randomProduct).click();
         Thread.sleep(2000); // Or wait for the next page to load if needed
         
         // Click on "Add to cart"
         WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Add to cart']")));
         addToCartButton.click();

         // Wait for the alert and verify its text
         Alert alert = wait.until(ExpectedConditions.alertIsPresent());
         String alertText = alert.getText();
         System.out.println("Alert Text: " + alertText);
         
         Assert.assertEquals(alertText, "Product added.", "Alert text does not match expected value.");
         alert.accept();
         
         // Optional: Wait for the alert to be dismissed
         Thread.sleep(2000);
     } else {
         Assert.fail("No items found on the page to add to the cart.");
     }
 }


 @Test(priority = 5, description ="Remove item from cart")
  public void RemoveItem() throws InterruptedException {
    driver.findElement(By.id("cartur")).click();
    Thread.sleep(2000);	
    driver.findElement(By.xpath("(//a[@href='#'][normalize-space()='Delete'])[1]")).click();
    Thread.sleep(2000);	
  }

  @Test(priority = 6, description ="Complete successful checkout with random item")
  public void CompleteCheckout() throws InterruptedException {
    Thread.sleep(2000);	
    driver.findElement(By.xpath("(//button[normalize-space()='Place Order'])[1]")).click();
    Thread.sleep(2000);	
    driver.findElement(By.id("name")).sendKeys(names[randNames]);
    driver.findElement(By.id("country")).sendKeys("Jordan");
    driver.findElement(By.id("city")).sendKeys("Amman");
    driver.findElement(By.id("card")).sendKeys("123-123-123");
    driver.findElement(By.id("month")).sendKeys("April");
    driver.findElement(By.id("year")).sendKeys("2023");
    driver.findElement(By.xpath("//button[normalize-space()='Purchase']")).click();
    String Actual= driver.findElement(By.xpath("//h2[normalize-space()='Thank you for your purchase!']")).getText();
    Assertion.assertEquals(Actual, "Thank you for your purchase!", "Purchase was done");
  }

  @AfterTest
  public void AfterTest() throws InterruptedException {
    Thread.sleep(2000);	
    driver.close();
  }
  
}
