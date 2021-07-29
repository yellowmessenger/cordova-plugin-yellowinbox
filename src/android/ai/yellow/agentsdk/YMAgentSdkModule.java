package ai.yellow.agentsdk;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.yellowmessenger.YellowInbox;
import com.yellowmessenger.YmAppProcessLifeCycleListener;
import com.yellowmessenger.datalayer.vo.AgentModel;
import com.yellowmessenger.datalayer.vo.Resource;
import com.yellowmessenger.datalayer.vo.Roles;
import com.yellowmessenger.datalayer.vo.User;
import com.yellowmessenger.ui.vo.YmAgentStatus;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ai.yellow.agentsdk.utils.Utils;

/**
 * This class echoes a string called from JavaScript.
 */
public class YMAgentSdkModule extends CordovaPlugin {

  private Context ionicContext;
  private Activity ionicActivity;
  private YMAgentSdkIonicBroadCastReceiver ymMessageReceiver;

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
      case "changeAgentStatus": {
        changeAgentStatus(args, callbackContext);
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
      case "startOverviewActivity": {
        startOverviewActivity(args, callbackContext);
        return true;
      }
      case "startMyChatActivity": {
        startMyChatActivity(args, callbackContext);
        return true;
      }
    }
    return false;
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

          final Observer<? super Resource<Void>> observer = new Observer<Resource<Void>>() {
            @Override
            public void onChanged(Resource<Void> resource) {
              Log.d("YmLog", "Sending success");
              Utils.genericSuccessHelper(callbackContext);
            }
          };

          YellowInbox.init(ionicContext, apiKey, userId, botId).observe(ProcessLifecycleOwner.get(), observer);

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
    Log.d("YmLog", "attached local listener");

    ymMessageReceiver = new YMAgentSdkIonicBroadCastReceiver(callbackContext);
    YellowInbox.setLocalReceiver(ymMessageReceiver);

  }

  private void setUpdatedEvent(JSONArray args, CallbackContext callbackContext) {
    ymMessageReceiver.sendNotification(args, callbackContext);
  }

  private void changeAgentStatus(JSONArray args, CallbackContext callbackContext) {
    ionicActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        try {

          String status = args.getString(0);
          YmAgentStatus statusToChange = YmAgentStatus.UNKNOWN;
          switch (status) {
            case "AVAILABLE":
              statusToChange = YmAgentStatus.AVAILABLE;
              break;
            case "AWAY":
              statusToChange = YmAgentStatus.AWAY;
              break;
            case "DND":
            case "BUSY":
              statusToChange = YmAgentStatus.BUSY;
              break;
          }

          Log.d("YmLog", "Changing status to " + status);

          YellowInbox.changeAgentStatus(statusToChange).observe(ProcessLifecycleOwner.get(),
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
                  callbackContext.success(ymAgentStatusResource.getData().toString());
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

          YellowInbox.logout();
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
