package apiutils.testrail.testrailapplicationrequest;

import apiutils.testrail.APIClient;
import apiutils.testrail.APIException;
import org.json.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class TestRailUtils {

    public static String addRun(APIClient client, int projectId, int suiteId, String name, String description) {
        HashMap<String, Object> addRunParams = new HashMap<String, Object>();
        addRunParams.put("suite_id",suiteId);
        addRunParams.put("name",name);
        addRunParams.put("description",description);
        JSONObject addRunJson = null;
        try {
            addRunJson = (JSONObject)client.sendPost(TestRailEndPoints.ADD_RUN + projectId,addRunParams);
        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
        return new org.json.JSONObject(addRunJson.toJSONString()).get("id").toString();
    }

    public static JSONArray getTestsRequest(APIClient client, String runId) {
        org.json.simple.JSONArray testsJsonArray = null;
        try {
            testsJsonArray = (org.json.simple.JSONArray) client.sendGet(TestRailEndPoints.GET_TESTS + runId);
        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
        return new JSONArray(testsJsonArray.toJSONString());
    }

    public static String addResultRequest(APIClient client, String testId, int statusId, String comment) {
        HashMap<String, Object> addResultParams = new HashMap<String, Object>();
        addResultParams.put("status_id",statusId);
        addResultParams.put("comment",comment);
        JSONObject addResultJson = null;
        try {
            addResultJson = (JSONObject)client.sendPost(TestRailEndPoints.ADD_RESULT + testId,addResultParams);
        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
        return new org.json.JSONObject(addResultJson.toJSONString()).get("id").toString();
    }

    public static String addAttachmentToResult(APIClient client, String resultId, String filePath) {
        JSONObject addAttachment = null;
        try {
            addAttachment = (JSONObject)client.sendPost(TestRailEndPoints.ADD_ATTACHMENT_TO_RESULT + resultId,filePath);
        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
        return new org.json.JSONObject(addAttachment.toJSONString()).get("attachment_id").toString();
    }

}
