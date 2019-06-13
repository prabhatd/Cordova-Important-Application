// Empty constructor
function MswipeSDKPlugin() {}

// The function that passes work along to native shells

MswipeSDKPlugin.prototype.processCardSale = function() {

  console.log("prototype.processCardSale");

if(arguments.length == 15){

processARRCardSale(
    arguments[0], arguments[1], arguments[2], arguments[3], arguments[4],
    arguments[5], arguments[6], arguments[7], arguments[8], arguments[9],
    arguments[10], arguments[11], arguments[12], arguments[13], arguments[14]);

}
else if(arguments.length == 5){

    processSDKCardSale(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);

   }

}

function processARRCardSale(
username, password, production, amount, mobileNo, requestId, mailId, notes,
extra1, extra2, extra3, isSignatureRequired, isPrinterSupported, isPrintSignatureOnReceipt, onResponseCallBack) {

  console.log('processARRCardSale: ' + amount);

  var options = {};

  options.username = username;
  options.password = password;
  options.production = production;
  options.amount = amount;
  options.mobileNo = mobileNo;
  options.requestId = requestId;
  options.mailId = mailId;
  options.notes = notes;
  options.extra1 = extra1;
  options.extra2 = extra2;
  options.extra3 = extra3;
  options.isSignatureRequired = isSignatureRequired;
  options.isPrinterSupported = isPrinterSupported;
  options.isPrintSignatureOnReceipt = isPrintSignatureOnReceipt;

  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "cardsaleaar", [options]);

}

function processSDKCardSale(
username, password, production, requestData, onResponseCallBack) {

  console.log('processSDKCardSale: ' + username);

  var options = {};

  requestData.username = username;
  requestData.password = password;
  requestData.production = production;


  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "cardsalenative", [requestData]);

}


MswipeSDKPlugin.prototype.processCashAtPOS = function() {

   console.log("prototype.processCashAtPOS");

  if(arguments.length == 15){

  processARRCashAtPOS(
      arguments[0], arguments[1], arguments[2], arguments[3], arguments[4],
      arguments[5], arguments[6], arguments[7], arguments[8], arguments[9],
      arguments[10], arguments[11], arguments[12], arguments[13], arguments[14]);

  }
  else if(arguments.length == 5){

    processSDKCashAtPOS(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);

  }

}


function processARRCashAtPOS(
username, password, production, cashAmt, mobileNo, requestId, mailId, notes, extra1, extra2, extra3,
isSignatureRequired, isPrinterSupported, isPrintSignatureOnReceipt, onResponseCallBack) {

  console.log('processARRCashAtPOS: ' + cashAmt);


  var options = {};

  options.username = username;
  options.password = password;
  options.production = production;
  options.cashAmt = cashAmt;
  options.mobileNo = mobileNo;
  options.requestId = requestId;
  options.mailId = mailId;
  options.notes = notes;
  options.extra1 = extra1;
  options.extra2 = extra2;
  options.extra3 = extra3;
  options.isSignatureRequired = isSignatureRequired;
  options.isPrinterSupported = isPrinterSupported;
  options.isPrintSignatureOnReceipt = isPrintSignatureOnReceipt;

  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "cashatposaar", [options]);

}

function processSDKCashAtPOS(username, password, production, requestData, onResponseCallBack) {

  console.log('processSDKCashAtPOS: ' + username);

    var options = {};

  requestData.username = username;
  requestData.password = password;
  requestData.production = production;

  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "cashatposnative", [requestData]);

}


MswipeSDKPlugin.prototype.getLastTransactionDetails = function(
username, password, production, onResponseCallBack) {

        console.log('getLastTransactionDetails: ' + username);

  var options = {};

  options.username = username;
  options.password = password;
  options.production = production;

  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "lasttransaction", [options]);

}

MswipeSDKPlugin.prototype.processVoidSale = function(
username, password, production, date, amount, lastfourdigits, onResponseCallBack) {

        console.log('processVoidSale: ' + amount + '' + lastfourdigits);

  var options = {};

  options.username = username;
  options.password = password;
  options.production = production;
  options.date = date;
  options.amount = amount;
  options.lastfourdigits = lastfourdigits;

  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "voidsale", [options]);

}

MswipeSDKPlugin.prototype.processVoidSaleWithOTP = function(
username, password, production, date, amount, lastfourdigits, onResponseCallBack) {

        console.log('processVoidSaleWithOTP: ' + amount + '' + lastfourdigits);

  var options = {};

  options.username = username;
  options.password = password;
  options.production = production;
  options.date = date;
  options.amount = amount;
  options.lastfourdigits = lastfourdigits;

  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "voidsalewithotp", [options]);

}



MswipeSDKPlugin.prototype.getChangePassword = function(
username, password, production, onResponseCallBack) {

        console.log('getChangePassword: ' + username);

  var options = {};

  options.username = username;
  options.password = password;
  options.production = production;

  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "changepassword", [options]);

}


MswipeSDKPlugin.prototype.getDeviceInfo = function(onResponseCallBack) {

        console.log('getDeviceInfo: ');

  var options = {};


  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "deviceinfo", [options]);

}


// Installation constructor that binds MswipeSDKPlugin to window
MswipeSDKPlugin.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.MSCordovaController = new MswipeSDKPlugin();
  return window.plugins.MSCordovaController;
};
cordova.addConstructor(MswipeSDKPlugin.install);


/*
MswipeSDKPlugin.prototype.processCardSale = function(username, password, production,
                                                     amount, mobileNo, requestId, mailId, notes, extra1, extra2, extra3,
                                                      isSignatureRequired, isPrinterSupported, isPrintSignatureOnReceipt,
                                                     onResponseCallBack) {

        //console.log('processCardSale: ' + arguments.length);
    var options = {};

  options.username = username;
  options.password = password;
  options.production = production;
  options.amount = amount;
  options.mobileNo = mobileNo;
  options.requestId = requestId;
  options.mailId = mailId;
  options.notes = notes;
  options.extra1 = extra1;
  options.extra2 = extra2;
  options.extra3 = extra3;
  options.isSignatureRequired = isSignatureRequired;
  options.isPrinterSupported = isPrinterSupported;
  options.isPrintSignatureOnReceipt = isPrintSignatureOnReceipt;


  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "cardsale", [options]);


}


MswipeSDKPlugin.prototype.processSDKCardSale = function(username, password, production,
                                                        amount, mobileNo, requestId, mailId, notes, extra1, extra2, extra3,
                                                         isSignatureRequired, isPrinterSupported, isPrintSignatureOnReceipt,
                                                        onResponseCallBack) {

       // console.log('processCardSale: ' + arguments.length);
    var options = {};

  options.username = username;
  options.password = password;
  options.production = production;
  options.amount = amount;
  options.mobileNo = mobileNo;
  options.requestId = requestId;
  options.mailId = mailId;
  options.notes = notes;
  options.extra1 = extra1;
  options.extra2 = extra2;
  options.extra3 = extra3;
  options.isSignatureRequired = isSignatureRequired;
  options.isPrinterSupported = isPrinterSupported;
  options.isPrintSignatureOnReceipt = isPrintSignatureOnReceipt;

  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "sdkcardsale", [options]);


}

MswipeSDKPlugin.prototype.processCashAtPOS = function(
username, password, production,
cashAmt, mobileNo, requestId, mailId, notes, extra1, extra2, extra3,
 isSignatureRequired, isPrinterSupported, isPrintSignatureOnReceipt,
onResponseCallBack) {

  console.log("mswipesdkkk: prototype.processCashAtPOS");


  var options = {};

  options.username = username;
  options.password = password;
  options.production = production;
  options.cashAmt = cashAmt;
  options.mobileNo = mobileNo;
  options.requestId = requestId;
  options.mailId = mailId;
  options.notes = notes;
  options.extra1 = extra1;
  options.extra2 = extra2;
  options.extra3 = extra3;
  options.isSignatureRequired = isSignatureRequired;
  options.isPrinterSupported = isPrinterSupported;
  options.isPrintSignatureOnReceipt = isPrintSignatureOnReceipt;

  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "cashatpos", [options]);

}



MswipeSDKPlugin.prototype.processSDKCashAtPOS = function(
username, password, production,
cashAmt, mobileNo, receipt, mailId, notes, extra1, extra2, extra3,
 isSignatureRequired, isPrinterSupported, isPrintSignatureOnReceipt,
onResponseCallBack) {

        console.log('processsdkCashAtPOS: ' + cashAmt);

  var options = {};

  options.username = username;
  options.password = password;
  options.production = production;
  options.cashAmt = cashAmt;
  options.mobileNo = mobileNo;
  options.receipt = receipt;
  options.mailId = mailId;
  options.notes = notes;
  options.extra1 = extra1;
  options.extra2 = extra2;
  options.extra3 = extra3;
  options.isSignatureRequired = isSignatureRequired;
  options.isPrinterSupported = isPrinterSupported;
  options.isPrintSignatureOnReceipt = isPrintSignatureOnReceipt;

  cordova.exec(onResponseCallBack, onResponseCallBack, 'MswipeSDKPlugin', "sdkcashatpos", [options]);

}
*/