import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Frames {

    private static final By FRAME = By.xpath("//iframe");
    private static final By TEXT = By.xpath("//html/body");
    private WebDriver iFrame = null;

    @BeforeMethod
    public void setUp(){
        iFrame = new FirefoxDriver();
        iFrame.get("https://the-internet.herokuapp.com/iframe");

    }

    @AfterMethod
    public void tearDown(){
        iFrame.close();

    }

    @Test
    public void testMethod() throws InterruptedException {

        WebElement frame = iFrame.findElement(FRAME);

        iFrame.switchTo().frame(frame);
        WebElement inputText = iFrame.findElement(TEXT);
        inputText.clear();
        inputText.sendKeys("Hello ");

        iFrame.switchTo().defaultContent();
        WebElement selectBoldFont = iFrame.findElement(By.xpath("//div[@id='mceu_3']/button"));
        selectBoldFont.click();

        iFrame.switchTo().frame(frame);
        inputText.sendKeys("World!");

        Assert.assertEquals(inputText.getText(), "Hello \uFEFFWorld!");

        Thread.sleep(3000);

    }
}
