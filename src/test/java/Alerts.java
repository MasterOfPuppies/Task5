import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class Alerts {

    private WebDriver alerts;
    private Alert myAlert;
    String string;

    @BeforeClass
    public void setUp(){
        alerts = new FirefoxDriver();
        alerts.get("https://the-internet.herokuapp.com/javascript_alerts");

    }

    @AfterClass
    public void tearDown(){
        alerts.close();

    }

    @Test
    public void jSAlert() {
        WebElement alertButtonFirst = alerts.findElement(By.xpath("//button[@onclick='jsAlert()']"));
        alertButtonFirst.click();

        myAlert = alerts.switchTo().alert();
        string = myAlert.getText();
        myAlert.accept();
        Assert.assertEquals(string, "I am a JS Alert");

    }

    @Test
    public void jSConfirm() {
        WebElement alertButtonSecond = alerts.findElement(By.xpath("//button[@onclick='jsConfirm()']"));
        alertButtonSecond.click();
        myAlert = alerts.switchTo().alert();
        string = myAlert.getText();
        myAlert.accept();
        Assert.assertEquals(string, "I am a JS Confirm");

    }

    @Test
    public void jSPrompt() {
        WebElement alertButtonThird = alerts.findElement(By.xpath("//button[@onclick='jsPrompt()']"));
        alertButtonThird.click();
        myAlert = alerts.switchTo().alert();
        string = myAlert.getText();
        myAlert.sendKeys("Boom");
        myAlert.accept();
        Assert.assertEquals(string, "I am a JS prompt");

    }
}
