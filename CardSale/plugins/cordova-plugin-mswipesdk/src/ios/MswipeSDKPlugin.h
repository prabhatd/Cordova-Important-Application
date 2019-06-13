#import <Cordova/CDV.h>

@interface MswipeSDKPlugin : CDVPlugin

- (void)cardsaleaar:(CDVInvokedUrlCommand*)command;
- (void)cardsalenative:(CDVInvokedUrlCommand*)command;

- (void)cashatposaar:(CDVInvokedUrlCommand*)command;
- (void)cashatposnative:(CDVInvokedUrlCommand*)command;

- (void)changepassword:(CDVInvokedUrlCommand*)command;

@end