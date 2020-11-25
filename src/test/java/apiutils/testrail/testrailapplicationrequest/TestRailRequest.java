package apiutils.testrail.testrailapplicationrequest;

import apiutils.testrail.APIClient;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.ITestResult;

import static apiutils.testrail.testrailapplicationrequest.TestRailUtils.*;
import static steps.Utils.testRailGetTestId;
import static steps.Utils.writeScreenshotToResources;

public class TestRailRequest {

    public static void addTestWithScreenInTestRail(ISettingsFile testDataFile, int testResult, byte[]screenFile, String projectDir) {
        APIClient apiClient = new APIClient(testDataFile.getValue("/testRailUrl").toString());
        ISettingsFile credFile = new JsonSettingsFile(testDataFile.getValue("/credFilePath").toString());
        apiClient.setUser(credFile.getValue("/login").toString());
        apiClient.setPassword(credFile.getValue("/password").toString());
        String runId = addRun(apiClient,(int)testDataFile.getValue("/testRailProjectId"),(int)testDataFile.getValue("/testRailSuiteId"),
                testDataFile.getValue("/testRailTestName").toString(), testDataFile.getValue("/testRailDescription").toString());
        String testId = testRailGetTestId(getTestsRequest(apiClient,runId),runId);
        String testResultComment;
        switch (testResult) {
            case ITestResult.SUCCESS:
                testResultComment = "Test was successful";
                break;
            case ITestResult.FAILURE:
                testResultComment = "Test was failed";
                testResult = 5;
                break;
            default:
                testResultComment = "Test failed";
                testResult = 5;
        }
        String resultId = addResultRequest(apiClient,testId, testResult,testResultComment);
        writeScreenshotToResources(screenFile,projectDir,testDataFile.getValue("/imgsDirectory").toString(),
                testDataFile.getValue("/testRailScreenshotName").toString());
        addAttachmentToResult(apiClient,resultId,projectDir + testDataFile.getValue("/imgsDirectory").toString()
                + testDataFile.getValue("/testRailScreenshotName").toString());
    }
}
