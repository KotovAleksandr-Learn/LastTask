package Steps;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

public class Utils {

    public static String converteDataFileToProjectLogFormat(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder builder = new StringBuilder();
        String currentLine = reader.readLine();
        while (currentLine != null) {
            builder.append(currentLine);
            builder.append(" ");
            currentLine = reader.readLine();
        }
        String fileString=builder.toString().replaceAll("  "," ");
        fileString=fileString.substring(1,fileString.length()-1);
        return fileString;

    }



    public static boolean compareFiles(String firstFilePath, String secondFilePath) throws IOException {
        byte[] arrayBytesFirstFile = Files.readAllBytes(Paths.get(firstFilePath));
        byte[] arrayBytesSecondFile = Files.readAllBytes(Paths.get(secondFilePath));
        return Arrays.equals(arrayBytesFirstFile,arrayBytesSecondFile);
    }



    public static boolean checkSortingDateJson(JSONArray dateJson){
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S");
        Date[]arrayDate=new Date[dateJson.length()];

        for(int i=0;i<dateJson.length();i++){
            arrayDate[i]=format.parseDateTime(dateJson.getJSONObject(i).getString("startTime")).toDate();
            if(i>=1)
                if(!arrayDate[i-1].after(arrayDate[i])) return false;
        }
        return true;
    }
    public static boolean compareDataTest(JSONArray webJsonArray,JSONArray apiJsonArray){
        for(int i=0;i<webJsonArray.length();i++){
            int flag=0;
            for(int j=0;j<apiJsonArray.length();j++){
                if(webJsonArray.getJSONObject(i).toString().equalsIgnoreCase(apiJsonArray.getJSONObject(j).toString())) {
                    flag = 1;
                    if (flag==1) break;
                }
            }
            if (flag==0) return false;
        }

        return true;
    }




}
