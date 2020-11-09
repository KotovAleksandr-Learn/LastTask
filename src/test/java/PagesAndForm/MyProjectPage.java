package PagesAndForm;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MyProjectPage extends Form {
    public MyProjectPage(String name) {
        super(By.xpath("//li[(text())='MyProject']"), name);
    }

    public TestPage testPage;

    private static String testNameLinkLocator="//a[(text())='Check authorization form with correct login/password']";

    public boolean isNewTestExist(){
        ILink testNameLink=getElementFactory().getLink(By.xpath(testNameLinkLocator),"test name link");
        return testNameLink.state().isExist();
    }

    public void goToTestPage(){
        ILink testNameLink=getElementFactory().getLink(By.xpath(testNameLinkLocator),"test name link");
        testNameLink.click();
    }




}
