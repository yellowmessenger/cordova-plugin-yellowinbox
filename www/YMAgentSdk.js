var exec = require('cordova/exec');

const AgentSdk =
{
    initialize: (apiKey, userId, botId, success, failure) => {
        exec(success, failure, 'YMAgentSdk', 'initialize', [apiKey, userId, botId]);
    },

    setFirebaseDeviceToken: (token) => {
        exec(null, null, 'YMAgentSdk', 'setFirebaseDeviceToken', [token]);
    },

    handleBackgroundNotification: (data) => {
        exec(null, null, 'YMAgentSdk', 'handleBackgroundNotification', [data]);
    },

    setLocalReceiver: (eventCallBack, failureCallBack) => {
        exec(eventCallBack, failureCallBack, 'YMAgentSdk', 'setLocalReceiver', []);
    },

    setUpdatedEvent: (title, body, model, eventType, success, failure) => {
        exec(success, failure, 'YMAgentSdk', 'setUpdatedEvent', [title, body, model, eventType]);
    },

    setAgentStatus: (status, success, failure) => {
        exec(success, failure, 'YMAgentSdk', 'setAgentStatus', [status]);
    },

    getAgentStatus: (success, failure) => {
        exec(success, failure, 'YMAgentSdk', 'getAgentStatus', []);
    },

    logout: (success, failure) => {
        exec(success, failure, 'YMAgentSdk', 'logout', []);
    },

    startOverviewScreen: (success, failure) => {
        exec(success, failure, 'YMAgentSdk', 'startOverviewActivity', []);
    },

    startMyChatScreen: (success, failure) => {
        exec(success, failure, 'YMAgentSdk', 'startMyChatActivity', []);
    },
}

module.exports = AgentSdk
