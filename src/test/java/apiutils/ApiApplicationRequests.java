package apiutils;

import org.apache.http.HttpStatus;
import org.json.JSONArray;

import java.util.HashMap;

public class ApiApplicationRequests {
    public static String getTokenRequest(String variant) {
        HashMap<String, String> requestParameters = new HashMap<>();
        requestParameters.put("variant",variant);
        return ApiUtils.postRequest(requestParameters, ApiEndPoints.GET_TOKEN_POINT,HttpStatus.SC_OK);
    }

    public static JSONArray getTestsListJsonFormatRequest(String projectId) {
        HashMap<String, String> requestParameters = new HashMap<>();
        requestParameters.put("projectId",projectId);
        return new JSONArray(ApiUtils.postRequest(requestParameters,ApiEndPoints.GET_LIST_TESTS_JSON_POINT,HttpStatus.SC_OK));
    }
}
