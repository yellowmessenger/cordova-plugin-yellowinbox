# YMAgentSdk

## Installation

### cordova

Specify the path to the plugin folder here my path is ../YMAgentSdk

```
ionic cordova plugin add ../YMAgentSdk
```

## Usage

Plugin methods are directly callable

### Initialize sdk

```javascript
cordova.plugins.YMAgentSdk.init(
  successCallBack,
  failureCallBack,
  apiKey,
  userName,
  botId
);
```

- successCallBack: `function (value)`

  - value: `String` - "OK"

- failureCallBack :`function (errorJson)`

  - errorJson: `Object` -

    ```javascript
    {
      "success": Boolean,
      "error": String,
      "stackTrace": Object
    }
    ```

- apiKey: `String`
- userName: `String`
- userName: `String`
- botId: `String`

### Present overview screen

```javascript
cordova.plugins.YMAgentSdk.startOverviewScreen(
  successCallBack,
  failureCallBack
);
```

- successCallBack :`function (successResult)`

  - successResult: `String` - "OK"

- failureCallBack :`function (errorJson)`

  - errorJson: `Object` -

    ```javascript
    {
      "success": Boolean,
      "error": String,
      "stackTrace": Object
    }
    ```

### Present MyChats screen

```javascript
cordova.plugins.YMAgentSdk.startMyChatScreen(successCallBack, failureCallBack);
```

- successCallBack :`function (successResult)`

  - successResult: `String` - "OK"

- failureCallBack :`function (errorJson)`

  - errorJson: `Object` -

    ```javascript
    {
      "success": Boolean,
      "error": String,
      "stackTrace": Object
    }
    ```

### Other configurations

## Notifications

### 1. Push Notifications

YMAgentSdk supports firebase notifications. Push notifications needs `device token`

Firebase push notifications are sent, when app is not running in foreground or in background. Events like ticket create event, User message event and actions on ticket will trigger a push notification

### Device Token

Device token can be set using `setFirebaseDeviceToken` method. Pass `fcmToken` as a parameter to this method.

```javascript
cordova.plugins.YMAgentSdk.setFirebaseDeviceToken(deviceToken);
```

- deviceToken: `String`

Note: Firebase service account key is required to send notifications. You can share the service account key with us. More info [here](https://developers.google.com/assistant/engagement/notifications#get_a_service_account_key)

### 2. Local notifications

Local notifications data can be modified by catching the events `TicketCreateEvent` and `TicketUpdateEvent`

Local notifications are sent by XMPP, when app is running in foreground are background. Events like ticket create event, User message event and actions on ticket will trigger a XMPP local notification

```javascript
cordova.plugins.YMAgentSdk.setLocalReceiver(eventCallBack, failureCallBack);
```

on event callback you will receive event data

- eventCallBack : `function(event)`

  - event: `Object`

    ```javascript
    {
      title: String,
      body: String,
      model: Object,
      eventType: String
    }
    ```

- eventType: `String`

  - `TicketCreateEvent`
  - `TicketUpdateEvent`

Send back the updated data, to show local notification by calling `setUpdatedEvent`.

```javascript
cordova.plugins.YMAgentSdk.setUpdatedEvent(
  successCallBack,
  failureCallBack,
  title,
  body,
  model,
  eventType
);
```

- successCallBack :`function (successResult)`

  - successResult: `String` - "OK"

  failureCallBack :`function (errorJson)`

  - errorJson: `Object` -

    ```javascript
    {
      "success": Boolean,
      "error": String,
      "stackTrace": Object
    }
    ```

### 3. Handle background notifications for call join call

If a client is receiving a video call notification while the app is in background, there is no special handling required for it. Simply tapping on notification will take the user to the appropriate view and show a dialog to answer/decline the call.

In case the app is running and in the foreground, the client needs to listen to Firebase notification and call the following function.

Check if data received form firebase push notification has `callJoinUrl` key then call the `handleBackgroundNotification` to show the call joining pop up

```javascript
cordova.plugins.YMAgentSdk.handleBackgroundNotification(
  backgroundNotificationData
);
```

- backgroundNotificationData: `Object`

### Change Bot

SDK need a call to reset the Xmpp connection in case bot is getting changed
Also, we need to de-register old BOT (and client should register new one)

Current working chatbot in agent sdk can be changed by calling `changeBot`

```javascript
cordova.plugins.YMAgentSdk.changeBot(
  successCallBack,
  failureCallBack,
  botId,
  subscriptionId
);
```

- successCallBack : `function (successResult)`

  - successResult: `String` - "OK"

- failureCallBack :`function (errorJson)`

  - errorJson: `Object` -

    ```javascript
    {
      "success": Boolean,
      "error": String,
      "stackTrace": Object
    }
    ```

- botId: `String`
- subscriptionId: `String`

## Agent Status

To get availability status of logged in User against the bot id (Used for initialising the SDK), client can call the following method `getAgentStatus`

```javascript
cordova.plugins.YMAgentSdk.getAgentStatus(successCallBack, failureCallBack);
```

- successCallBack : `function (successResult)`

  - successResult: `String` - "OK"

- failureCallBack :`function (errorJson)`

  - errorJson: `Object` -

    ```javascript
    {
      "success": Boolean,
      "error": String,
      "stackTrace": Object
    }
    ```

- status: `String`

  - AVAILABLE
  - AWAY
  - BUSY
  - UNKNOWN ( ambiguous state )

### Change Agent Status

To change the status of logged in User against the bot id (Used for initialising the SDK), the client can call the following method `changeAgentStatus`

```javascript
cordova.plugins.YMAgentSdk.changeAgentStatus(
  successCallBack,
  failureCallBack,
  status
);
```

- successCallBack : `function (successResult)`

  - successResult: `String` - "OK"

- failureCallBack :`function (errorJson)`

  - errorJson: `Object` -

    ```javascript
    {
      "success": Boolean,
      "error": String,
      "stackTrace": Object
    }
    ```

- status: `String`

  - AVAILABLE
  - AWAY
  - BUSY

### Get Agents

To get List of all agents, the client can call the following method `getAgents`

```javascript
cordova.plugins.YMAgentSdk.getAgents(successCallBack, failureCallBack);
```

- successCallBack : `function (successResult)`

  - successResult: `Object`

- failureCallBack :`function (errorJson)`

  - errorJson: `Object` -

    ```javascript
    {
      "success": Boolean,
      "error": String,
      "stackTrace": Object
    }
    ```

## Logout

Please call the method `logout` to logout the user from SDK (call the api while the user is logging out from the app for cleanup).
By doing so, all the services and notifications will be terminated.

```javascript
cordova.plugins.YMAgentSdk.logout(successCallBack, failureCallBack);
});
```

- successCallBack : `function (success)`

  - success: `String` - "OK"

- failureCallBack :`function (errorJson)`

  - errorJson: `Object` -

    ```javascript
    {
      "success": Boolean,
      "error": String,
      "stackTrace": Object
    }
    ```
