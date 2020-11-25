import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.RestAssured;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pagesform.NewProjectForm;
import pagesform.ProjectPage;
import pagesform.ProjectsPage;
import pagesform.TestPage;
import steps.BrowserMethods;

import static apiutils.ApiApplicationRequests.getTestsListJsonFormatRequest;
import static apiutils.ApiApplicationRequests.getTokenRequest;
import static apiutils.testrail.testrailapplicationrequest.TestRailRequest.addTestWithScreenInTestRail;
import static databaseutils.DataBaseRequests.addNewTestWithLogAndAttach;
import static databaseutils.DataBaseUtils.logString;
import static steps.BrowserMethods.saveImgFromBrowserPage;
import static steps.Utils.*;

public class UnionReportingTest {

    public ISettingsFile configFile;
    public  ISettingsFile testDataFile;
    public Browser browser;
    public String url;
    public String projectDir;

    @BeforeTest
    public void setupConfig() {
        configFile = new JsonSettingsFile("config.json");
        testDataFile = new JsonSettingsFile("testData.json");
        url = configFile.getValue("/url").toString();
        projectDir = System.getProperty("user.dir");
        browser = AqualityServices.getBrowser();
        browser.maximize();
    }

    @Test
    public void unionReportTest() {
        RestAssured.baseURI = configFile.getValue("/baseApiUrl").toString();
        String token = getTokenRequest(configFile.getValue("/numVariant").toString());
        Assert.assertNotNull(token,"Token isn't generated!");
        browser.goTo(url);
        browser.waitForPageToLoad();
        BrowserMethods.addCookie(token);
        ProjectsPage projectsPage = new ProjectsPage();
        Assert.assertTrue(projectsPage.state().isDisplayed(),"Projects page isn't open");
        browser.refresh();
        browser.waitForPageToLoad();
        Assert.assertEquals(projectsPage.getFooterVariantNumberLocator(),testDataFile.getValue("/expectedFooterVariantNumber").toString(),
                "The variant don't match");
        ProjectPage pageNexageProject = new ProjectPage("Nexage","Nexage Project");
        projectsPage.goToProjectPage("Nexage",pageNexageProject);
        Assert.assertTrue(pageNexageProject.waitForDisplayedPage(),"Nexage Page isn't displayed");
        JSONArray webJsonArrayTestData = pageNexageProject.getTestInfo();
        Assert.assertTrue(checkSortingDateJson(webJsonArrayTestData),"Tests are not sorted in descending order of dates");
        JSONArray apiJsonArrayTestData = getTestsListJsonFormatRequest(configFile.getValue("/nexageProjectId").toString());
        Assert.assertTrue(compareDataTest(webJsonArrayTestData,apiJsonArrayTestData),"Test data don't match");
        pageNexageProject.backToProjectsPage(projectsPage);
        Assert.assertTrue(projectsPage.state().isDisplayed(),"ProjectsPage isn't displayed");
        projectsPage.newProjectForm = new NewProjectForm();
        projectsPage.clickAddNewProjectToListBtn(projectsPage.newProjectForm);
        Assert.assertTrue(projectsPage.newProjectForm.state().isDisplayed(),"New project form isn't displayed");
        projectsPage.newProjectForm.setProjectName(testDataFile.getValue("/newProjectName").toString());
        projectsPage.newProjectForm.saveNewProject();
        Assert.assertTrue(projectsPage.newProjectForm.checkSuccessSave(testDataFile.getValue("/newProjectName").toString()),
                "the project was not saved");
        projectsPage.newProjectForm.closeNewProjectForm(configFile.getValue("/closeNewProjectFormScriptFilePath").toString());
        Assert.assertFalse(projectsPage.newProjectForm.state().isDisplayed(),"Add new project form isn't closed");
        browser.refresh();
        browser.waitForPageToLoad();
        Assert.assertTrue(projectsPage.checkNewProjectAtList(testDataFile.getValue("/newProjectName").toString()),
                testDataFile.getValue("/newProjectName") + "Test not added");
        ProjectPage myProjectPage = new ProjectPage(testDataFile.getValue("/newProjectName").toString(),
                testDataFile.getValue("/newProjectName").toString() + "Page");
        projectsPage.goToProjectPage(testDataFile.getValue("/newProjectName").toString(),myProjectPage);
        addNewTestWithLogAndAttach(configFile,testDataFile,projectDir,browser.getScreenshot());
        Assert.assertTrue(myProjectPage.waitForDisplayedPage(),testDataFile.getValue("/newProjectName").toString() + " isn't displayed");
        Assert.assertTrue(myProjectPage.isNewTestExist(testDataFile.getValue("/testName").toString()),"new test ins't exist");
        myProjectPage.testPage = new TestPage(testDataFile.getValue("/newProjectName").toString() + " Test Page");
        myProjectPage.goToTestPage(testDataFile.getValue("/testName").toString(),myProjectPage.testPage);
        Assert.assertTrue(myProjectPage.testPage.state().isDisplayed(), "Test page isn't displayed");
        Assert.assertEquals(myProjectPage.testPage.getTestData("projectName"),testDataFile.getValue("/newProjectName"),
                "Projects names don't match");
        Assert.assertEquals(myProjectPage.testPage.getTestData("testName"),testDataFile.getValue("/testName"),
                "Tests names don't match");
        Assert.assertEquals(myProjectPage.testPage.getTestData("testMethodName"),testDataFile.getValue("/testMethodName"),
                "Tests methods names don't match");
        Assert.assertEquals(myProjectPage.testPage.getTestData("status"),testDataFile.getValue("/statusTest"),"Test status don't match");
        Assert.assertEquals(myProjectPage.testPage.getTestData("startTime"),"Start time: " + testDataFile.getValue("/startTime"),
                "Start time test don't match");
        Assert.assertEquals(myProjectPage.testPage.getTestData("endTime"),"End time: " + testDataFile.getValue("/endTime"),
                "End time test don't match");
        Assert.assertEquals(myProjectPage.testPage.getTestData("environment"),testDataFile.getValue("/environment"),
                "Environment don't match");
        Assert.assertEquals(myProjectPage.testPage.getTestData("browser"),testDataFile.getValue("/browser"),
                "Tests browsers don't match");
        Assert.assertEquals(myProjectPage.testPage.getTestData("attachmentType"),testDataFile.getValue("/attachmentType"),
                "Attachment type don't match");
        Assert.assertEquals(myProjectPage.testPage.getTestData("logInfo"),logString,"log data don't match");
        String screenHref = myProjectPage.testPage.downloadImg();
        browser.goTo(screenHref);
        browser.waitForPageToLoad();
        saveImgFromBrowserPage(projectDir + testDataFile.getValue("/imgsDirectory").toString()
                + testDataFile.getValue("/secondImageName").toString());
        Assert.assertTrue(compareFiles(projectDir + testDataFile.getValue("/imgsDirectory").toString()
                        + testDataFile.getValue("/secondImageName").toString(), projectDir + testDataFile.getValue("/imgsDirectory").toString()
                        + testDataFile.getValue("/firstImageName").toString()),"Images don't match");
    }

    @AfterMethod
    public void sendResultTestRail(ITestResult result) {
        addTestWithScreenInTestRail(testDataFile,result.getStatus(),browser.getScreenshot(),projectDir);
    }

    @AfterTest
    public void quiteBrowser() {
        browser.quit();
    }
}
