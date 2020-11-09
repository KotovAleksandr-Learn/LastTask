package PagesForms;

import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class ProjectPage extends Form {
    public ProjectPage(String projectName,String name) {
        super(By.xpath("//li[(text())='"+projectName+"']"), name);
        //super(By.xpath("//div[contains(@class,'panel-heading')and contains(text(),'Total tests progress')]"),name);
    }


    public TestPage testPage;


    private String backToProjectsPageLinkLocator="//a[text()='Home']";
    private String tableNameTestLocator="//table[@class='table']//tr//td[1]";
    private String tableStartTimeTestLocator="//table[@class='table']//tr//td[4]";
    //Вот этот локатор мне не нравится
    private String testNameLinkLocator="//a[(text())='Check authorization form with correct login/password']";


    public void backToProjectsPage(){
        ILink backToProjectsPageLink=getElementFactory().getLink(By.xpath(backToProjectsPageLinkLocator),"backToProjectsPage");
        backToProjectsPageLink.click();

    }

    public List<String> getTestNameFromTable(){
        List<ITextBox> tableElements=getElementFactory().findElements(By.xpath(tableNameTestLocator), ElementType.TEXTBOX);
        List<String> testNameList = new ArrayList<String>();
        for(int i=0;i<tableElements.size();i++)
            testNameList.add(i,tableElements.get(i).getText());
        return testNameList;
    }

    public List<String>  getTestStartTimeFromTable(){
        List<ITextBox> tableElements=getElementFactory().findElements(By.xpath(tableStartTimeTestLocator),ElementType.TEXTBOX);
        List<String> testDateList = new ArrayList<String>();
        for(int i=0;i<tableElements.size();i++)
            testDateList.add(i,tableElements.get(i).getText());
        return testDateList;
    }


    public boolean isNewTestExist(){
        ILink testNameLink=getElementFactory().getLink(By.xpath(testNameLinkLocator),"test name link");
        return testNameLink.state().isExist();
    }

    public void goToTestPage(){
        ILink testNameLink=getElementFactory().getLink(By.xpath(testNameLinkLocator),"test name link");
        testNameLink.click();
    }









}
