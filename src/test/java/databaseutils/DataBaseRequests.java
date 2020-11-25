package databaseutils;

import aquality.selenium.core.utilities.ISettingsFile;

public class DataBaseRequests {

    public static void addNewTestWithLogAndAttach(ISettingsFile configFile, ISettingsFile testDataFile, String projectDir, byte[]screenFile) {
        try {
            Class.forName(configFile.getValue("/dataBaseDriver").toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        DataBaseConnection.createConnection(configFile.getValue("/dataBaseUrl").toString(),
                configFile.getValue("/dataBaseLogin").toString(),configFile.getValue("/dataBasePassword").toString());
        String projectId = DataBaseUtils.getProjectId(testDataFile.getValue("/newProjectName").toString());
        DataBaseUtils.addNewTest(testDataFile,projectId);
        String testId = DataBaseUtils.getTestId(projectId);
        DataBaseUtils.addLogFile(testDataFile,testId);
        DataBaseUtils.addAttachment(testDataFile,projectDir,testId,screenFile);
    }
}
