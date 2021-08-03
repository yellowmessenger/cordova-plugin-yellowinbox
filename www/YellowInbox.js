var exec = require('cordova/exec');

const YellowInbox =
{
    initialize: (apiKey, userId, botId, success, failure) => {
        exec(success, failure, 'YellowInbox', 'initialize', [apiKey, userId, botId]);
    },

    setFirebaseDeviceToken: (token) => {
        exec(null, null, 'YellowInbox', 'setFirebaseDeviceToken', [token]);
    },

    handleBackgroundNotification: (data) => {
        exec(null, null, 'YellowInbox', 'handleBackgroundNotification', [data]);
    },

    setLocalReceiver: (eventCallBack, failureCallBack) => {
        exec(eventCallBack, failureCallBack, 'YellowInbox', 'setLocalReceiver', []);
    },

    setUpdatedEvent: (event, eventType, success, failure) => {
        exec(success, failure, 'YellowInbox', 'setUpdatedEvent', [event]);
    },

    setAgentStatus: (status, success, failure) => {
        exec(success, failure, 'YellowInbox', 'setAgentStatus', [status]);
    },

    getAgentStatus: (success, failure) => {
        exec(success, failure, 'YellowInbox', 'getAgentStatus', []);
    },

    logout: (success, failure) => {
        exec(success, failure, 'YellowInbox', 'logout', []);
    },

    showOverviewScreen: (success, failure) => {
        exec(success, failure, 'YellowInbox', 'showOverviewScreen', []);
    },

    showMyChatScreen: (success, failure) => {
        exec(success, failure, 'YellowInbox', 'showMyChatScreen', []);
    },
}

module.exports = YellowInbox
