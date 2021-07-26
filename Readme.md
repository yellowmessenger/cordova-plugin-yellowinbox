# ymchat

## Installation

### cordova

Specify the path to the plugin folder here my path is ../ymchat

```
ionic cordova plugin add ../YMAgentSdk
```

## Usage

plugin methods are directly callable

### initialize sdk

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

- failureCallBack :function (errorJson)

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

- successCallBack :`function (value)`

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

### Other configurations

## Push Notifications

YMAgentSdk supports firebase notifications. Push notifications needs `device token`

### Device Token

Device token can be set using `setFirebaseDeviceToken` method. Pass `fcmToken` as a parameter to this method.

```javascript
cordova.plugins.YMAgentSdk.setFirebaseDeviceToken(token);
```

token: `String`

Note: Firebase service account key is required to send notifications. You can share the service account key with us. More info [here](https://developers.google.com/assistant/engagement/notifications#get_a_service_account_key)

### Handle background notifications for call join url

check if data received form firebase push notification has `callJoinUrl` key then call the `handleBackgroundNotification` to show the call joining pop up

```javascript
cordova.plugins.YMAgentSdk.handleBackgroundNotification(
  backgroundNotificationData
);
```

backgroundNotificationData: `Object`

### Local notifications

Local notifications data can be modified by catching the events `TicketCreateEvent` and `TicketUpdateEvent`

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
      model: JSON,
      eventType: String
    }
    ```

- eventType: `String`

  - `TicketCreateEvent`
  - `TicketUpdateEvent`

send back the updated data to throw local notification by calling `setUpdatedEvent`

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

- successCallBack :`function (value)`

  - value: `String` - "OK"

  failureCallBack :function (errorJson)

  - errorJson: `Object` -

    ```javascript
    {
      "success": Boolean,
      "error": String,
      "stackTrace": Object
    }
    ```

### Change Bot

Current working chatbot in agent sdk can be changed by calling `changeBot`

```javascript
cordova.plugins.YMAgentSdk.changeBot(
  successCallBack,
  failureCallBack,
  botId,
  subscriptionId
);
```

- successCallBack : `function (value)`

  - value: `String` - "OK"

- failureCallBack :function (errorJson)

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

### change Agent Status

Current agent status can be changed by calling `changeAgentStatus`

```javascript
cordova.plugins.YMAgentSdk.changeAgentStatus(
  successCallBack,
  failureCallBack,
  status
);
```

- successCallBack : `function (value)`

  - value: `String` - "OK"

- failureCallBack :function (errorJson)

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

## Agent Status

Current agent status can be accessed by calling `getAgentStatus`

```javascript
cordova.plugins.YMAgentSdk.getAgentStatus(successCallBack, failureCallBack);
```

- successCallBack : `function (value)`

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

- status: `String`

  - AVAILABLE
  - AWAY
  - BUSY
  - UNKNOWN ( ambiguous state )

## logout

Logout can be performed by calling `logout`

```javascript
cordova.plugins.YMAgentSdk.logout(successCallBack, failureCallBack);
});
```

- successCallBack : `function (value)`

  - value: `String` - "OK"

- failureCallBack :function (errorJson)

  - errorJson: `Object` -

    ```javascript
    {
      "success": Boolean,
      "error": String,
      "stackTrace": Object
    }
    ```
