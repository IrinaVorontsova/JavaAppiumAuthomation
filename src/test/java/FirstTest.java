import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;
import java.util.Locale;


public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidEmulator");
        capabilities.setCapability("platformVersion", "11.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "F:/Irina/IdeaProjects/JavaAppiumAuthomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testAssertElementHasText(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find skip button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search input",
                5
        );
        String text = "Search Wikipedia";
        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                text,
                "Cannot find text of element"
        );
    }

    @Test
    public void testFewArticlesAndCancel(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find skip button",
                5
        );
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search input",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Quality Assurance",
                "Cannot find search line",
                10);

        waitForElementsPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Search didn't wokr",
                15
        );

        List<WebElement> webElements = waitForElementsPresent(
                By.xpath(String.format("//*[contains(@text,'uality')]")),
                "Cannot find search articles",
                15
        );

        int size_list = webElements.size();

        Assert.assertTrue("\n" +
                "Number of articles less than two", size_list > 2);

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search line",
                5
        );
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find skip button",
                5
        );
    }

    @Test
    public void testAssertTextInSearchResults(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find skip button",
                5
        );
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search input",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "JAVA",
                "Cannot find search line",
                10);

        waitForElementsPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Search didn't work",
                15
        );

        List<WebElement> webElements = waitForElementsPresent(
                By.xpath(("//android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.FrameLayout/androidx.recyclerview.widget.RecyclerView//android.widget.TextView[1]")),
                "Cannot find search articles",
                15
        );

        for (WebElement element : webElements){
            Assert.assertTrue(element.getText().toLowerCase(Locale.ROOT).contains("java"));
        }

    }


    @Test
    public void firstTest(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find skip button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search input",
                5
        );
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search line",
                10);
     }


    @Test
    public void testCancelSearch(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find skip button",
                5
        );
        waitForElementAndClick(By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search input",
                5
        );
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search line",
                10);
        waitForElementAndClear(
            By.id("org.wikipedia:id/search_src_text"),
                    "Cannot find search line",
                    5
        );
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find skip button",
                5
        );
    }

    @Test
    public void testCompareArticleTitle(){
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find skip button",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search input",
                5
        );
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search line",
                10);

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Java (programming language)')]"),
                "Cann't find 'Search Wikipedia' input",
                5
        );

        //driver.navigate().refresh();

        WebElement title_element = waitForElementPresent(
                By.xpath("/hierarchy//android.widget.FrameLayout[1]//android.webkit.WebView/android.view.View/android.view.View[1]/android.widget.TextView[1]"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getText();

        Assert.assertEquals("We see unexpected title", article_title, "Java (programming language)");

    }

    private WebElement waitForElementPresent(By by, String error_message, long timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by, error_message, 5);
    }

    private List<WebElement> waitForElementsPresent(By by, String error_message, long timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeOutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeOutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");

        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeOutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.clear();
        return element;
    }

    private boolean assertElementHasText(By by, String element_text, String error_message){
        WebElement element = waitForElementPresent(by, error_message);
        return element.getText().equals(element_text);
    }
}
