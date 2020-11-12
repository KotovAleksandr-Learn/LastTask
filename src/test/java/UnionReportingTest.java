import APIUtils.APIUtils;
import DataBaseUtils.DataBaseRequest;
import PagesForms.AddNewProjectForm;
import PagesForms.ProjectPage;
import PagesForms.ProjectsPage;
import PagesForms.TestPage;
import Steps.BrowserMethods;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.RestAssured;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.time.Duration;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import static Steps.BrowserMethods.saveImgFromBrowserPage;
import static Steps.Utils.*;

public class UnionReportingTest {



    @Test
    public static void unionReportingTest() throws SQLException, IOException, ClassNotFoundException, AWTException {

        ISettingsFile configFile=new JsonSettingsFile("config.json");
        ISettingsFile testDataFile=new JsonSettingsFile("testData.json");

        //step1
        RestAssured.baseURI=configFile.getValue("/baseApiUrl").toString();
        String token= APIUtils.getToken("1");
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
        projectsPage.goToProjectPage("Nexage");
        browser.waitForPageToLoad();

        ProjectPage pageNexageProject=new ProjectPage("Nexage","Nexage Project");
        pageNexageProject.state().waitForDisplayed(Duration.ofSeconds(Integer.valueOf(configFile.getValue("/durationOfSeconds").toString())));
        Assert.assertTrue(pageNexageProject.state().isDisplayed(),"NexagePage isn't displayed");

        JSONArray webJsonArray=pageNexageProject.getTestInfo();
        Assert.assertTrue(checkSortingDateJson(webJsonArray),"Tests are not sorted in descending order of dates");

        JSONArray apiJsonArray=APIUtils.getTestsListJsonFormat(configFile.getValue("/nexageProjectId").toString());
        Assert.assertTrue(compareDataTest(webJsonArray,apiJsonArray),"test data don't match");

        //step4
        pageNexageProject.backToProjectsPage();
        browser.waitForPageToLoad();
        /////
        projectsPage.clickAddNewProjectToListBtn();
        projectsPage.newProjectForm=new AddNewProjectForm();
        browser.getDriver().switchTo().frame(0);
        projectsPage.newProjectForm.setProjectName(testDataFile.getValue("/newProjectName").toString());
        projectsPage.newProjectForm.saveNewProject();

        Assert.assertTrue(projectsPage.newProjectForm.checkSuccessSave(testDataFile.getValue("/newProjectName").toString()),"the project was not saved");
        browser.getDriver().switchTo().defaultContent();
        browser.executeScript("$('#addProject').modal('hide');");
        projectsPage.newProjectForm.state().waitForNotDisplayed();
        Assert.assertFalse(projectsPage.newProjectForm.state().isDisplayed(),"Add new project isn'r closed");
        browser.refresh();
        Assert.assertTrue(projectsPage.checkNewProjectAtList(testDataFile.getValue("/newProjectName").toString()),testDataFile.getValue("/newProjectName")+"test not added");


        //step5

        projectsPage.goToProjectPage(testDataFile.getValue("/newProjectName").toString());
        browser.waitForPageToLoad();

        ProjectPage myProjectPage=new ProjectPage(testDataFile.getValue("/newProjectName").toString(),testDataFile.getValue("/newProjectName").toString()+"Page");

        DataBaseRequest.doSqlRequest(configFile,testDataFile,browser.getScreenshot());

        Assert.assertTrue(myProjectPage.state().waitForDisplayed(),"MyProject isn't displayed");
        Assert.assertTrue(myProjectPage.isNewTestExist(testDataFile.getValue("/testName").toString()),"new test ins't exist");

        //step6
        myProjectPage.goToTestPage(testDataFile.getValue("/testName").toString());
        myProjectPage.testPage=new TestPage(testDataFile.getValue("/newProjectName").toString()+"test Page");
        Assert.assertTrue(myProjectPage.testPage.state().isDisplayed());


        Assert.assertEquals(myProjectPage.testPage.getProjectName(),testDataFile.getValue("/newProjectName"),"Name prject don't match");
        Assert.assertEquals(myProjectPage.testPage.getTestName(),testDataFile.getValue("/testName"),"Test name don't match");
        Assert.assertEquals(myProjectPage.testPage.getTestMethodName(),testDataFile.getValue("/testMethodName"),"Test method name don't match");
        Assert.assertEquals(myProjectPage.testPage.getStatus(),testDataFile.getValue("/statusTest"),"Test status don't match");
        Assert.assertEquals(myProjectPage.testPage.getStartTime(),"Start time: "+testDataFile.getValue("/startTime"),"Start time test don't match");
        Assert.assertEquals(myProjectPage.testPage.getEndTime(),"End time: "+testDataFile.getValue("/endTime"),"End time test don't match");
        Assert.assertEquals(myProjectPage.testPage.getEnvironment(),testDataFile.getValue("/environment"),"Environment don't match");
        Assert.assertEquals(myProjectPage.testPage.getBrowser(),testDataFile.getValue("/browser"),"Test browser don't match");
        Assert.assertEquals(myProjectPage.testPage.getAttachmentType(),testDataFile.getValue("/attachmentType"),"attachment type don't match");
        Assert.assertEquals(myProjectPage.testPage.getLogInfo(),converteDataFileToProjectLogFormat(testDataFile.getValue("/logFile").toString()),"log data don't match");

        String screenHref=myProjectPage.testPage.downloadImg();
        browser.goTo(screenHref);
        browser.waitForPageToLoad();
        saveImgFromBrowserPage(testDataFile.getValue("/saveImgAbsPath").toString());
        Assert.assertTrue(compareFiles(testDataFile.getValue("/firstImgPath").toString(),testDataFile.getValue("/secondImgPath").toString()),"Images don't match");

    }


    @AfterTest
    public static void closeBrowser(){
        AqualityServices.getBrowser().quit();
    }


}
