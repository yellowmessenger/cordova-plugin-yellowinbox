package ai.yellow.inbox;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.yellowmessenger.YellowInbox;
import com.yellowmessenger.YmAppProcessLifeCycleListener;
import com.yellowmessenger.datalayer.vo.Resource;
import com.yellowmessenger.ui.vo.YmAgentStatus;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import ai.yellow.inbox.utils.Utils;

/**
 * This class echoes a string called from JavaScript.
 */
public class YellowInboxModule extends CordovaPlugin {

  private Context ionicContext;
  private Activity ionicActivity;
  private YellowInboxBroadCastReceiver ymMessageReceiver;

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

    switch (action) {
      case "initialize": {
        initialize(args, callbackContext);
        return true;
      }
      case "setFirebaseDeviceToken": {
        setFirebaseDeviceToken(args, callbackContext);
        return true;
      }
      case "setLocalReceiver": {
        setLocalReceiver(args, callbackContext);
        return true;
      }
      case "setUpdatedEvent": {
        setUpdatedEvent(args, callbackContext);
        return true;
      }
      case "setAgentStatus": {
        setAgentStatus(args, callbackContext);
        return true;
      }
      case "getAgentStatus": {
        getAgentStatus(args, callbackContext);
        return true;
      }
      case "handleBackgroundNotification": {
        handleBackgroundNotification(args, callbackContext);
        return true;
      }
      case "logout": {
        logout(args, callbackContext);
        return true;
      }
      case "showOverviewScreen": {
        startOverviewActivity(args, callbackContext);
        return true;
      }
      case "showMyChatScreen": {
        startMyChatActivity(args, callbackContext);
        return true;
      }
      case "getAllAgentStatus": {
        getAllAgentStatus(args, callbackContext);
        return true;
      }
    }
    return false;
  }

  private void getAllAgentStatus(JSONArray args, CallbackContext callbackContext) {
    ionicActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        try {
          callbackContext.success(Utils.dataClassToJSONArray(YellowInbox.getAllAgentStatus()));
        } catch (Exception e) {

          Utils.genericErrorHelper(e, callbackContext);

        }
      }
    });
  }

  private void initialize(JSONArray args, CallbackContext callbackContext) {

    ionicContext = this.cordova.getActivity().getApplicationContext();
    ionicActivity = cordova.getActivity();

    this.cordova.getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        try {

          String apiKey = args.getString(0);
          String userId = args.getString(1);
          String botId = args.getString(2);

          ProcessLifecycleOwner.get().getLifecycle().addObserver(new YmAppProcessLifeCycleListener());

          YellowInbox.init(ionicContext, apiKey, userId, botId).observe(ProcessLifecycleOwner.get(),
              new Observer<Resource<Void>>() {
                @Override
                public void onChanged(Resource<Void> resource) {
                  switch (resource.getStatus()) {
                    case SUCCESS:
                      Log.i("YMLog", "success");
                      Utils.genericSuccessHelper(callbackContext);
                      break;
                    case ERROR:
                      Log.i("YMLog", "error");
                      Utils.sdkErrorHelper(resource.getMessage(), callbackContext);
                      break;
                    case LOADING:
                      break;
                  }
                }
              });

        } catch (Exception e) {
          Utils.genericErrorHelper(e, callbackContext);
        }
      }
    });

  }

  private void setFirebaseDeviceToken(JSONArray args, CallbackContext callbackContext) {
    try {

      String token = args.getString(0);
      YellowInbox.setFirebaseDeviceToken(token);
      Utils.genericSuccessHelper(callbackContext);

    } catch (Exception e) {

      Utils.genericErrorHelper(e, callbackContext);

    }
  }

  private void setLocalReceiver(JSONArray args, CallbackContext callbackContext) {

    ymMessageReceiver = new YellowInboxBroadCastReceiver(callbackContext);
    YellowInbox.setLocalReceiver(ymMessageReceiver);

  }

  private void setUpdatedEvent(JSONArray args, CallbackContext callbackContext) {
    ymMessageReceiver.sendNotification(args, callbackContext);
  }

  private void setAgentStatus(JSONArray args, CallbackContext callbackContext) {
    ionicActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        try {
          JSONObject ymAgentStatusJSON = args.getJSONObject(0);
          YmAgentStatus ymAgentStatus = Utils.gson.fromJson(ymAgentStatusJSON.toString(), YmAgentStatus.class);

          YellowInbox.setAgentStatus(ymAgentStatus).observe(ProcessLifecycleOwner.get(),
              new Observer<Resource<Void>>() {
                @Override
                public void onChanged(Resource<Void> resource) {
                  switch (resource.getStatus()) {
                    case SUCCESS:
                      callbackContext.success();
                      break;
                    case ERROR:
                      Utils.sdkErrorHelper(resource.getMessage(), callbackContext);
                      break;
                    case LOADING:
                      break;
                  }
                }
              });

        } catch (Exception e) {

          Utils.genericErrorHelper(e, callbackContext);

        }
      }
    });
  }

  private void getAgentStatus(JSONArray args, CallbackContext callbackContext) {

    ionicActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        try {
          YellowInbox.getAgentStatus().observe(ProcessLifecycleOwner.get(), new Observer<Resource<YmAgentStatus>>() {
            @Override
            public void onChanged(Resource<YmAgentStatus> ymAgentStatusResource) {

              switch (ymAgentStatusResource.getStatus()) {
                case SUCCESS:
                  callbackContext.success(Utils.dataClassToJSONObject(ymAgentStatusResource.getData()));
                  break;
                case ERROR:
                  Utils.sdkErrorHelper(ymAgentStatusResource.getMessage(), callbackContext);
                  break;
                case LOADING:
                  break;
              }
            }
          });

        } catch (Exception e) {

          Utils.genericErrorHelper(e, callbackContext);

        }
      }
    });

  }

  private void handleBackgroundNotification(JSONArray args, CallbackContext callbackContext) throws JSONException {
    JSONObject extraParams = args.getJSONObject(0);
    Map<String, Object> extraParamsMap = Utils.gson.fromJson(extraParams.toString(), Utils.mapStringObjectType);
    YellowInbox.handleBackgroundNotification(ionicActivity, extraParamsMap);
  }

  private void logout(JSONArray args, CallbackContext callbackContext) {
    ionicActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        try {

          YellowInbox.logout().observe(ProcessLifecycleOwner.get(), new Observer<Resource<Void>>() {
            @Override
            public void onChanged(Resource<Void> resource) {
              switch (resource.getStatus()) {
                case SUCCESS:
                  callbackContext.success();
                  break;
                case ERROR:
                  Utils.sdkErrorHelper(resource.getMessage(), callbackContext);
                  break;
                case LOADING:
                  break;
              }
            }
          });
          Utils.genericSuccessHelper(callbackContext);

        } catch (Exception e) {

          Utils.genericErrorHelper(e, callbackContext);

        }
      }
    });

  }

  private void startOverviewActivity(JSONArray args, CallbackContext callbackContext) {

    ionicActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        try {

          YellowInbox.startOverviewActivity(ionicActivity);
          Utils.genericSuccessHelper(callbackContext);

        } catch (Exception e) {
          Utils.genericErrorHelper(e, callbackContext);
        }
      }
    });
  }

  private void startMyChatActivity(JSONArray args, CallbackContext callbackContext) {

    ionicActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        try {

          YellowInbox.startMyChatActivity(ionicActivity);
          Utils.genericSuccessHelper(callbackContext);

        } catch (Exception e) {

          Utils.genericErrorHelper(e, callbackContext);

        }
      }
    });
  }

}
