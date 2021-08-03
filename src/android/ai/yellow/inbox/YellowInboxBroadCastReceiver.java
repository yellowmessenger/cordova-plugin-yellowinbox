package ai.yellow.inbox;

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

import ai.yellow.inbox.utils.Utils;

public class YellowInboxBroadCastReceiver extends YmMessageReceiver {

  private final CallbackContext callBackContext;
  private final String titleString = "title";
  private final String bodyString = "body";
  private final String modelString = "model";
  private final String eventType = "eventType";

  YellowInboxBroadCastReceiver(CallbackContext callBackContext) {
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
      JSONObject event = args.getJSONObject(0);

      String title = event.getString(this.titleString);
      String body = event.getString(this.bodyString);
      JSONObject model = event.getJSONObject(this.modelString);
      String eventType = event.getString(this.eventType);
      if (eventType.equals(Utils.TicketCreateEvent)) {
        YmTicketCreateModel ymTicketCreateModel = Utils.gson.fromJson(model.toString(), YmTicketCreateModel.class);
        super.onTicketCreateEventReceived(title, body, ymTicketCreateModel);

      } else if (eventType.equals(Utils.TicketUpdateEvent)) {
        YmXMPPMessageModel ymTicketUpdateModel = Utils.gson.fromJson(model.toString(), YmXMPPMessageModel.class);
        super.onTicketUpdateEventReceived(title, body, ymTicketUpdateModel);

      }

      Utils.genericSuccessHelper(callbackContext);

    } catch (Exception e) {
      Utils.genericErrorHelper(e, callbackContext);
    }
  }

}
