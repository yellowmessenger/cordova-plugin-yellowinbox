var exec = require('cordova/exec');

const AgentSdk =
{
    initialize: (success, failure, apiKey, userId, botId) => {
        console.log(`Calling init apiKey: ${apiKey}, userId: ${userId}, botId: ${botId}`)
        exec(success, failure, 'YMAgentSdk', 'initialize', [apiKey, userId, botId]);
    },

    initializeInternal: (success, failure, apiKey, userId, botId, source) => {
        console.log(`Calling initInternal apiKey: ${apiKey}, userId: ${userId}, botId: ${botId}, source: ${source}`)
        exec(success, failure, 'YMAgentSdk', 'initializeInternal', [apiKey, userId, botId, source]);
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

    changeBot: (success, failure, botId, subscriptionId) => {
        exec(success, failure, 'YMAgentSdk', 'changeBot', [botId, subscriptionId]);
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
        console.log("Starting overview activity");
        exec(success, failure, 'YMAgentSdk', 'startOverviewActivity', []);
    },

    startMyChatScreen: (success, failure) => {
        exec(success, failure, 'YMAgentSdk', 'startMyChatActivity', []);
    },

    getAgents: (success, failure) => {
        exec(success, failure, 'YMAgentSdk', 'getAgents', []);
    }
}

module.exports = AgentSdk
