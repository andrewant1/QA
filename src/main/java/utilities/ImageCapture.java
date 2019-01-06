package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import seleniumDriver.CreateDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageCapture {

    public enum  RESULT {Matched, SizeMismatch, PixelMismatch}

    public ImageCapture() throws  Exception{

    }

    public static String screenShot(ITestResult result) throws Exception{
        DateFormat stamp = new SimpleDateFormat("MM.dd.yyyy.HH.mm.ss");
        Date date = new Date();
        ITestNGMethod method = result.getMethod();
        String testName = method.getMethodName();
        return captureScreen(testName + "_" + stamp.format(date)+ ".png");

    }

    public static String captureScreen(String filename) throws Exception{
        String bitmapPath ="myPath";
        WebDriver driver = CreateDriver.getInstance().getDriver();
        File screen = null;

        if(Global_VARS.DEF_ENVIRONMENT.equalsIgnoreCase("remote")){
            screen =((TakesScreenshot)new Augmenter().augment(driver)).getScreenshotAs(OutputType.FILE);
        }

        else {
            screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        }
        FileUtils.copyFile(screen, new File(bitmapPath + filename));

        return filename;

    }

    public static File imageSnapshot(WebElement element) throws Exception{

        WrapsDriver driver = (WrapsDriver) element;
        File screen = null;
        screen = ((TakesScreenshot)driver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(screen);

        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();

        Rectangle rect = new Rectangle(width, height);
        Point p = element.getLocation();
        BufferedImage dest = img.getSubimage(p.getX(),p.getX(),width,height);
        ImageIO.write(dest,"png",screen);
        return screen;

    }

    public static void captureImage(String image) throws Exception{
        WebDriver driver = CreateDriver.getInstance().getCurrentDriver();
        WebElement getImage = driver.findElement(By.cssSelector("img[src*='" + image + "']"));
        image = image.replace(".","_"+Global_VARS.DEF_BROWSER +".");
        FileUtils.copyFile(imageSnapshot(getImage), new File(Global_VARS.BITMAP_OUTPUT_PATH + image));

    }


    public  static RESULT compareImage (String expFile, String actFile) throws Exception{

        RESULT compareResult = null;
        Image baseImage = Toolkit.getDefaultToolkit().getImage(expFile);
        Image actImage = Toolkit.getDefaultToolkit().getImage(actFile);

        PixelGrabber baseImageGrab = new PixelGrabber(baseImage,0,0,-1,-1,false);
        PixelGrabber actImageGrab = new PixelGrabber(actImage,0,0,-1,-1,false);

        int[] baseImageData = null;
        int[] actImageData = null;

        if ( baseImageGrab.grabPixels() ) {
            int width = baseImageGrab.getWidth();
            int height = baseImageGrab.getHeight();
            baseImageData = new int[width * height];
            baseImageData = (int[])baseImageGrab.getPixels();
        }
        if ( actImageGrab.grabPixels() ) {
            int width = actImageGrab.getWidth();
            int height = actImageGrab.getHeight();
            actImageData = new int[width * height];
            actImageData = (int[])actImageGrab.getPixels();
        }

        if ( (baseImageGrab.getHeight() != actImageGrab.getHeight()) ||
                (baseImageGrab.getWidth() != actImageGrab.getWidth()) ) {
            compareResult = RESULT.SizeMismatch;
        }
        else if ( java.util.Arrays.equals(baseImageData,actImageData) ) {
            compareResult = RESULT.Matched;
        }else {
            compareResult = RESULT.PixelMismatch;
        }
        return compareResult;

    }



}
