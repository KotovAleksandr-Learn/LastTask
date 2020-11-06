import PagesAndForm.AddNewProjectForm;
import PagesAndForm.ListGroupForm;
import PagesAndForm.PageOfAProject;
import PagesAndForm.ProjectsPage;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import aquality.selenium.elements.actions.JsActions;
import io.restassured.RestAssured;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static Steps.Steps.compareTestsDataName;

public class UnionReportingTest {


    @Test
    public static void unionReportingTest() throws IOException {

        ISettingsFile configFile=new JsonSettingsFile("config.json");
        ISettingsFile testDataFile=new JsonSettingsFile("testData.json");
        //step1
        RestAssured.baseURI=configFile.getValue("/baseApiUrl").toString();
        String token=ApiUtils.getToken("1");
        Assert.assertNotNull(token,"Token isn't generated!");
        //step2
        Browser browser=AqualityServices.getBrowser();
        browser.maximize();
        String url=configFile.getValue("/url").toString();
        browser.goTo(url);
        browser.waitForPageToLoad();
        BrowserMethods.addCookie(token);

        ProjectsPage projectsPage=new ProjectsPage();
        Assert.assertTrue(projectsPage.state().isDisplayed(),"Projects page isn't open");

        browser.refresh();
        browser.waitForPageToLoad();
        Assert.assertEquals(projectsPage.getFooterVariantNumberLocator(),testDataFile.getValue("/expectedFooterVariantNumber").toString(),"The variant don't match");

        //step3
/*

        projectsPage.listGroupForm=new ListGroupForm();
        projectsPage.listGroupForm.goToProjectPage(projectsPage.linkToProjectNexagePageLocator);
        browser.waitForPageToLoad();


        PageOfAProject pageOfNexageProject=new PageOfAProject("NexageProject");
        Assert.assertTrue(pageOfNexageProject.state().isDisplayed(),"NexagePage isn't displayed");


        List<String> testsName=pageOfNexageProject.getTestNameFromTable();
        Assert.assertTrue(pageOfNexageProject.getTestStartTimeAndCheckSortTime(),"Tests are not sorted in descending order of dates");
        JSONArray jsonArray=ApiUtils.getTestsListJsonFormat(configFile.getValue("/nexageProjectId").toString());

        Assert.assertTrue(compareTestsDataName(jsonArray,testsName),"Tests names don't match");

        //step4

        pageOfNexageProject.backToProjectsPage();
        browser.waitForPageToLoad();
*/

        projectsPage.addNewProjectToList();
        browser.getDriver().switchTo().frame(0);

        projectsPage.newProjectForm=new AddNewProjectForm("new project");

        projectsPage.newProjectForm.setProjectName(testDataFile.getValue("/newProjectName").toString());
        projectsPage.newProjectForm.saveNewProject();

        Assert.assertTrue(projectsPage.newProjectForm.checkSuccessSave(testDataFile.getValue("/newProjectName").toString()),"the project was not saved");

        browser.getDriver().switchTo().defaultContent();
        browser.executeScript("$('#addProject').modal('hide');");

        projectsPage.newProjectForm.state().waitForNotDisplayed();
        Assert.assertFalse(projectsPage.newProjectForm.state().isDisplayed(),"Add new project isn'r closed");
        browser.refresh();

        Assert.assertTrue(projectsPage.checkNewProjectAtList(testDataFile.getValue("/newProjectName").toString()));



    }
    @AfterTest
    public void closeB(){
        AqualityServices.getBrowser().quit();
    }

}
