package pagesform;

import aquality.selenium.elements.ElementType;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;

import java.util.List;

public class ProjectPage extends Form {
    private ILink backToProjectsPageLink = getElementFactory().getLink(By.xpath("//a[text()='Home']"),"backToProjectsPage");
    private String testNameLinkLocator = "//a[(text())='";
    private String testInfoTxtBoxLocator = "//table[@class='table']//tr//td";
    private ITextBox uniqPageElement;
    public TestPage testPage;

    public ProjectPage(String projectName, String name) {
        super(By.xpath("//*[contains(@class,'nav')]"),name);
        uniqPageElement = getElementFactory().getTextBox(By.xpath("//li[(text())='" + projectName + "']"),"uniqPageLocator");
    }

    public JSONArray getTestInfo() {
        List<ITextBox> testInfo = getElementFactory().findElements(By.xpath(testInfoTxtBoxLocator), ElementType.TEXTBOX);
        JSONArray jsonArrayTestInfo = new JSONArray();
        int j = 0;
        for (int i = 0;i < testInfo.size();i += 6) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",testInfo.get(i).getText());
            jsonObject.put("method",testInfo.get(i + 1).getText());
            jsonObject.put("status",testInfo.get(i + 2).getText());
            if (!testInfo.get(i + 3).getText().isEmpty()) {
                jsonObject.put("startTime",testInfo.get(i + 3).getText());
            }
            if (!testInfo.get(i + 4).getText().isEmpty()) {
                jsonObject.put("endTime",testInfo.get(i + 4).getText());
            }
            jsonObject.put("duration",testInfo.get(i + 5).getText());
            i++;
            jsonArrayTestInfo.put(j,jsonObject);
            j++;
        }
        return jsonArrayTestInfo;
    }

    public void backToProjectsPage(Form projectPage) {
        backToProjectsPageLink.click();
        projectPage.state().waitForDisplayed();
    }

    public boolean isNewTestExist(String testName) {
        ILink testNameLink = getElementFactory().getLink(By.xpath(testNameLinkLocator + testName + "']"),"test name link");
        return testNameLink.state().isExist();
    }

    public void goToTestPage(String testName, Form testPage) {
        ILink testNameLink = getElementFactory().getLink(By.xpath(testNameLinkLocator + testName + "']"),"test name link");
        testNameLink.click();
        testPage.state().waitForDisplayed();
    }

    public boolean waitForDisplayedPage() {
        return uniqPageElement.state().waitForDisplayed();
    }
}
