package PagesAndForm;

import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PageOfAProject extends Form {
    public PageOfAProject(String name) {
        //локатор надо поменять
        super(By.xpath("//div[contains(@class,'panel-heading')and contains(text(),'Total tests progress')]"),name);
    }



    public String tableNameTestLocator="//table[@class='table']//tr//td[1]";
    public String tableStartTimeTestLocator="//table[@class='table']//tr//td[4]";
    public String backToProjectsPageLinkLocator="//a[text()='Home']";

    public List<String> getTestNameFromTable(){

        List<ITextBox> tableElements=getElementFactory().findElements(By.xpath(tableNameTestLocator),ElementType.TEXTBOX);
        List<String> testNameList = new ArrayList<String>();
        for(int i=0;i<tableElements.size();i++){
            testNameList.add(i,tableElements.get(i).getText());
        }
        return testNameList;
    }



    public boolean  getTestStartTimeAndCheckSortTime(){
        List<ITextBox> tableElements=getElementFactory().findElements(By.xpath(tableStartTimeTestLocator),ElementType.TEXTBOX);
        List<String> testDateList = new ArrayList<String>();
        for(int i=0;i<tableElements.size();i++){
            testDateList.add(i,tableElements.get(i).getText());
        }
        return checkSortDate(testDateList);
    }



    public void backToProjectsPage(){
        ILink backToProjectsPageLink=getElementFactory().getLink(By.xpath(backToProjectsPageLinkLocator),"backToProjectsPage");
        backToProjectsPageLink.click();

    }






    //наверно его лучше вынести в utils потом
    private boolean checkSortDate(List<String> testDate){

        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S");
        Date[]arrayDate=new Date[testDate.size()];

        for(int i=0;i<testDate.size();i++){
            arrayDate[i]=format.parseDateTime(testDate.get(i)).toDate();
            if(i>=1)
                if(!arrayDate[i-1].after(arrayDate[i])) return false;
        }
        return true;
    }










}
