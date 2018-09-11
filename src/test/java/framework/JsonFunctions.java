package framework;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonFunctions {

    public static String parseResponseBody(String parseString, String firstParam, String secondParam) {
        String param = null;
        try {
            JSONObject object1 = new JSONObject(getResponseBody(parseString,firstParam));
            param = object1.getString(secondParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return param;
    }

    public static String getResponseBody(String parseString,String firstParam)  {
        String body = null;
        try {
            JSONObject object = new JSONObject(parseString);
            body = object.getString(firstParam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return body;
    }
}

