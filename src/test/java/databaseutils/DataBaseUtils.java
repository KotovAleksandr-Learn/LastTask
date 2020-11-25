package databaseutils;

import aquality.selenium.core.utilities.ISettingsFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static steps.Utils.converteDataFileToProjectLogFormat;
import static steps.Utils.writeScreenshotToResources;

public class DataBaseUtils {
    public static String logString;

    public static String getProjectId(String projectName) {
        String projectIdStr = null;
        try {
            ResultSet projectId = DataBaseConnection.statement.executeQuery("select * from project where name='" + projectName + "'");
            projectId.next();
            projectIdStr = projectId.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectIdStr;
    }

    public static void addNewTest(ISettingsFile testDataFile, String projectId) {
        String insertTestSql = "insert into test (name,method_name,status_id,start_time,end_time,session_id,project_id,env,browser)"
                + " values (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement insertTest = DataBaseConnection.connection.prepareStatement(insertTestSql);
            insertTest.setString(1,testDataFile.getValue("/testName").toString());
            insertTest.setString(2,testDataFile.getValue("/testMethodName").toString());
            insertTest.setString(3,testDataFile.getValue("/statusId").toString());
            insertTest.setString(4,testDataFile.getValue("/startTime").toString());
            insertTest.setString(5,testDataFile.getValue("/endTime").toString());
            insertTest.setString(6,testDataFile.getValue("/sessionId").toString());
            insertTest.setString(7,projectId);
            insertTest.setString(8,testDataFile.getValue("/environment").toString());
            insertTest.setString(9,testDataFile.getValue("/browser").toString());
            insertTest.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getTestId(String projectId) {
        String testIdStr = null;
        try {
            ResultSet testId = DataBaseConnection.statement.executeQuery("select id from test where project_id=" + projectId);
            testId.next();
            testIdStr = testId.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testIdStr;
    }

    public static void addLogFile(ISettingsFile testDataFile, String testId) {
        String addLogSql = "insert into log (content,is_exception,test_id) values(?,?,?)";
        try {
            PreparedStatement insertLog = DataBaseConnection.connection.prepareStatement(addLogSql);
            File logFile = new File(testDataFile.getValue("/logFile").toString());
            FileInputStream fisLog = new FileInputStream(logFile);
            insertLog.setBinaryStream(1,fisLog,(int)logFile.length());
            insertLog.setString(2,testDataFile.getValue("/isException").toString());
            insertLog.setString(3,testId);
            insertLog.executeUpdate();
            logString = converteDataFileToProjectLogFormat(testDataFile.getValue("/logFile").toString());
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void addAttachment(ISettingsFile testDataFile, String projectDir, String testId, byte[]screenFile) {
        String addAttachSql = "insert into attachment (content,content_type,test_id) values (?,?,?)";
        try {
            PreparedStatement insertAttach = DataBaseConnection.connection.prepareStatement(addAttachSql);
            writeScreenshotToResources(screenFile,projectDir,testDataFile.getValue("/imgsDirectory").toString(),
                    testDataFile.getValue("/firstImageName").toString());
            File attachFile = new File(projectDir + testDataFile.getValue("/imgsDirectory").toString()
                    + testDataFile.getValue("/firstImageName").toString());
            FileInputStream fisAttach = new FileInputStream(attachFile);
            insertAttach.setBinaryStream(1,fisAttach,(int)attachFile.length());
            insertAttach.setString(2,testDataFile.getValue("/attachmentType").toString());
            insertAttach.setString(3,testId);
            insertAttach.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
