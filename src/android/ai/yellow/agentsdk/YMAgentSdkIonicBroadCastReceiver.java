package ai.yellow.agentsdk;

import android.util.Log;

import com.yellowmessenger.YmMessageReceiver;
import com.yellowmessenger.ui.xmpp.model.YmTicketCreateModel;
import com.yellowmessenger.ui.xmpp.model.YmXMPPMessageModel;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

import ai.yellow.agentsdk.utils.Utils;

public class YMAgentSdkIonicBroadCastReceiver extends YmMessageReceiver {

  private final CallbackContext callBackContext;
  private final String titleString = "title";
  private final String bodyString = "body";
  private final String modelString = "model";
  private final String eventType = "eventType";

  YMAgentSdkIonicBroadCastReceiver(CallbackContext callBackContext) {
    this.callBackContext = callBackContext;
  }

  @Override
  public void onTicketCreateEventReceived(@Nullable String title, @Nullable String body,
      @NotNull YmTicketCreateModel model) {
    try {
      JSONObject event = new JSONObject();
      event.put(titleString, title);
      event.put(bodyString, body);
      event.put(modelString, new JSONObject(Utils.gson.toJson(model, YmTicketCreateModel.class)));
      event.put(eventType, Utils.TicketCreateEvent);

      PluginResult successPluginResult = new PluginResult(PluginResult.Status.OK, event);
      successPluginResult.setKeepCallback(true);
      this.callBackContext.sendPluginResult(successPluginResult);

    } catch (Exception e) {
      Log.e("YMLog", "error", e);

      PluginResult errorPluginResult = new PluginResult(PluginResult.Status.ERROR, e.getStackTrace().toString());
      errorPluginResult.setKeepCallback(true);
      this.callBackContext.sendPluginResult(errorPluginResult);
      try {
        super.clone();
      } catch (CloneNotSupportedException cloneNotSupportedException) {
        cloneNotSupportedException.printStackTrace();
      }

    }
  }

  @Override
  public void onTicketUpdateEventReceived(@Nullable String title, @Nullable String body,
      @NotNull YmXMPPMessageModel model) {
    try {
      JSONObject event = new JSONObject();
      event.put(titleString, title);
      event.put(bodyString, body);
      event.put(modelString, new JSONObject(Utils.gson.toJson(model, YmXMPPMessageModel.class)));
      event.put(eventType, Utils.TicketUpdateEvent);

      PluginResult successPluginResult = new PluginResult(PluginResult.Status.OK, event);
      successPluginResult.setKeepCallback(true);
      this.callBackContext.sendPluginResult(successPluginResult);
      ;

    } catch (Exception e) {

      PluginResult successPluginResult = new PluginResult(PluginResult.Status.ERROR, "Parsing error");
      successPluginResult.setKeepCallback(true);
      this.callBackContext.sendPluginResult(successPluginResult);

    }
  }

  public void sendNotification(JSONArray args, CallbackContext callbackContext) {
    try {

      String title = args.getString(0);
      String body = args.getString(1);
      JSONObject data = args.getJSONObject(2);
      String eventType = args.getString(3);
      if (eventType.equals(Utils.TicketCreateEvent)) {
        YmTicketCreateModel ymTicketCreateModel = Utils.gson.fromJson(data.toString(), YmTicketCreateModel.class);
        super.onTicketCreateEventReceived(title, body, ymTicketCreateModel);

      } else if (eventType.equals(Utils.TicketUpdateEvent)) {
        YmXMPPMessageModel ymTicketUpdateModel = Utils.gson.fromJson(data.toString(), YmXMPPMessageModel.class);
        super.onTicketUpdateEventReceived(title, body, ymTicketUpdateModel);

      }

      Utils.genericSuccessHelper(callbackContext);

    } catch (Exception e) {
      Utils.genericErrorHelper(e, callbackContext);
    }
  }

}
