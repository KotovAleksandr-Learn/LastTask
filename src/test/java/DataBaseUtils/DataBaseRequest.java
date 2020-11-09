package DataBaseUtils;

import aquality.selenium.browser.AqualityServices;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DataBaseRequest {


    public static void doSqlRequest() throws SQLException, ClassNotFoundException, IOException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        ResultSet projectId=DataBaseConnection.statement.executeQuery("select * from project where name='MyProject'");
        projectId.next();
        System.out.println("Project id "+projectId.getString(1));


        // statement.executeUpdate("insert into session(session_key,created_time,build_number) values('2020-11-08 17:12:12.0','2020-11-08 17:12:12.0',1)");

        //  statement.executeUpdate("insert into test (name,method_name,status_id,start_time,end_time,session_id,project_id,env) values ('testname','method_name',1,'2020-11-08 17:12:12.0','2020-11-08 17:15:11.0',20,7,'KOTOV')");

        String insertTestSql="insert into test (name,method_name,status_id,start_time,end_time,session_id,project_id,env,browser) values (?,?,?,?,?,?,?,?,?)";

        PreparedStatement insertTest=DataBaseConnection.connection.prepareStatement(insertTestSql);
        insertTest.setString(1,"Check authorization form with correct login/password");
        insertTest.setString(2,"checkAuthFormCorrectLoginPassworTest");
        insertTest.setString(3,"1");
        insertTest.setString(4,"2020-11-08 17:09:11.0");
        insertTest.setString(5,"2020-11-08 17:10:11.0");
        insertTest.setString(6,"20");
        insertTest.setString(7,projectId.getString(1));
        insertTest.setString(8,"KOTOV");
        insertTest.setString(9,"chrome");
        insertTest.executeUpdate();


        ResultSet testId=DataBaseConnection.statement.executeQuery("select id from test where project_id="+projectId.getString(1));
        testId.next();

        String addLogSql="insert into log (content,is_exception,test_id) values(?,?,?)";

        PreparedStatement insertLog=DataBaseConnection.connection.prepareStatement(addLogSql);
        File logFile=new File("src/test/resources/file.log");
        FileInputStream fisLog=new FileInputStream(logFile);

        insertLog.setBinaryStream(1,fisLog,(int)logFile.length());
        insertLog.setString(2,"0");
        insertLog.setString(3,testId.getString(1));


        insertLog.executeUpdate();


        //добавление скриншотика
        String addAttachSql="insert into attachment (content,content_type,test_id) values (?,?,?)";
        PreparedStatement insertAttach=DataBaseConnection.connection.prepareStatement(addAttachSql);
        //
        FileOutputStream fos=new FileOutputStream(new File("src/test/resources/screenshot.png"));
        fos.write(AqualityServices.getBrowser().getScreenshot());
        fos.close();
        File attachFile=new File("src/test/resources/screenshot.png");
        FileInputStream fisAttach=new FileInputStream(attachFile);

        insertAttach.setBinaryStream(1,fisAttach,(int)attachFile.length());
        insertAttach.setString(2,"image/png");
        insertAttach.setString(3,testId.getString(1));
        insertAttach.executeUpdate();


    }




}
