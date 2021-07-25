/*
* Notes: The @objc shows that this class & function should be exposed to Cordova.
*/

import YMSupport

@objc(YMPartner2) class YMPartner2 : CDVPlugin {
  @objc(coolMethod:) // Declare your function name.
  func coolMethod(command: CDVInvokedUrlCommand) { // write the function code.
    /* 
     * Always assume that the plugin will fail.
     * Even if in this example, it can't.
     */
    // Set the plugin result to fail.
    var pluginResult = CDVPluginResult (status: CDVCommandStatus_ERROR, messageAs: "The Plugin Failed");
    // Testing library
    //Login API call
    YMCore.shared.login(
        username: "priyank@yellow.ai",
        password: "P@$$9333172315",
        completion: {
            response in
            print("Hello")
            pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs:"Login called");
        }
            );
    
//    pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: YMCore.shared.isLoggednIn ? "user logged in" : "User not logged in");
    
    // Send the function result back to Cordova.
    
    // Set the plugin result to succeed.
//        pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: "The plugin succeeded");
    self.commandDelegate!.send(pluginResult, callbackId: command.callbackId);
  }

  @objc(YMPartner2) class YMPartner2 : CDVPlugin {
  @objc(coolMethod2:) // Declare your function name.
  func coolMethod2(command: CDVInvokedUrlCommand) { }
    
    func printLoginResponse (response: LoginResponseType) {
       print("hello");
   }
    
}

