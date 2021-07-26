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

import ai.yellow.agentsdk.models.ClientModel;
import ai.yellow.agentsdk.utils.Utils;

/**
 * This class echoes a string called from JavaScript.
 */
public class AgentSdkModule extends CordovaPlugin {

  private Context ionicContext;
  private Activity ionicActivity;
  private AgentSdkIonicBroadCastReceiver ymMessageReceiver;

  @Override
  public void onStart() {
    super.onStart();
    Log.d("YmChat", "Startd applciation");
  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

    switch (action) {
      case "init": {
        init(args, callbackContext);
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
      case "changeBot": {
        changeBot(args, callbackContext);
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
      case "getOverviewFragment": {
        Log.d("YmLog", "Starting overview fragment");
        getOverviewFragment(args, callbackContext);
        return true;
      }
      case "startOverviewActivity": {
        Log.d("YmLog", "Starting overview activity");
        startOverviewActivity(args, callbackContext);
        return true;
      }
      case "getMyChatsFragment": {
        Log.d("YmLog", "Starting overview fragment");
        getMyChatsFragment(args, callbackContext);
        return true;
      }
      case "startMyChatActivity": {
        Log.d("YmLog", "Starting MyChat Activity");
        startMyChatActivity(args, callbackContext);
        return true;
      }
      case "getAgents": {
        Log.d("YmLog", "Getting agent status");
        getAgents(args, callbackContext);
        return true;
      }

    }
    return false;
  }

  private void init(JSONArray args, CallbackContext callbackContext) {

    Log.d("YmLog", "initialising");

    ionicContext = this.cordova.getActivity().getApplicationContext();
    ionicActivity = cordova.getActivity();
    // this.cordova.getActivity().runOnUiThread(new Runnable() {
    // @Override
    // public void run() {
    // try {
    //
    // String apiKey = args.getString(0);
    // String userId = args.getString(1);
    // String botId = args.getString(2);
    //
    // ProcessLifecycleOwner.get().getLifecycle().addObserver(new
    // YmAppProcessLifeCycleListener());
    //
    // final Observer<? super Resource<Void>> observer = new
    // Observer<Resource<Void>>() {
    // @Override
    // public void onChanged(Resource<Void> resource) {
    // Log.d("YmLog", "Ssending success");
    // Utils.successHelper(callbackContext);
    // }
    // };
    //
    // YellowInbox.init(ionicContext, apiKey, userId,
    // botId).observe(ProcessLifecycleOwner.get(), observer);
    //
    // } catch (Exception e) {
    // Utils.errorHelper(e, callbackContext);
    // }
    // }
    // });

    ionicActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        try {
          ProcessLifecycleOwner.get().getLifecycle().addObserver(new YmAppProcessLifeCycleListener());

          String apiKey = args.getString(0);
          String userId = args.getString(1);
          String botId = args.getString(2);

          YellowInbox.initInternal(ionicContext, botId, getInternalData(botId, userId, apiKey),
              "ai.yellow.supportagent");
          Utils.genericSuccessHelper(callbackContext);

        } catch (Exception e) {
          Utils.genericErrorHelper(e, callbackContext);
        }
      }
    });

  }

  private String getInternalData(String botId, String userId, String apiKey) {
    Roles roles = new Roles(botId, "ROLE_BOT_ECHO_AGENT");
    List<Roles> listRoles = new ArrayList<Roles>();
    listRoles.add(roles);
    User user = new User(null, null, null, listRoles, "Purushottam yadav battula", null, null,
        "purushottam.yadav@yellow.ai", userId, null);
    ClientModel model = new ClientModel(apiKey, user);

    return Utils.gson.toJson(model);

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

    ymMessageReceiver = new AgentSdkIonicBroadCastReceiver(callbackContext);
    YellowInbox.setLocalReceiver(ymMessageReceiver);

  }

  private void setUpdatedEvent(JSONArray args, CallbackContext callbackContext) {
    ymMessageReceiver.sendNotification(args, callbackContext);
  }

  private void changeBot(JSONArray args, CallbackContext callbackContext) {
    ionicActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        try {

          String botId = args.getString(0);
          int subscriptionId = args.getInt(1);

          YellowInbox.changeBot(botId, subscriptionId, "ai.yellow.supportagent");
          Utils.genericSuccessHelper(callbackContext);

        } catch (Exception e) {

          Utils.genericErrorHelper(e, callbackContext);

        }
      }
    });

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

  private void getOverviewFragment(JSONArray args, CallbackContext callbackContext) {
    try {

      Fragment overViewFragment = YellowInbox.getOverviewFragment();
      Utils.genericSuccessHelper(callbackContext);

    } catch (Exception e) {
      Utils.genericErrorHelper(e, callbackContext);
    }
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

  private void getMyChatsFragment(JSONArray args, CallbackContext callbackContext) {
    try {
      Fragment myChatsFragment = YellowInbox.getMyChatsFragment();
    } catch (Exception e) {
      Utils.genericErrorHelper(e, callbackContext);
    }
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

  private void getAgents(JSONArray args, CallbackContext callbackContext) {
    ionicActivity.runOnUiThread(new Runnable() {
      @Override
      public void run() {

        try {
          YellowInbox.getAgents().observe(ProcessLifecycleOwner.get(), new Observer<Resource<List<AgentModel>>>() {
            @Override
            public void onChanged(Resource<List<AgentModel>> agentsResource) {
              switch (agentsResource.getStatus()) {
                case SUCCESS:
                  try {
                    callbackContext
                        .success(new JSONArray(Utils.gson.toJson(agentsResource.getData(), Utils.listAgentModelType)));
                  } catch (Exception e) {
                    Utils.genericErrorHelper(e, callbackContext);
                  }
                  break;
                case ERROR:
                  Utils.sdkErrorHelper(agentsResource.getMessage(), callbackContext);
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

}
