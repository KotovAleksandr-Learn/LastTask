import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import org.openqa.selenium.Cookie;

public class BrowserMethods {


    public static void addCookie(String token){
        Browser browser= AqualityServices.getBrowser();
        Cookie cookie=new Cookie("token",token);
        browser.getDriver().manage().addCookie(cookie);
    }

}
