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

    setUpdatedEvent: (title, body, model, eventType, success, failure) => {
        exec(success, failure, 'YellowInbox', 'setUpdatedEvent', [title, body, model, eventType]);
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

    startOverviewScreen: (success, failure) => {
        exec(success, failure, 'YellowInbox', 'startOverviewScreen', []);
    },

    startMyChatScreen: (success, failure) => {
        exec(success, failure, 'YellowInbox', 'startMyChatScreen', []);
    },
}

module.exports = YellowInbox
