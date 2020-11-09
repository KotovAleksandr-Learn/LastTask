package Steps;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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


    public static boolean checkSortingDate(List<String> testDate){
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S");
        Date[]arrayDate=new Date[testDate.size()];

        for(int i=0;i<testDate.size();i++){
            arrayDate[i]=format.parseDateTime(testDate.get(i)).toDate();
            if(i>=1)
                if(!arrayDate[i-1].after(arrayDate[i])) return false;
        }
        return true;
    }


}
