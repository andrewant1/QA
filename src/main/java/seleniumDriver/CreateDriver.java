package seleniumDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.URL;
import java.sql.Time;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CreateDriver {

    private static CreateDriver instance = null;
    private String browserHandle = null;
    private static  final int IMPLICITE_TIMEOUT = 0;
    private  ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    private ThreadLocal<AppiumDriver<MobileElement>> mobileDriver = new ThreadLocal<AppiumDriver<MobileElement>>();
    private ThreadLocal<String> sessionID = new ThreadLocal<String>();
    private ThreadLocal<String> sessionBrowser = new ThreadLocal<String>();
    private ThreadLocal<String> sessionPlatform = new ThreadLocal<String>();
    private ThreadLocal<String> sessionVersion = new ThreadLocal<String>();
    private String getEnv = null;



    private  CreateDriver(){

    }

    public static CreateDriver getInstance(){
        if (instance == null) {
            instance = new CreateDriver();
        }
        return instance;
    }

    public final void setDriver(String browser, String environment, String platform, Map<String,Object>... optPrefernces)throws Exception{
        DesiredCapabilities caps = null;
        String localHub="http://127.0.0.1:4723/wd/hub/";
        String getPlatform = null;

        switch (browser){
            case "firefox":
                //System.setProperty("webdriver.gecko.driver","/home/andrew/IdeaProjects/ua.company/src/drivers/geckodriver");
                WebDriverManager.firefoxdriver().setup();
                caps = DesiredCapabilities.firefox();
               FirefoxOptions ffOpts = new FirefoxOptions();
                webDriver.set(new FirefoxDriver(ffOpts.merge(caps)));
                break;

            case "chrome":
                WebDriverManager.chromedriver().setup();
                caps = DesiredCapabilities.chrome();
                ChromeOptions chOpts = new ChromeOptions();
                webDriver.set(new ChromeDriver(chOpts.merge(caps)));
                break;

            case "internet explorer":
                WebDriverManager.iedriver().setup();
                caps = DesiredCapabilities.internetExplorer();
                InternetExplorerOptions ieOpts = new InternetExplorerOptions();
                webDriver.set(new InternetExplorerDriver(ieOpts.merge(caps)));
                break;

            case "safari":
                caps = DesiredCapabilities.safari();
                SafariOptions safOpts = new SafariOptions();
                webDriver.set(new SafariDriver(safOpts.merge(caps)));
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                caps = DesiredCapabilities.edge();
                EdgeOptions edgeOpts = new EdgeOptions();
                webDriver.set(new EdgeDriver(edgeOpts.merge(caps)));
                break;

            case "iphone":
            case "ipad":
                if(browser.equalsIgnoreCase("iphone")) {
                    caps = DesiredCapabilities.iphone();
                }
                 else {
                     caps = DesiredCapabilities.ipad();
                }
                 mobileDriver.set(new IOSDriver<MobileElement>(new URL(localHub),caps));
                 break;

            case "android":
                caps=DesiredCapabilities.android();
                mobileDriver.set(new AndroidDriver<MobileElement>(new URL(localHub),caps));
                break;

        }

    }
    public void setDriver (WebDriver driver) {
        webDriver.set(driver);
        sessionID.set(((RemoteWebDriver)webDriver.get()).getSessionId().toString());
        sessionBrowser.set(((RemoteWebDriver) webDriver.get()).getCapabilities().getBrowserName());
        sessionPlatform.set(((RemoteWebDriver) webDriver.get()).getCapabilities().getPlatform().toString());

    }

    public  void  setDriver(AppiumDriver<MobileElement> driver){
        mobileDriver.set(driver);
        sessionID.set(mobileDriver.get().getSessionId().toString());
        sessionBrowser.set(mobileDriver.get().getCapabilities().getBrowserName());
        sessionPlatform.set(mobileDriver.get().getCapabilities().getPlatform().toString());

    }

    public WebDriver getDriver(){
        return webDriver.get();
    }

    public  AppiumDriver<MobileElement> getDriver(boolean mobile){
        return  mobileDriver.get();
    }

    public  WebDriver getCurrentDriver(){
        if (getInstance().getSessionBrowser().contains("iphone")||
             getInstance().getSessionBrowser().contains("ipad") ||
              getInstance().getSessionBrowser().contains("android") )
        {
            return  getInstance().getDriver(true);
        }
        return  getInstance().getDriver();
    }

    public String getSessionBrowser(){
        return sessionBrowser.get();
    }

    public void waitDriver(int seconds){
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void refreshDriver(){
        getCurrentDriver().navigate().refresh();
    }

    public void  closeDriver() {
        try {
            getCurrentDriver().quit();
        } catch (Exception e) {
        }
    }


}
