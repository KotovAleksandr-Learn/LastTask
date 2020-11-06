
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONArray;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ApiUtils {



    public static String getToken(String variant){

        HashMap<String,String>requestParameters=new HashMap<>();
        requestParameters.put("variant",variant);

        Response  response=
                given().params(requestParameters).
                    when().post(ApiEndPoints.getTokenPoint).
                            then().statusCode(HttpStatus.SC_OK).extract().response();

        return response.body().asString();
    }



    public static JSONArray getTestsListJsonFormat(String projectId){

        HashMap<String,String>requestParameters=new HashMap<>();
        requestParameters.put("projectId",projectId);
        Response  response=
                given().params(requestParameters).
                        when().post(ApiEndPoints.getListTestsJson).
                        then().statusCode(HttpStatus.SC_OK).extract().response();

        System.out.println("respponse"+response.body().asString());
        return new JSONArray(response.body().asString());
      }





}
