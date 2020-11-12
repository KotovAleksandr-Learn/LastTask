package PagesForms;

import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;

import java.util.List;

public class ProjectPage extends Form {
    public ProjectPage(String projectName,String name) {
        super(By.xpath("//li[(text())='"+projectName+"']"), name);
    }


    public TestPage testPage;


    private String backToProjectsPageLinkLocator="//a[text()='Home']";
    private String testNameLinkLocator="//a[(text())='";
    private String stringInfoTest="//table[@class='table']//tr//td";


    public void backToProjectsPage(){
        ILink backToProjectsPageLink=getElementFactory().getLink(By.xpath(backToProjectsPageLinkLocator),"backToProjectsPage");
        backToProjectsPageLink.click();

    }


    public boolean isNewTestExist(String testName){
        ILink testNameLink=getElementFactory().getLink(By.xpath(testNameLinkLocator+testName+"']"),"test name link");
        return testNameLink.state().isExist();
    }

    public void goToTestPage(String testName){
        ILink testNameLink=getElementFactory().getLink(By.xpath(testNameLinkLocator+testName+"']"),"test name link");
        testNameLink.click();
    }





    public JSONArray getTestInfo(){
        List<ITextBox> testInfo=getElementFactory().findElements(By.xpath(stringInfoTest),ElementType.TEXTBOX);
        JSONArray jsonArray=new JSONArray();
        int j=0;
        for(int i=0;i<testInfo.size();i+=6){
            JSONObject jsonObject=new JSONObject();

            jsonObject.put("name",testInfo.get(i).getText());
            jsonObject.put("method",testInfo.get(i+1).getText());
            jsonObject.put("status",testInfo.get(i+2).getText());
            if(!testInfo.get(i+3).getText().isEmpty())jsonObject.put("startTime",testInfo.get(i+3).getText());
            if(!testInfo.get(i+4).getText().isEmpty())jsonObject.put("endTime",testInfo.get(i+4).getText());
            jsonObject.put("duration",testInfo.get(i+5).getText());
            i++;
            jsonArray.put(j,jsonObject);
            j++;
        }
        return jsonArray;

    }




}
