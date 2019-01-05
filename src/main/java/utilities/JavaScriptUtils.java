package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import seleniumDriver.CreateDriver;

public class JavaScriptUtils {

    public  JavaScriptUtils(){

    }

    public  static void  execute(String command){
        WebDriver driver = CreateDriver.getInstance().getDriver();
        JavascriptExecutor js =(JavascriptExecutor)driver;
        js.executeScript(command);
    }

    public  static  void execute(String command, WebElement element){
        WebDriver driver = CreateDriver.getInstance().getDriver();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript(command, element);
    }

    public  static void  click(WebElement element){
        WebDriver driver = CreateDriver.getInstance().getDriver();
        JavascriptExecutor js= (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();",element);
    }

    public  static void  click(By by){
        WebDriver driver = CreateDriver.getInstance().getDriver();
        WebElement element= driver.findElement(by);
        JavascriptExecutor js= (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();",element);

    }

    public  static void sendKeys(String keys, WebElement element){
        WebDriver driver = CreateDriver.getInstance().getDriver();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].value ='"+keys +"';",element);
    }

    public static boolean isPageReady(WebDriver driver){
        JavascriptExecutor js =(JavascriptExecutor)driver;
        return  (Boolean)js.executeScript("return document.readyState").equals("complete");

    }

    public static boolean isAjaxReady(WebDriver driver){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        return (Boolean)js.executeScript("return jQuery.active == 0");
    }
}
