package Steps;

import org.json.JSONArray;

import java.util.List;

public class CompareTestsName {

    public static boolean compareTestsName(JSONArray testsJsonArray, List<String> testsNameList){

        for(int i=0;i<testsNameList.size();i++){
            int flag=0;
            for(int j=0;j<testsJsonArray.length();j++){
                if(testsNameList.get(i).equals(testsJsonArray.getJSONObject(j).getString("name"))) {
                    flag = 1;
                    if (flag==1) break;
                }
            }
            if (flag==0) return false;
        }
        return true;
    }


}
