package apiutils;

import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    public static String getRequest(HashMap<String, String> requestParams, String endPoint, int httpStatus) {
        Response response =
                given().params(requestParams)
                        .when().get(endPoint)
                        .then().statusCode(httpStatus).extract().response();
        return response.body().asString();
    }

    public static String postRequest(HashMap<String, String> requestParams, String endPoint, int httpStatus) {
        Response response =
                given().params(requestParams)
                        .when().post(endPoint)
                        .then().statusCode(httpStatus).extract().response();
        return response.body().asString();
    }

    public static String putRequest(HashMap<String, String> requestParams, String endPoint, int httpStatus) {
        Response response =
                given().params(requestParams)
                        .when().put(endPoint)
                        .then().statusCode(httpStatus).extract().response();
        return response.body().asString();
    }

    public static String deleteRequest(HashMap<String, String> requestParams, String endPoint, int httpStatus) {
        Response response =
                given().params(requestParams)
                        .when().delete(endPoint)
                        .then().statusCode(httpStatus).extract().response();
        return response.body().asString();
    }
}
