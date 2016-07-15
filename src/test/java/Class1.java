import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class Class1 {

    private static final By INPUT_USERNAME_CSS = By.cssSelector("#Username");
    private static final By INPUT_USERNAME_XPATH = By.xpath("//div[@id='Username']");
    private static final By INPUT_PASSWORD_CSS = By.cssSelector("#Password");
    private static final By INPUT_PASSWORD_XPATH = By.xpath("//div[@id='Password']");
    private static final By REMEMBERME_LABEL_CSS = By.cssSelector(".editor-chbox>span");
    private static final By REMEMBERME_LABEL_XPATH = By.xpath("//div[@class='remember-chBox']/span");
    private static final By REMEMBERME_INPUT_CSS = By.cssSelector(".remember-chBox>label>span");
    private static final By REMEMBERME_INPUT_XPATH = By.xpath("//div[@class='remember-chBox']/label/span");
    private static final By LOGIN_CSS = By.cssSelector("#SubmitButton");
    private static final By LOGIN_XPATH = By.xpath("//div[@id='SubmitButton']");
    private static final By SIGNOUT_CSS = By.cssSelector(".sign-out-span>a>ins");
    private static final By SIGNOUT_XPATH = By.xpath("//div[@class='menu-links']/span[@class='sign-out-span']/a/ins");
    private static final By ALL_LINKS_OFFICES_CSS = By.cssSelector("a[href*='#Office-Chapaeva']");
    private static final By ALL_LINKS_OFFICES_XPATH = By.xpath("//a[contains(text(),'Chapaeva')]");
    private static final By CHAPAEVA118_LINK_OFFICE_CSS = By.cssSelector("a[href='#Office-Chapaeva 118']");
    private static final By CHAPAEVA118_LINK_OFFICE_XPATH = By.cssSelector("//a[contains(@href,'#Office-Chapaeva 118')]");
    private static final By LUNCHVOTING_CSS = By.cssSelector(".lunchvoting");
    private static final By LUNCHVOTING_XPATH = By.xpath("//a[@href='https://lunchvoting.issoft.by/']");
    private static final By VACATION_TAB = By.xpath("//a[@id='vacationMenu']");
    private static final By TO_XPATH = By.xpath("//div[@id='thirdContainer']/span");
    private static final By TO_CSS = By.cssSelector("#thirdContainer>span");
    private static final By CC_XPATH = By.xpath("//div[@id='fourthContainer']/span");
    private static final By CC_CSS = By.cssSelector("#fourthContainer>span");
    private static final By COMPANY_TAB = By.cssSelector("#companyMenu");//li[contains(@class,'company-record')]
    private static final By COMPANIES_CSS = By.cssSelector(".company-record");
    private static final By COMPANIES_XPATH = By.xpath("//li[contains(@class,'company-record')]");
    private static final By CONTACTS_CSS = By.cssSelector(".tab-link-contact");
    private static final By CONTACTS_XPATH = By.xpath("//a[contains(@class,'tab-link-contact')]");
    private static final By USERNAMEERROR = By.xpath("//span[@title='Username is required']");
    private static final By PASSWORDERROR = By.xpath("//span[@title='Password is required']");
    private WebDriver driver = null;
    private WebDriverWait explicitWaiter;
    private WebDriverWait explicitWaiterExtended;

    private static List<String> getStringsFromFile(File file) throws IOException {
        List<String> info = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            String str = null;
            while((str = br.readLine()) != null) {
                info.add(str);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return info;

    }

    private static Object[][] getDataFromTextFile(File file, String divider) throws IOException {
        List<String> text = getStringsFromFile(file);
        int rowsCount = text.size();
        int columnsCount = text.get(0).split(divider).length;
        Object[][] objects = new Object[rowsCount][columnsCount];
        for (int i = 0; i < text.size(); i++) {
            objects[i] = text.get(i).split(divider);
        }
        return objects;

    }

    @DataProvider(name = "fileData")
    public static Object[][] testData() throws IOException {
        File file_txt = new File("file.txt");
        return getDataFromTextFile(file_txt, ",");

    }

    @BeforeMethod
    public void setUpMethod(){
        driver = new FirefoxDriver();
        driver.get("https://192.168.100.26/");
        explicitWaiter = new WebDriverWait(driver, 5);
        explicitWaiterExtended = new WebDriverWait(driver, 15, 2700);

    }

    @AfterMethod
    public void tearDownMethod(){
        driver.close();

    }

    @Test(dataProvider = "fileData")
    public void test1(String username, String password, String category) throws InterruptedException {

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        explicitWaiter.until(ExpectedConditions.titleIs("RMSys - Sign In"));

        WebElement inputUsername = driver.findElement(INPUT_USERNAME_CSS);
        WebElement inputPassword = driver.findElement(INPUT_PASSWORD_CSS);

        WebElement loginButton = driver.findElement(LOGIN_CSS);

        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        assertEquals(inputUsername.getAttribute("value"), username);
        loginButton.click();

        if(category.equals("1")){
            assertEquals((driver.findElement(USERNAMEERROR)).getAttribute("title"),"Username is required");
        } else if (category.equals("2")){
            assertEquals((driver.findElement(PASSWORDERROR)).getAttribute("title"),"Password is required");
        } else {

            explicitWaiter.until(ExpectedConditions.titleIs("RMSys - Home"));

            WebElement signoutButton = driver.findElement(SIGNOUT_CSS);

            explicitWaiter.until(ExpectedConditions.visibilityOf(signoutButton));

            WebElement officeMenu = driver.findElement(By.xpath("//a[@id='officeMenu']"));
            officeMenu.click();
            explicitWaiterExtended.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='search-container']/input"))));

            Thread.sleep(1000);

        }
    }
}
