package com.mswipesdk.cordova;

// The native Toast API
import android.widget.Toast;

// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.mswipetech.wisepad.sdk.view.MSAARHandlerActivity;
import com.mswipesdk.mswipe.cardsale.MswipeCardSaleActivity;
import com.mswipesdk.mswipe.voidsale.VoidSaleActivity;
import com.mswipesdk.mswipe.lasttransaction.LastTransactionActivity;
import com.mswipesdk.mswipe.changepassword.ChangePasswordView;
import com.mswipesdk.mswipe.deviceinfo.DeviceInfoActivity;
import android.Manifest;
import android.os.Build;
import android.content.Intent;
import android.app.Activity;
import android.util.Log;
import android.content.Context;

public class MswipeSDKPlugin extends CordovaPlugin {

    private static final String DURATION_LONG = "long";

    private static final String MS_CARDSALE_ACTIVITY_INTENT_ACTION = "mswipe.wisepad.sdk.CardSaleAction";
    private static final String MS_CASHATPOS_ACTIVITY_INTENT_ACTION = "mswipe.wisepad.sdk.CashAtPosAction";

    private static final String MS_SDK_CARDSALE_ACTIVITY_INTENT_ACTION = "com.mswipesdk.mswipe.cardsale.MswipeCardSaleActivity";
    private static final String MS_SDK_CASHATPOS_ACTIVITY_INTENT_ACTION = "com.mswipesdk.mswipe.cardsale.MswipeCardSaleActivity";

    private static final String MS_VOIDSALE_ACTIVITY_INTENT_ACTION = "com.mswipesdk.mswipe.voidsale.VoidSaleActivity";
    private static final String MS_VOIDSALE_WITH_OTP_ACTIVITY_INTENT_ACTION = "com.mswipesdk.mswipe.voidsale.VoidSaleActivity";
    private static final String MS_LASTTRANSACTION_ACTIVITY_INTENT_ACTION = "com.mswipesdk.mswipe.lasttransaction.LastTransactionActivity";
    private static final String MS_CHANGE_PASSWORD_ACTIVITY_INTENT_ACTION = "com.mswipesdk.mswipe.changepassword.ChangePasswordView";
    private static final String MS_DEVICEINFO_ACTIVITY_INTENT_ACTION = "com.mswipesdk.mswipe.deviceinfo.DeviceInfoActivity";

    private static final int MS_SDK_CARDSALE_ACTIVITY_REQUEST_CODE = 1001;
    private static final int MS_SDK_CASHATPOS_ACTIVITY_REQUEST_CODE = 1002;

    private static final int MS_CARDSALE_ACTIVITY_REQUEST_CODE = 1003;
    private static final int MS_CASHATPOS_ACTIVITY_REQUEST_CODE = 1004;

    private static final int MS_VOIDSALE_ACTIVITY_REQUEST_CODE = 1005;
    private static final int MS_VOIDSALE_WITH_OTP_ACTIVITY_REQUEST_CODE = 1006;
    private static final int MS_LASTTRANSACTION_ACTIVITY_REQUEST_CODE = 1007;
    private static final int MS_DEVICEINFO_ACTIVITY_REQUEST_CODE = 1008;
    private static final int MS_CHANGE_PASSWORD_ACTIVITY_REQUEST_CODE = 1009;

    private boolean IS_DEBUG_ON = true;

    private CallbackContext callbackContext;

    private JSONObject options;
    private String mAction = "";
    private Context context;


    @Override
  public boolean execute(String action, JSONArray args,
    final CallbackContext callbackContext) {

      this.callbackContext = callbackContext;
      context = cordova.getActivity().getApplicationContext();
      if(IS_DEBUG_ON)
      {
          Log.v("mswipesdkkk: ", "requestCode ");
          Log.v("mswipesdkkk: ", "Build.VERSION.SDK_INT " + Build.VERSION.SDK_INT);
          Log.v("mswipesdkkk: ", "action" + action);
      }

      mAction = action;

      if(mAction.length() == 0){
          callbackContext.error("\"" + action + "\" is not a recognized action.");
          return false;
      }


      try {
          options = args.getJSONObject(0);
      } catch (Exception e) {
          callbackContext.error("Error encountered: " + e.getMessage());
          return false;
      }

      if (Build.VERSION.SDK_INT >= 23) {

          String[] PERMISSIONS = {
              Manifest.permission.READ_PHONE_STATE,
              Manifest.permission.READ_EXTERNAL_STORAGE,
              Manifest.permission.WRITE_EXTERNAL_STORAGE,
              Manifest.permission.ACCESS_COARSE_LOCATION,
              "android.permission.BBPOS"};

          if(IS_DEBUG_ON)
            Log.v("mswipesdkkk", "PERMISSIONS " + PERMISSIONS);

          cordova.requestPermissions(this, 0, PERMISSIONS);

          if(IS_DEBUG_ON)
            Log.v("mswipesdkkk", "PERMISSIONS " + PERMISSIONS);
      }
      else{

          callAction();

      }

      return true;
    }

    private void callAction() {

        try {

            if(mAction.equals("cardsaleaar"))
            {
                cardSale(options);
            }
            else if(mAction.equals("cashatposaar"))
            {
                cashAtPOSSale(options);
            }
            else if(mAction.equals("cardsalenative"))
            {
                sdkcardSale(options);
            }
            else if(mAction.equals("cashatposnative"))
            {
                sdkcashAtPOSSale(options);
            }
            else if(mAction.equals("voidsale"))
            {
                voidsale(options);
            }
            else if(mAction.equals("voidsalewithotp"))
            {
                voidsalewithotp(options);
            }
            else if (mAction.equals("lasttransaction"))
            {
                processLastTransaction(options);
            }
            else if (mAction.equals("deviceinfo"))
            {
                getDeviceInfo(options);
            }

            else if (mAction.equals("changepassword"))
            {
                changePassword(options);
            }

        } catch (Exception e) {

            Log.v("mswipesdkkk", "Exception: " + e.getMessage());
            callbackContext.error("Error encountered: " + e.getMessage());
        }
    }


    @Override
    public void onRequestPermissionResult(int requestCode, String[] permissions,
                                          int[] grantResults) throws JSONException
    {
        if(IS_DEBUG_ON)
            Log.v("mswipesdkkk", "requestCode " + requestCode + " permissions " + permissions+" grantResults " + grantResults);

        try {

            callAction();

        } catch (Exception e) {
            callbackContext.error("Error encountered: " + e.getMessage());
        }
    }


    public void cardSale(final JSONObject options) throws Exception {

        if(IS_DEBUG_ON)
            Log.v("mswipesdkkk", "cardSale");

        final CordovaPlugin that = this;

        cordova.getThreadPool().execute(new Runnable() {
            public void run() {

                Intent intent = new Intent(context,MSAARHandlerActivity.class);
                intent.setType(MS_CARDSALE_ACTIVITY_INTENT_ACTION);
               /* Intent intent = new Intent();
                intent.setAction(MS_CARDSALE_ACTIVITY_INTENT_ACTION);*/

                if(IS_DEBUG_ON)
                    Log.v("mswipesdkkk", "production" + options.optBoolean("production"));

                intent.putExtra("username", options.optString("username") == null ? "" : options.optString("username"));
                intent.putExtra("password", options.optString("password") == null ? "" : options.optString("password"));
                intent.putExtra("production", options.optBoolean("production"));
                intent.putExtra("Amount", options.optString("amount") == null ? "" : options.optString("amount"));
                intent.putExtra("MobileNumber", options.optString("mobileNo") == null ? "" : options.optString("mobileNo"));
                intent.putExtra("Reciept", options.optString("requestId") == null ? "" : options.optString("requestId"));
                intent.putExtra("MailId", options.optString("mailId") == null ? "" : options.optString("mailId"));
                intent.putExtra("Notes", options.optString("notes") == null ? "" : options.optString("notes"));
                intent.putExtra("extra1", options.optString("extra1") == null ? "" : options.optString("extra1"));
                intent.putExtra("extra2", options.optString("extra2") == null ? "" : options.optString("extra2"));
                intent.putExtra("extra3", options.optString("extra3") == null ? "" : options.optString("extra3"));
                intent.putExtra("orientation", "auto");
                intent.putExtra("isSignatureRequired", options.optBoolean("isSignatureRequired"));
                intent.putExtra("isPrinterSupported", options.optBoolean("isPrinterSupported"));
                intent.putExtra("isPrintSignatureOnReceipt", options.optBoolean("isPrintSignatureOnReceipt"));
                intent.putExtra("title", "");

                intent.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());
                that.cordova.startActivityForResult(that, intent, MS_CARDSALE_ACTIVITY_REQUEST_CODE);
            }
        });
    }


    public void cashAtPOSSale(final JSONObject options) throws Exception {

        if(IS_DEBUG_ON)
            Log.v("mswipesdkkk", "cashAtPOSSale");

        final CordovaPlugin that = this;

        cordova.getThreadPool().execute(new Runnable() {
            public void run() {

                Intent intent = new Intent(context,MSAARHandlerActivity.class);
                intent.setType(MS_CASHATPOS_ACTIVITY_INTENT_ACTION);

                /*Intent intent = new Intent();
                intent.setAction(MS_CASHATPOS_ACTIVITY_INTENT_ACTION);*/

                if(IS_DEBUG_ON)
                    Log.v("mswipesdkkk", "production" + options.optBoolean("production"));

                intent.putExtra("username", options.optString("username") == null ? "" : options.optString("username"));
                intent.putExtra("password", options.optString("password") == null ? "" : options.optString("password"));
                intent.putExtra("production", options.optBoolean("production"));
                intent.putExtra("cashAmount", options.optString("cashAmt") == null ? "" : options.optString("cashAmt"));
                intent.putExtra("MobileNumber", options.optString("mobileNo") == null ? "" : options.optString("mobileNo"));
                intent.putExtra("Reciept", options.optString("requestId") == null ? "" : options.optString("requestId"));
                intent.putExtra("MailId", options.optString("mailId") == null ? "" : options.optString("mailId"));
                intent.putExtra("Notes", options.optString("notes") == null ? "" : options.optString("notes"));
                intent.putExtra("extra1", options.optString("extra1") == null ? "" : options.optString("extra1"));
                intent.putExtra("extra2", options.optString("extra2") == null ? "" : options.optString("extra2"));
                intent.putExtra("extra3", options.optString("extra3") == null ? "" : options.optString("extra3"));
                intent.putExtra("orientation", "auto");
                intent.putExtra("isSignatureRequired", options.optBoolean("isSignatureRequired"));
                intent.putExtra("isPrinterSupported", options.optBoolean("isPrinterSupported"));
                intent.putExtra("isPrintSignatureOnReceipt", options.optBoolean("isPrintSignatureOnReceipt"));
                intent.putExtra("title", "");

                intent.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());
                that.cordova.startActivityForResult(that, intent, MS_CASHATPOS_ACTIVITY_REQUEST_CODE);
            }
        });
    }


    public void sdkcardSale(final JSONObject options) throws Exception{

        if(IS_DEBUG_ON)
            Log.v("mswipesdkkk", "sdkcardSale");

        final CordovaPlugin that = this;

      cordova.getThreadPool().execute(new Runnable() {

          public void run() {

             /* Intent intent = new Intent();
              intent.setAction(MS_SDK_CARDSALE_ACTIVITY_INTENT_ACTION);*/

              Intent intent = new Intent(context,MswipeCardSaleActivity.class);
              intent.setType(MS_SDK_CARDSALE_ACTIVITY_INTENT_ACTION);

              if(IS_DEBUG_ON)
              {
              Log.v("mswipesdkkk", "production" + options.optBoolean("production"));
              Log.v("mswipesdkkk", "username" + options.optString("username"));
              Log.v("mswipesdkkk", "password" + options.optString("password"));
              Log.v("mswipesdkkk", "amount" + options.optString("amount"));
              Log.v("mswipesdkkk", "mobileNo" + options.optString("mobileNo"));
              Log.v("mswipesdkkk", "receipt" + options.optString("receipt"));
              Log.v("mswipesdkkk", "extra1" + options.optString("extra1"));
              Log.v("mswipesdkkk", "extra2" + options.optString("extra2"));
              Log.v("mswipesdkkk", "extra3" + options.optString("extra3"));
              }

              intent.putExtra("username", options.optString("username") == null ? "" : options.optString("username"));
              intent.putExtra("password", options.optString("password") == null ? "" : options.optString("password"));
              intent.putExtra("production", options.optBoolean("production"));
              intent.putExtra("Amount", options.optString("amount") == null ? "" : options.optString("amount"));
              intent.putExtra("MobileNumber", options.optString("mobileNo") == null ? "" : options.optString("mobileNo"));
              intent.putExtra("Reciept", options.optString("receipt") == null ? "" : options.optString("receipt"));
              intent.putExtra("MailId", options.optString("mailId") == null ? "" : options.optString("mailId"));
              intent.putExtra("Notes", options.optString("notes") == null ? "" : options.optString("notes"));
              intent.putExtra("title", options.optString("title") == null ? "" : options.optString("title"));
              intent.putExtra("extra1", options.optString("extra1") == null ? "" : options.optString("extra1"));
              intent.putExtra("extra2", options.optString("extra2") == null ? "" : options.optString("extra2"));
              intent.putExtra("extra3", options.optString("extra3") == null ? "" : options.optString("extra3"));
              intent.putExtra("orientation", "auto");
              intent.putExtra("isSignatureRequired", options.optBoolean("isSignatureRequired"));
              intent.putExtra("isPrinterSupported", options.optBoolean("isPrinterSupported"));
              intent.putExtra("isPrintSignatureOnReceipt", options.optBoolean("isPrintSignatureOnReceipt"));
              intent.putExtra("isSaleWithCash", false);

              intent.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());
              that.cordova.startActivityForResult(that, intent, MS_SDK_CARDSALE_ACTIVITY_REQUEST_CODE);
          }
      });
  }



    /**
     * Starts an intent to scan and decode a barcode.
     */
    public void sdkcashAtPOSSale(final JSONObject options) throws Exception{

        if(IS_DEBUG_ON)
            Log.v("mswipesdkkk","sdkcashAtPOSSale");

        final CordovaPlugin that = this;

        cordova.getThreadPool().execute(new Runnable() {
            public void run() {

               /* Intent intent = new Intent();
                intent.setAction(MS_SDK_CASHATPOS_ACTIVITY_INTENT_ACTION);*/

                Intent intent = new Intent(context,MswipeCardSaleActivity.class);
                intent.setType(MS_SDK_CASHATPOS_ACTIVITY_INTENT_ACTION);

                if(IS_DEBUG_ON)
                {
                    Log.v("mswipesdkkk", "production" + options.optBoolean("production"));
                    Log.v("mswipesdkkk", "username" + options.optString("username"));
                    Log.v("mswipesdkkk", "password" + options.optString("password"));
                    Log.v("mswipesdkkk", "cashAmount" + options.optString("cashAmount"));
                    Log.v("mswipesdkkk", "mobileNo" + options.optString("mobileNo"));
                    Log.v("mswipesdkkk", "receipt" + options.optString("receipt"));
                    Log.v("mswipesdkkk", "extra1" + options.optString("extra1"));
                    Log.v("mswipesdkkk", "extra2" + options.optString("extra2"));
                    Log.v("mswipesdkkk", "extra3" + options.optString("extra3"));
                }

                intent.putExtra("username", options.optString("username") == null ? "" : options.optString("username"));
                intent.putExtra("password", options.optString("password") == null ? "" : options.optString("password"));
                intent.putExtra("production", options.optBoolean("production"));
                intent.putExtra("cashAmount", options.optString("cashAmount") == null ? "" : options.optString("cashAmount"));
                intent.putExtra("MobileNumber", options.optString("mobileNo") == null ? "" : options.optString("mobileNo"));
                intent.putExtra("Reciept", options.optString("receipt") == null ? "" : options.optString("receipt"));
                intent.putExtra("MailId", options.optString("mailId") == null ? "" : options.optString("mailId"));
                intent.putExtra("Notes", options.optString("notes") == null ? "" : options.optString("notes"));
                intent.putExtra("title", options.optString("title") == null ? "" : options.optString("title"));
                intent.putExtra("extra1", options.optString("extra1") == null ? "" : options.optString("extra1"));
                intent.putExtra("extra2", options.optString("extra2") == null ? "" : options.optString("extra2"));
                intent.putExtra("extra3", options.optString("extra3") == null ? "" : options.optString("extra3"));
                intent.putExtra("orientation", "auto");
                intent.putExtra("isSignatureRequired", options.optBoolean("isSignatureRequired"));
                intent.putExtra("isPrinterSupported", options.optBoolean("isPrinterSupported"));
                intent.putExtra("isPrintSignatureOnReceipt", options.optBoolean("isPrintSignatureOnReceipt"));
                intent.putExtra("isSaleWithCash",true);

                intent.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());
                that.cordova.startActivityForResult(that, intent, MS_SDK_CASHATPOS_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void voidsale(final JSONObject options) throws Exception{

        if(IS_DEBUG_ON)
            Log.v("mswipesdkkk", "voidsale");

        final CordovaPlugin that = this;

        cordova.getThreadPool().execute(new Runnable() {
            public void run() {


                /*Intent intent = new Intent();
                intent.setAction(MS_VOIDSALE_ACTIVITY_INTENT_ACTION);*/

                Intent intent = new Intent(context,VoidSaleActivity.class);
                intent.setType(MS_VOIDSALE_ACTIVITY_INTENT_ACTION);

                if(IS_DEBUG_ON)
                {
                    Log.v("mswipesdkkk", "production" + options.optBoolean("production"));
                    Log.v("mswipesdkkk", "username" + options.optString("username"));
                    Log.v("mswipesdkkk", "password" + options.optString("password"));
                    Log.v("mswipesdkkk", "amount" + options.optString("amount"));
                    Log.v("mswipesdkkk", "lastdigits" + options.optString("lastfourdigits"));
                    Log.v("mswipesdkkk", "date" + options.optString("date"));
                }

                intent.putExtra("username", options.optString("username") == null ? "" : options.optString("username"));
                intent.putExtra("password", options.optString("password") == null ? "" : options.optString("password"));
                intent.putExtra("production", options.optBoolean("production"));
                intent.putExtra("Amount", options.optString("amount") == null ? "" : options.optString("amount"));
                intent.putExtra("LastDigits", options.optString("lastfourdigits") == null ? "" : options.optString("lastfourdigits"));
                intent.putExtra("Date", options.optString("date") == null ? "" : options.optString("date"));

                intent.putExtra("isVoidWithOTp", false);

                intent.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());
                that.cordova.startActivityForResult(that, intent, MS_VOIDSALE_ACTIVITY_REQUEST_CODE);
            }
        });
    }


    public void voidsalewithotp(final JSONObject options) throws Exception{

        Log.v("mswipesdkkk", "voidsale with otp");

        final CordovaPlugin that = this;

        cordova.getThreadPool().execute(new Runnable() {
            public void run() {


                /*Intent intent = new Intent();
                intent.setAction(MS_VOIDSALE_WITH_OTP_ACTIVITY_INTENT_ACTION);*/
                Intent intent = new Intent(context,VoidSaleActivity.class);
                intent.setType(MS_VOIDSALE_WITH_OTP_ACTIVITY_INTENT_ACTION);


                if(IS_DEBUG_ON)
                {
                    Log.v("mswipesdkkk", "production" + options.optBoolean("production"));
                    Log.v("mswipesdkkk", "username" + options.optString("username"));
                    Log.v("mswipesdkkk", "password" + options.optString("password"));
                    Log.v("mswipesdkkk", "amount" + options.optString("amount"));
                    Log.v("mswipesdkkk", "lastdigits" + options.optString("lastfourdigits"));
                    Log.v("mswipesdkkk", "date" + options.optString("date"));
                }

                intent.putExtra("username", options.optString("username") == null ? "" : options.optString("username"));
                intent.putExtra("password", options.optString("password") == null ? "" : options.optString("password"));
                intent.putExtra("production", options.optBoolean("production"));
                intent.putExtra("Amount", options.optString("amount") == null ? "" : options.optString("amount"));
                intent.putExtra("LastDigits", options.optString("lastfourdigits") == null ? "" : options.optString("lastfourdigits"));
                intent.putExtra("Date", options.optString("date") == null ? "" : options.optString("date"));

                intent.putExtra("isVoidWithOTp", true);

                intent.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());
                that.cordova.startActivityForResult(that, intent, MS_VOIDSALE_WITH_OTP_ACTIVITY_REQUEST_CODE);
            }
        });
    }



    public void processLastTransaction(final JSONObject options) throws Exception{

        if(IS_DEBUG_ON)
            Log.v("mswipesdkkk", "last transaction");

        final CordovaPlugin that = this;

        cordova.getThreadPool().execute(new Runnable() {
            public void run() {


                /*Intent intent = new Intent();
                intent.setAction(MS_LASTTRANSACTION_ACTIVITY_INTENT_ACTION);*/

                Intent intent = new Intent(context,LastTransactionActivity.class);
                intent.setType(MS_LASTTRANSACTION_ACTIVITY_INTENT_ACTION);

                if(IS_DEBUG_ON)
                {
                    Log.v("mswipesdkkk", "production" + options.optBoolean("production"));
                    Log.v("mswipesdkkk", "username" + options.optString("username"));
                    Log.v("mswipesdkkk", "password" + options.optString("password"));
                }

                intent.putExtra("username", options.optString("username") == null ? "" : options.optString("username"));
                intent.putExtra("password", options.optString("password") == null ? "" : options.optString("password"));
                intent.putExtra("production", options.optBoolean("production"));


                intent.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());
                that.cordova.startActivityForResult(that, intent, MS_LASTTRANSACTION_ACTIVITY_REQUEST_CODE);
            }
        });
    }


    public void getDeviceInfo(final JSONObject options) throws Exception{

        if(IS_DEBUG_ON)
            Log.v("mswipesdkkk", "device info");

        final CordovaPlugin that = this;

        cordova.getThreadPool().execute(new Runnable() {
            public void run() {


              /*  Intent intent = new Intent();
                intent.setAction(MS_DEVICEINFO_ACTIVITY_INTENT_ACTION);*/

                Intent intent = new Intent(context,DeviceInfoActivity.class);
                intent.setType(MS_DEVICEINFO_ACTIVITY_INTENT_ACTION);

                intent.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());
                that.cordova.startActivityForResult(that, intent, MS_DEVICEINFO_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void changePassword(final JSONObject options) throws Exception{

        if(IS_DEBUG_ON)
            Log.v("mswipesdkkk", "changepassword");

        final CordovaPlugin that = this;

        cordova.getThreadPool().execute(new Runnable() {
            public void run() {


               /* Intent intent = new Intent();
                intent.setAction(MS_CHANGE_PASSWORD_ACTIVITY_INTENT_ACTION);*/

                Intent intent = new Intent(context,ChangePasswordView.class);
                intent.setType(MS_CHANGE_PASSWORD_ACTIVITY_INTENT_ACTION);

                if(IS_DEBUG_ON)
                {
                    Log.v("mswipesdkkk", "production" + options.optBoolean("production"));
                    Log.v("mswipesdkkk", "username" + options.optString("username"));
                    Log.v("mswipesdkkk", "password" + options.optString("password"));
                }

                intent.putExtra("username", options.optString("username") == null ? "" : options.optString("username"));
                intent.putExtra("password", options.optString("password") == null ? "" : options.optString("password"));
                intent.putExtra("production", options.optBoolean("production"));


                intent.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());
                that.cordova.startActivityForResult(that, intent, MS_CHANGE_PASSWORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }



    /**
   * Called when the barcode scanner intent completes.
   *
   * @param requestCode The request code originally supplied to startActivityForResult(),
   *                       allowing you to identify who this result came from.
   * @param resultCode  The integer result code returned by the child activity through its setResult().
   * @param intent      An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
   */
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent)
  {
    if ((requestCode == MS_CARDSALE_ACTIVITY_REQUEST_CODE || requestCode == MS_CASHATPOS_ACTIVITY_REQUEST_CODE) ||
            (requestCode == MS_SDK_CARDSALE_ACTIVITY_REQUEST_CODE || requestCode == MS_SDK_CASHATPOS_ACTIVITY_REQUEST_CODE) && this.callbackContext != null)
    {
        if(IS_DEBUG_ON)
            Log.v("result code","result code"+resultCode);

        if(IS_DEBUG_ON)
            Log.v("status message","status message"+intent.getStringExtra("statusMessage"));

        JSONObject obj = new JSONObject();

        if (resultCode == Activity.RESULT_OK) {

            boolean status = intent.getBooleanExtra("status", false);
            String statusMessage = intent.getStringExtra("statusMessage");

            if(IS_DEBUG_ON)
                Log.v("card sale statusMessage","card sale statusMessage"+statusMessage);


            if(status){

                String rrno = intent.getStringExtra("RRNo");
                String authcode = intent.getStringExtra("AuthCode");
                String tvr = intent.getStringExtra("TVR");
                String tsi = intent.getStringExtra("TSI");
                String receiptdetail = intent.getStringExtra("receiptDetail");

                try {
                    obj.put("status", status);
                    obj.put("rrno",rrno);
                    obj.put("authcode",authcode);
                    obj.put("tvr",tvr);
                    obj.put("tsi",tsi);
                    obj.put("receiptdetail", receiptdetail);

                } catch (Exception e) {
                }
            }
            else{
                try {

                    String errNo = intent.getStringExtra("errNo");

                    if(errNo == null)
                        errNo = "0";

                    Log.v("cardsale errNo", errNo);

                    obj.put("status", status);
                    obj.put("statusMessage", statusMessage);
                    obj.put("errNo",errNo);

                } catch (Exception e) {
                    callbackContext.error("Error encountered: " + e.getMessage());
                }
            }

            this.callbackContext.success(obj);
        }
        else if (resultCode == Activity.RESULT_CANCELED) {

            String statusMessage = intent.getStringExtra("statusMessage");

            if(IS_DEBUG_ON)
                Log.v("card sale failed statusMessage","card sale failed statusMessage"+statusMessage);


            try {

                obj.put("status", false);
                obj.put("statusMessage", statusMessage);
                obj.put("errNo","0");

            } catch (Exception e) {
                callbackContext.error("Error encountered: " + e.getMessage());

            }

            this.callbackContext.success(obj);
        }
        else {

            try {
                obj.put("status", false);
                obj.put("statusMessage", "trancation cancelled");
                obj.put("errNo","0");
            } catch (Exception e) {
            }

            this.callbackContext.success(obj);
        }
    }

    else if (requestCode == MS_VOIDSALE_ACTIVITY_REQUEST_CODE || requestCode == MS_VOIDSALE_WITH_OTP_ACTIVITY_REQUEST_CODE && this.callbackContext != null)
    {

        JSONObject obj = new JSONObject();

        if(IS_DEBUG_ON)
            Log.v("void sale on activity result","");

        if (resultCode == Activity.RESULT_OK) {

            if(IS_DEBUG_ON)
                Log.v("void sale on activity result ok","");

            boolean status = intent.getBooleanExtra("status", false);
            String statusMessage = intent.getStringExtra("statusMessage");

            if (status) {

                String trxDate = intent.getStringExtra("trxDate");
                String stanId = intent.getStringExtra("stanId");
                String voucherNo = intent.getStringExtra("voucherNo");
                String cardNo = intent.getStringExtra("cardNo");
                String trxAmnt = intent.getStringExtra("trxAmnt");
                String authcode = intent.getStringExtra("authCode");
                String rrno = intent.getStringExtra("rrNo");


                try {
                    obj.put("status", status);
                    obj.put("trxDate", trxDate);
                    obj.put("stanId", stanId);
                    obj.put("voucherNo", voucherNo);
                    obj.put("cardNo", cardNo);
                    obj.put("trxAmnt", trxAmnt);
                    obj.put("authcode", authcode);
                    obj.put("rrno", rrno);

                } catch (Exception e) {
                }
                this.callbackContext.success(obj);

            }
            else{
                try {

                    obj.put("status", false);
                    obj.put("statusMessage", statusMessage);

                } catch (Exception e) {
                }

                this.callbackContext.success(obj);
            }

        }else {

            try {

                String statusMessage = intent.getStringExtra("statusMessage");

                if(statusMessage == null)
                    statusMessage = "";

                obj.put("status", false);
                obj.put("statusMessage", statusMessage);

            } catch (Exception e) {
            }

            this.callbackContext.success(obj);
        }
    }

    else if (requestCode == MS_LASTTRANSACTION_ACTIVITY_REQUEST_CODE  && this.callbackContext != null)
    {
        JSONObject obj = new JSONObject();

        if(IS_DEBUG_ON)
            Log.v("last transaction activity result","");

        try {

            if (resultCode == Activity.RESULT_OK)
            {
                obj.put("status", true);
                obj.put("statusMessage", "");

            }else {

                String statusMessage = intent.getStringExtra("statusMessage");

                if(statusMessage == null)
                    statusMessage = "";

                obj.put("status", false);
                obj.put("statusMessage", statusMessage);
            }

        } catch (Exception e) {
        }



        this.callbackContext.success(obj);
    }
    else if (requestCode == MS_DEVICEINFO_ACTIVITY_REQUEST_CODE  && this.callbackContext != null)
    {

        JSONObject obj = new JSONObject();

        if(IS_DEBUG_ON)
            Log.v("device info activity result","");

        try {

            obj.put("status", true);
            obj.put("statusMessage", "");
        } catch (Exception e) {
        }

        this.callbackContext.success(obj);

    }

    else if (requestCode == MS_CHANGE_PASSWORD_ACTIVITY_REQUEST_CODE  && this.callbackContext != null)
    {
        JSONObject obj = new JSONObject();

        if(IS_DEBUG_ON)
            Log.v("change password activity result","");

        try {

            if (resultCode == Activity.RESULT_OK)
            {
                obj.put("status", true);
                obj.put("statusMessage", "");

            }else {

                String statusMessage = intent.getStringExtra("statusMessage");

                if(statusMessage == null)
                    statusMessage = "";

                obj.put("status", false);
                obj.put("statusMessage", statusMessage);
            }

        } catch (Exception e) {
        }

        this.callbackContext.success(obj);
    }
  }
}