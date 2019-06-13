#import "MswipeSDKPlugin.h"
#import "InstantiateViewControllerError.h"
#import <UIKit/UIKit.h>
#import "CardSaleViewController.h"
#import "TransactionData.h"

#import "ChangePinViewController.h"

@interface MswipeSDKPlugin (hidden)

-(UIViewController*) instantiateViewControllerWithName: (NSString*) name;
-(UIViewController*) tryInstantiateViewWithName: (NSString*) name;

@end

@implementation MswipeSDKPlugin

- (void)cardsaleaar:(CDVInvokedUrlCommand*)command {

    NSDictionary *dict = [[command arguments] objectAtIndex:0];

    NSString *username = dict[@"username"];
    NSString *password = dict[@"password"];
    bool production = [dict[@"production"] boolValue];
    NSString *amount = dict[@"amount"];
    NSString *mobileNo = dict[@"mobileNo"];
    NSString *receipt = dict[@"receipt"];
    NSString *mailId = dict[@"mailId"];
    NSString *notes = dict[@"notes"];
    NSString *title = dict[@"title"];
    NSString *extra1 = dict[@"extra1"];
    NSString *extra2 = dict[@"extra2"];
    NSString *extra3 = dict[@"extra3"];
    bool isSignatureRequired = [dict[@"isSignatureRequired"] boolValue];
    bool isPrinterSupported = [dict[@"isPrinterSupported"] boolValue];
    bool isPrintSignatureOnReceipt = [dict[@"isPrintSignatureOnReceipt"] boolValue];

    TransactionData* transactionData = [[TransactionData alloc] init];

    double baseAmount = [amount doubleValue];
    NSString* strAmount = [NSString stringWithFormat : @"%.2f",  baseAmount];

    [transactionData setMBaseAmount: strAmount];
    [transactionData setMTipAmount: @"0.00"];
    [transactionData setMTotAmount: strAmount];
    [transactionData setMReceipt: receipt];
    [transactionData setMPhoneNo: mobileNo];
    [transactionData setMEmail: mailId];
    [transactionData setMNotes:notes];
    [transactionData setMNoteOne: extra1];
    [transactionData setMNoteTwo: extra2];
    [transactionData setMNoteThree: extra3];

    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"CardSaleStoryBoard" bundle:nil];
    CardSaleViewController *viewController = [storyboard instantiateViewControllerWithIdentifier:@"CardSaleViewController"];

    viewController.mTransactionData = transactionData;
    viewController.mSaleCommandDelegate = self.commandDelegate;
    viewController.callbackId = command.callbackId;

    viewController.mUserName = username;
    viewController.mPassword = password;
    viewController.mTitle = title;

    if(production)
    {
        viewController.mIsProduction = true;
    }
    else{
        viewController.mIsProduction = false;
    }

    if(isSignatureRequired)
    {
        viewController.mIsSignatureRequired = true;
    }
    else{
        viewController.mIsSignatureRequired = false;
    }

    if(isPrinterSupported)
        {
            viewController.isPrinterSupported = true;
        }
        else{
            viewController.isPrinterSupported = false;
        }

        if(isPrintSignatureOnReceipt)
        {
            viewController.isPrintSignatureOnReceipt = true;
        }
        else{
            viewController.isPrintSignatureOnReceipt = false;
        }

    if ([self.viewController navigationController] != nil) {

        if ([self.viewController.navigationController.viewControllers count] > 1) {
            [self.viewController.navigationController popViewControllerAnimated: YES];
        }else{

            [self.viewController.navigationController pushViewController:viewController animated:YES];
        }
    }
    else{

        UINavigationController* nav = [[UINavigationController alloc] init];
        //nav.delegate = self;
        self.webView.window.rootViewController = nav;
        [nav pushViewController:self.viewController animated:NO];
        nav.hidesBarsOnSwipe  = NO;
        nav.hidesBarsOnTap = NO;
        nav.navigationBarHidden = YES;
        nav.delegate = nil;
        nav.toolbarHidden = YES;

        [self.viewController.navigationController pushViewController:viewController animated:YES];
    }
}

- (void)cashatposaar:(CDVInvokedUrlCommand*)command {

    NSDictionary *dict = [[command arguments] objectAtIndex:0];

    NSString *username = dict[@"username"];
    NSString *password = dict[@"password"];
    bool production = [dict[@"production"] boolValue];
    NSString *amount = dict[@"cashAmt"];
    NSString *mobileNo = dict[@"mobileNo"];
    NSString *receipt = dict[@"receipt"];
    NSString *mailId = dict[@"mailId"];
    NSString *notes = dict[@"notes"];
    NSString *title = dict[@"title"];
    NSString *extra1 = dict[@"extra1"];
    NSString *extra2 = dict[@"extra2"];
    NSString *extra3 = dict[@"extra3"];
    bool isSignatureRequired = [dict[@"isSignatureRequired"] boolValue];
    bool isPrinterSupported = [dict[@"isPrinterSupported"] boolValue];
    bool isPrintSignatureOnReceipt = [dict[@"isPrintSignatureOnReceipt"] boolValue];


    TransactionData* transactionData = [[TransactionData alloc] init];

    double baseAmount = [amount doubleValue];
    NSString* strAmount = [NSString stringWithFormat : @"%.2f",  baseAmount];

    [transactionData setMBaseAmount: strAmount];
    [transactionData setMTipAmount: @"0.00"];
    [transactionData setMTotAmount: strAmount];
    [transactionData setMReceipt: receipt];
    [transactionData setMPhoneNo: mobileNo];
    [transactionData setMEmail: mailId];
    [transactionData setMNotes:notes];
    [transactionData setMNoteOne: extra1];
    [transactionData setMNoteTwo: extra2];
    [transactionData setMNoteThree: extra3];

    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"CardSaleStoryBoard" bundle:nil];
    CardSaleViewController *viewController = [storyboard instantiateViewControllerWithIdentifier:@"CardSaleViewController"];

    viewController.mTransactionData = transactionData;
    viewController.mSaleCommandDelegate = self.commandDelegate;
    viewController.callbackId = command.callbackId;

    viewController.mUserName = username;
    viewController.mPassword = password;
    viewController.mIsSaleWithCash = YES;
    viewController.mTitle = title;

    if(production)
    {
        viewController.mIsProduction = true;
    }
    else{
        viewController.mIsProduction = false;
    }

    if(isSignatureRequired)
    {
        viewController.mIsSignatureRequired = true;
    }
    else{
        viewController.mIsSignatureRequired = false;
    }

    if(isPrinterSupported)
    {
        viewController.isPrinterSupported = true;
    }
    else{
        viewController.isPrinterSupported = false;
    }

    if(isPrintSignatureOnReceipt)
    {
        viewController.isPrintSignatureOnReceipt = true;
    }
    else{
        viewController.isPrintSignatureOnReceipt = false;
    }


    if ([self.viewController navigationController] != nil) {

        if ([self.viewController.navigationController.viewControllers count] > 1) {
            [self.viewController.navigationController popViewControllerAnimated: YES];
        }else{

            [self.viewController.navigationController pushViewController:viewController animated:YES];
        }
    }
    else{

        UINavigationController* nav = [[UINavigationController alloc] init];
        //nav.delegate = self;
        self.webView.window.rootViewController = nav;
        [nav pushViewController:self.viewController animated:NO];
        nav.hidesBarsOnSwipe  = NO;
        nav.hidesBarsOnTap = NO;
        nav.navigationBarHidden = YES;
        nav.delegate = nil;
        nav.toolbarHidden = YES;

        [self.viewController.navigationController pushViewController:viewController animated:YES];
    }
}

- (void)cardsalenative:(CDVInvokedUrlCommand*)command {

    NSDictionary *dict = [[command arguments] objectAtIndex:0];

    NSString *username = dict[@"username"];
    NSString *password = dict[@"password"];
    bool production = [dict[@"production"] boolValue];
    NSString *amount = dict[@"amount"];
    NSString *mobileNo = dict[@"mobileNo"];
    NSString *receipt = dict[@"receipt"];
    NSString *mailId = dict[@"mailId"];
    NSString *notes = dict[@"notes"];
    NSString *title = dict[@"title"];
    NSString *extra1 = dict[@"extra1"];
    NSString *extra2 = dict[@"extra2"];
    NSString *extra3 = dict[@"extra3"];
    bool isSignatureRequired = [dict[@"isSignatureRequired"] boolValue];
    bool isPrinterSupported = [dict[@"isPrinterSupported"] boolValue];
    bool isPrintSignatureOnReceipt = [dict[@"isPrintSignatureOnReceipt"] boolValue];

    TransactionData* transactionData = [[TransactionData alloc] init];

    double baseAmount = [amount doubleValue];
    NSString* strAmount = [NSString stringWithFormat : @"%.2f",  baseAmount];

    [transactionData setMBaseAmount: strAmount];
    [transactionData setMTipAmount: @"0.00"];
    [transactionData setMTotAmount: strAmount];
    [transactionData setMReceipt: receipt];
    [transactionData setMPhoneNo: mobileNo];
    [transactionData setMEmail: mailId];
    [transactionData setMNotes:notes];
    [transactionData setMNoteOne: extra1];
    [transactionData setMNoteTwo: extra2];
    [transactionData setMNoteThree: extra3];

    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"CardSaleStoryBoard" bundle:nil];
    CardSaleViewController *viewController = [storyboard instantiateViewControllerWithIdentifier:@"CardSaleViewController"];

    viewController.mTransactionData = transactionData;
    viewController.mSaleCommandDelegate = self.commandDelegate;
    viewController.callbackId = command.callbackId;

    viewController.mUserName = username;
    viewController.mPassword = password;
    viewController.mTitle = title;

    if(production)
    {
        viewController.mIsProduction = true;
    }
    else{
        viewController.mIsProduction = false;
    }

    if(isSignatureRequired)
    {
        viewController.mIsSignatureRequired = true;
    }
    else{
        viewController.mIsSignatureRequired = false;
    }

    if(isPrinterSupported)
        {
            viewController.isPrinterSupported = true;
        }
        else{
            viewController.isPrinterSupported = false;
        }

        if(isPrintSignatureOnReceipt)
        {
            viewController.isPrintSignatureOnReceipt = true;
        }
        else{
            viewController.isPrintSignatureOnReceipt = false;
        }

    if ([self.viewController navigationController] != nil) {

        if ([self.viewController.navigationController.viewControllers count] > 1) {
            [self.viewController.navigationController popViewControllerAnimated: YES];
        }else{

            [self.viewController.navigationController pushViewController:viewController animated:YES];
        }
    }
    else{

        UINavigationController* nav = [[UINavigationController alloc] init];
        //nav.delegate = self;
        self.webView.window.rootViewController = nav;
        [nav pushViewController:self.viewController animated:NO];
        nav.hidesBarsOnSwipe  = NO;
        nav.hidesBarsOnTap = NO;
        nav.navigationBarHidden = YES;
        nav.delegate = nil;
        nav.toolbarHidden = YES;

        [self.viewController.navigationController pushViewController:viewController animated:YES];
    }
}

- (void)cashatposnative:(CDVInvokedUrlCommand*)command {

    NSDictionary *dict = [[command arguments] objectAtIndex:0];

    NSString *username = dict[@"username"];
    NSString *password = dict[@"password"];
    bool production = [dict[@"production"] boolValue];
    NSString *amount = dict[@"cashAmount"];
    NSString *mobileNo = dict[@"mobileNo"];
    NSString *receipt = dict[@"receipt"];
    NSString *mailId = dict[@"mailId"];
    NSString *notes = dict[@"notes"];
    NSString *title = dict[@"title"];
    NSString *extra1 = dict[@"extra1"];
    NSString *extra2 = dict[@"extra2"];
    NSString *extra3 = dict[@"extra3"];
    bool isSignatureRequired = [dict[@"isSignatureRequired"] boolValue];
    bool isPrinterSupported = [dict[@"isPrinterSupported"] boolValue];
    bool isPrintSignatureOnReceipt = [dict[@"isPrintSignatureOnReceipt"] boolValue];


    TransactionData* transactionData = [[TransactionData alloc] init];

    double baseAmount = [amount doubleValue];
    NSString* strAmount = [NSString stringWithFormat : @"%.2f",  baseAmount];

    [transactionData setMBaseAmount: strAmount];
    [transactionData setMTipAmount: @"0.00"];
    [transactionData setMTotAmount: strAmount];
    [transactionData setMReceipt: receipt];
    [transactionData setMPhoneNo: mobileNo];
    [transactionData setMEmail: mailId];
    [transactionData setMNotes:notes];
    [transactionData setMNoteOne: extra1];
    [transactionData setMNoteTwo: extra2];
    [transactionData setMNoteThree: extra3];

    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"CardSaleStoryBoard" bundle:nil];
    CardSaleViewController *viewController = [storyboard instantiateViewControllerWithIdentifier:@"CardSaleViewController"];

    viewController.mTransactionData = transactionData;
    viewController.mSaleCommandDelegate = self.commandDelegate;
    viewController.callbackId = command.callbackId;

    viewController.mUserName = username;
    viewController.mPassword = password;
    viewController.mIsSaleWithCash = YES;
    viewController.mTitle = title;

    if(production)
    {
        viewController.mIsProduction = true;
    }
    else{
        viewController.mIsProduction = false;
    }

    if(isSignatureRequired)
    {
        viewController.mIsSignatureRequired = true;
    }
    else{
        viewController.mIsSignatureRequired = false;
    }

    if(isPrinterSupported)
    {
        viewController.isPrinterSupported = true;
    }
    else{
        viewController.isPrinterSupported = false;
    }

    if(isPrintSignatureOnReceipt)
    {
        viewController.isPrintSignatureOnReceipt = true;
    }
    else{
        viewController.isPrintSignatureOnReceipt = false;
    }


    if ([self.viewController navigationController] != nil) {

        if ([self.viewController.navigationController.viewControllers count] > 1) {
            [self.viewController.navigationController popViewControllerAnimated: YES];
        }else{

            [self.viewController.navigationController pushViewController:viewController animated:YES];
        }
    }
    else{

        UINavigationController* nav = [[UINavigationController alloc] init];
        //nav.delegate = self;
        self.webView.window.rootViewController = nav;
        [nav pushViewController:self.viewController animated:NO];
        nav.hidesBarsOnSwipe  = NO;
        nav.hidesBarsOnTap = NO;
        nav.navigationBarHidden = YES;
        nav.delegate = nil;
        nav.toolbarHidden = YES;

        [self.viewController.navigationController pushViewController:viewController animated:YES];
    }
}

- (void)changepassword:(CDVInvokedUrlCommand*)command {

    NSDictionary *dict = [[command arguments] objectAtIndex:0];

    NSString *username = dict[@"username"];
    NSString *password = dict[@"password"];
    bool production = [dict[@"production"] boolValue];

    UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"ChangePinViewControllerStoryBoard" bundle:nil];
    ChangePinViewController *viewController = [storyboard instantiateViewControllerWithIdentifier:@"ChangePinViewController"];

    viewController.mSaleCommandDelegate = self.commandDelegate;
    viewController.callbackId = command.callbackId;

    viewController.mUserName = username;
    viewController.mPassword = password;

    if(production)
    {
        viewController.mIsProduction = true;
    }
    else{
        viewController.mIsProduction = false;
    }


    if ([self.viewController navigationController] != nil) {

        if ([self.viewController.navigationController.viewControllers count] > 1) {
            [self.viewController.navigationController popViewControllerAnimated: YES];
        }else{

            [self.viewController.navigationController pushViewController:viewController animated:YES];
        }
    }
    else{

        UINavigationController* nav = [[UINavigationController alloc] init];
        //nav.delegate = self;
        self.webView.window.rootViewController = nav;
        [nav pushViewController:self.viewController animated:NO];
        nav.hidesBarsOnSwipe  = NO;
        nav.hidesBarsOnTap = NO;
        nav.navigationBarHidden = YES;
        nav.delegate = nil;
        nav.toolbarHidden = YES;

        [self.viewController.navigationController pushViewController:viewController animated:YES];
    }
}

- (UIViewController*) instantiateViewControllerWithName: (NSString*) name {

    if ([[NSBundle mainBundle] pathForResource:name ofType: @"nib"]) {
        return [[UIViewController  alloc] initWithNibName: name bundle: nil];
    }

    NSString* message = [[NSString alloc] initWithFormat:@"The ViewController: %@ was not found", name];
    @throw [[InstantiateViewControllerError alloc] initWithName: @"notFound" reason: message userInfo: nil];
}

- (UIViewController*) tryInstantiateViewWithName:(NSString *)name {

    @try {
        return [self instantiateViewControllerWithName: name];
    } @catch (InstantiateViewControllerError* e) {
        NSLog(@"%@", e.reason);
    }

    return nil;
}

- (void) raiseClassNameError {

    NSString* message = [[NSString alloc] initWithFormat:@"The UIViewController name is required when the project don't have a navigatioController. Please, pass a className by param in JS, like this: 'NativeView.show('MyUIViewController')"];
    @throw [[InstantiateViewControllerError alloc] initWithName: @"nameNotDefined" reason: message userInfo: nil];
}

@end
