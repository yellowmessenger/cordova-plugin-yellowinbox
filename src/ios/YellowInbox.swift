
/*
 * Notes: The @objc shows that this class & function should be exposed to Cordova.
 */

import YMSupport

@objc(YMAgentSdk) public class YMAgentSdk : CDVPlugin {
    @objc(initializeYMAgentSdk:)
    public func initializeYMAgentSdk(_ command: CDVInvokedUrlCommand)
    {
        let apiKey:NSString = command.argument(at: 0) as! NSString
        let userId:NSString = command.argument(at: 0) as! NSString
        let botId:NSString = command.argument(at: 0) as! NSString
        
        self.genricSuccessResult(command)
    }
    @objc(setFirebaseDeviceToken:)
    public func setFirebaseDeviceToken(_ command: CDVInvokedUrlCommand)
    {
        let firebaseToken:NSString = command.argument(at:0) as! NSString
    }
    
    @objc(handleBackgroundNotification:)
    public func handleBackgroundNotification(_ command: CDVInvokedUrlCommand)
    {
        let token:NSString = command.argument(at:0) as! NSString
    }
    
    @objc(setLocalReceiver:)
    public func setLocalReceiver(_ command: CDVInvokedUrlCommand)
    {
        genricSuccessResult(command)
    }
    
    @objc(setUpdatedEvent:)
    public func setUpdatedEvent(_ command: CDVInvokedUrlCommand)
    {
        let title:NSString = command.argument(at: 0) as! NSString
        let body:NSString = command.argument(at: 0) as! NSString
        let model:NSDictionary = command.argument(at: 0) as! NSDictionary
        let eventType:NSString = command.argument(at: 0) as! NSString
        genricSuccessResult(command)
    }
    
    @objc(changeBot:)
    public func changeBot(_ command: CDVInvokedUrlCommand)
    {
        let botId:NSString = command.argument(at: 0) as! NSString
        let subscriptionId:NSString = command.argument(at: 0) as! NSString
        genricSuccessResult(command)
    }
    
    @objc(changeAgentStatus:)
    public func changeAgentStatus(_ command: CDVInvokedUrlCommand)
    {
        let agentStatus:NSString = command.argument(at: 0) as! NSString
        genricSuccessResult(command)
    }
    
    @objc(getAgentStatus:)
    public func getAgentStatus(_ command: CDVInvokedUrlCommand)
    {
        genricSuccessResult(command)
    }
    
    @objc(logout:)
    public func logout(_ command: CDVInvokedUrlCommand)
    {
        genricSuccessResult(command)
    }
    
    @objc(startMyChatScreen:)
    public func startMyChatScreen(_ command: CDVInvokedUrlCommand)
    {
        genricSuccessResult(command)
    }
    
    @objc(getAgents:)
    public func getAgents(_ command: CDVInvokedUrlCommand)
    {
        genricSuccessResult(command)
    }
    
    @objc(genricSuccessResult:)
    private func genricSuccessResult(_ command: CDVInvokedUrlCommand)
    {
        let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: "Not implemented yet")
        self.commandDelegate!.send(pluginResult, callbackId:command.callbackId)
    }
    
    @objc(genricErrrorResult:)
    private func genricErrrorResult(_ command: CDVInvokedUrlCommand)
    {
        let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR)
        self.commandDelegate!.send(pluginResult, callbackId:command.callbackId)
    }
}

