package APIUtils;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONArray;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class APIUtils {

    public static String getToken(String variant){

        HashMap<String,String> requestParameters=new HashMap<>();
        requestParameters.put("variant",variant);

        Response response=
                given().params(requestParameters).
                        when().post(APIEndPoints.GET_TOKEN_POINT).
                        then().statusCode(HttpStatus.SC_OK).extract().response();
        return response.body().asString();
    }

    public static JSONArray getTestsListJsonFormat(String projectId){

        HashMap<String,String>requestParameters=new HashMap<>();
        requestParameters.put("projectId",projectId);
        Response  response=
                given().params(requestParameters).
                        when().post(APIEndPoints.GET_LIST_TESTS_JSON_POINT).
                        then().statusCode(HttpStatus.SC_OK).extract().response();

        return new JSONArray(response.body().asString());
    }





}
