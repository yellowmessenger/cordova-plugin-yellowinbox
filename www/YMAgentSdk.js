var exec = require('cordova/exec');

const AgentSdk =
{
    initialize: (success, failure, apiKey, userId, botId) => {
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

    setUpdatedEvent: (success, failure, title, body, model, eventType) => {
        exec(success, failure, 'YMAgentSdk', 'setUpdatedEvent', [title, body, model, eventType]);
    },

    changeAgentStatus: (success, failure, status) => {
        exec(success, failure, 'YMAgentSdk', 'changeAgentStatus', [status]);
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
