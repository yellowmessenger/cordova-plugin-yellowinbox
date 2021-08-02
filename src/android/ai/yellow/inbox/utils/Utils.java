package ai.yellow.inbox.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yellowmessenger.datalayer.vo.AgentModel;

import org.apache.cordova.CallbackContext;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Utils {
  public static Gson gson = new Gson();

  public static String TicketCreateEvent = "TicketCreateEvent";
  public static String TicketUpdateEvent = "TicketUpdateEvent";

  public static Type mapStringObjectType = new TypeToken<Map<String, Object>>() {
  }.getType();

  public static Type listAgentModelType = new TypeToken<List<AgentModel>>() {
  }.getType();

  public static void genericErrorHelper(Exception exception, CallbackContext callbackContext) {
    Log.e("YmLog", "Failure", exception);
    try {
      String error = exception.getMessage();
      StackTraceElement[] stackTrace = exception.getStackTrace();

      JSONObject errorJson = new JSONObject();
      errorJson.put("success", false);
      errorJson.put("error", error);
      errorJson.put("stackTrace", stackTrace);
      callbackContext.error(errorJson);

    } catch (Exception e) {

      callbackContext.error("Error");

    }

  }

  public static void sdkErrorHelper(String error, CallbackContext callbackContext) {
    Log.d("YmLog", error);
    try {

      JSONObject errorJson = new JSONObject();
      errorJson.put("success", false);
      errorJson.put("error", error);
      errorJson.put("stackTrace", new JSONObject());
      callbackContext.error(errorJson);

    } catch (Exception e) {

      callbackContext.error("Error");

    }

  }

  public static void genericSuccessHelper(CallbackContext callbackContext) {
    Log.d("YmLog", "Success");
    callbackContext.success();

  }
}
