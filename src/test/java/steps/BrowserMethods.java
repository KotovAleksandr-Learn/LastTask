package steps;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import org.openqa.selenium.Cookie;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class BrowserMethods {

    public static void addCookie(String token) {
        Browser browser = AqualityServices.getBrowser();
        Cookie cookie = new Cookie("token",token);
        browser.getDriver().manage().addCookie(cookie);
    }

    public static void saveImgFromBrowserPage(String filePath) {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(1000);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_S);
            StringSelection stringSelection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null);
            robot.setAutoDelay(1000);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            robot.setAutoDelay(1000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.setAutoDelay(2000);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
