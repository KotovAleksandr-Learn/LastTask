package steps;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;

public class Utils {

    public static boolean checkSortingDateJson(JSONArray dateJson) {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S");
        Date[]arrayDate = new Date[dateJson.length()];

        for (int i = 0;i < dateJson.length();i++) {
            arrayDate[i] = format.parseDateTime(dateJson.getJSONObject(i).getString("startTime")).toDate();
            if (i >= 1) {
                if (!arrayDate[i - 1].after(arrayDate[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean compareDataTest(JSONArray webJsonArray,JSONArray apiJsonArray) {
        for (int i = 0;i < webJsonArray.length();i++) {
            int flag = 0;
            for (int j = 0;j < apiJsonArray.length();j++) {
                if (webJsonArray.getJSONObject(i).toString().equalsIgnoreCase(apiJsonArray.getJSONObject(j).toString())) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 0) {
                return false;
            }
        }
        return true;
    }

    public static String converteDataFileToProjectLogFormat(String fileName) {
        String fileString = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            StringBuilder builder = new StringBuilder();
            String currentLine = reader.readLine();
            while (currentLine != null) {
                builder.append(currentLine);
                builder.append(" ");
                currentLine = reader.readLine();
            }
            fileString = builder.toString().replaceAll(" {2}"," ");
            fileString = fileString.substring(0,fileString.length() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileString;
    }

    public static boolean compareFiles(String firstFilePath, String secondFilePath) {
        byte[] arrayBytesFirstFile = new byte[0];
        byte[] arrayBytesSecondFile = new byte[0];
        try {
            arrayBytesFirstFile = Files.readAllBytes(Paths.get(firstFilePath));
            arrayBytesSecondFile = Files.readAllBytes(Paths.get(secondFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.equals(arrayBytesFirstFile,arrayBytesSecondFile);
    }
    public static void writeScreenshotToResources(byte[] screenFile, String projectDir, String fileDirectory, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(projectDir + fileDirectory + fileName));
            fos.write(screenFile);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String testRailGetTestId(JSONArray jsonArray, String runId) {
        String testId = null;
        for (int i = 0;i < jsonArray.length();i++) {
            if (jsonArray.getJSONObject(i).get("run_id").toString().equals(runId)) {
                testId = jsonArray.getJSONObject(i).get("id").toString();
                break;
            }
        }
        return testId;
    }
}
