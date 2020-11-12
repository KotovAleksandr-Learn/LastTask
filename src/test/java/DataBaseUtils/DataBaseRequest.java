package DataBaseUtils;

import aquality.selenium.core.utilities.ISettingsFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DataBaseUtils.DataBaseConnection.createConnection;

public class DataBaseRequest {
    public static void doSqlRequest(ISettingsFile configFile, ISettingsFile testDataFile,byte[]screenFile) throws SQLException, ClassNotFoundException, IOException {


        createConnection(configFile.getValue("/dataBaseUrl").toString(),configFile.getValue("/dataBaseLogin").toString(),configFile.getValue("/dataBasePassword").toString());

        Class.forName(configFile.getValue("/dataBaseDriver").toString());

        ResultSet projectId= DataBaseConnection.statement.executeQuery("select * from project where name='"+testDataFile.getValue("/newProjectName").toString()+"'");
        projectId.next();

        String insertTestSql="insert into test (name,method_name,status_id,start_time,end_time,session_id,project_id,env,browser) values (?,?,?,?,?,?,?,?,?)";

        PreparedStatement insertTest= DataBaseConnection.connection.prepareStatement(insertTestSql);
        insertTest.setString(1,testDataFile.getValue("/testName").toString());
        insertTest.setString(2,testDataFile.getValue("/testMethodName").toString());
        insertTest.setString(3,testDataFile.getValue("/statusId").toString());
        insertTest.setString(4,testDataFile.getValue("/startTime").toString());
        insertTest.setString(5,testDataFile.getValue("/endTime").toString());
        insertTest.setString(6,testDataFile.getValue("/sessionId").toString());
        insertTest.setString(7,projectId.getString(1));
        insertTest.setString(8,testDataFile.getValue("/environment").toString());
        insertTest.setString(9,testDataFile.getValue("/browser").toString());
        insertTest.executeUpdate();


        ResultSet testId= DataBaseConnection.statement.executeQuery("select id from test where project_id="+projectId.getString(1));
        testId.next();

        String addLogSql="insert into log (content,is_exception,test_id) values(?,?,?)";

        PreparedStatement insertLog= DataBaseConnection.connection.prepareStatement(addLogSql);
        File logFile=new File(testDataFile.getValue("/logFile").toString());
        FileInputStream fisLog=new FileInputStream(logFile);

        insertLog.setBinaryStream(1,fisLog,(int)logFile.length());
        insertLog.setString(2,testDataFile.getValue("/isException").toString());
        insertLog.setString(3,testId.getString(1));

        insertLog.executeUpdate();

        String addAttachSql="insert into attachment (content,content_type,test_id) values (?,?,?)";
        PreparedStatement insertAttach= DataBaseConnection.connection.prepareStatement(addAttachSql);

        FileOutputStream fos=new FileOutputStream(new File(testDataFile.getValue("/firstImgPath").toString()));
        fos.write(screenFile);
        fos.close();
        File attachFile=new File(testDataFile.getValue("/firstImgPath").toString());
        FileInputStream fisAttach=new FileInputStream(attachFile);

        insertAttach.setBinaryStream(1,fisAttach,(int)attachFile.length());
        insertAttach.setString(2,testDataFile.getValue("/attachmentType").toString());
        insertAttach.setString(3,testId.getString(1));
        insertAttach.executeUpdate();
    }


}
