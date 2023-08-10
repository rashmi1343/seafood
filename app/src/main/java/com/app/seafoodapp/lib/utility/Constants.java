package com.app.seafoodapp.lib.utility;

public class Constants {
	public static final String PARAMETER_SEP = "&";
	public static final String PARAMETER_EQUALS = "=";
	//public static final String TRANS_URL = "https://secure.ccavenue.com/transaction/initTrans";
	public static final String ORDER_ID = "10";
	public static final String AMOUNT = "1.00";
	public static final String CURRENCY = "INR";
	public static final String ENC_VAL = "enc_val";

	// test details for payment gateway

	/*public static final String TRANS_URL = "https://test.ccavenue.com/transaction/initTrans";
	public static final String ACCESS_CODE = "AVSN88GJ23AW76NSWA";
	public static final String MERCHANT_ID = "235096";
	public static final String RSA_KEY_URL = "https://www.indianseafoodexpo.com/beta2020/mobileTest/GetRSA_Android.php"*/;



	//live detail for payment gateway

	public static final String TRANS_URL = "https://secure.ccavenue.com/transaction/initTrans";
	//public static final String ACCESS_CODE_DELEGATE = "AVYQ89GL35BC60QYCB";  // android
    public static final String ACCESS_CODE_DELEGATE = "AVKE89HA64AB98EKBA"; // ios
	public static final String ACCESS_CODE_STALL = "AVMR89GL55CC29RMCC";
	//public static final String MERCHANT_ID_delegate = "241674";     // android
	public static final String MERCHANT_ID_delegate = "243333";  // ios
	public static final String RSA_KEY_DELEGATE_URL = "https://www.indianseafoodexpo.com/apppg/GetRSA_Android_delegate.php";
	public static final String RSA_KEY_STALL_URL = "https://www.indianseafoodexpo.com/apppg/GetRSA_Android_stall.php";
	public static final String MERCHANT_ID_stall = "242787";

	/* Redirect url for delegate test */

	/*public static final String REDIRECT_URL_delegate = "https://www.indianseafoodexpo.com/beta2020/mobileTest/ccavResponseHandler_Android.php";
	public static final String CANCEL_URL_delegate = "https://www.indianseafoodexpo.com/beta2020/mobileTest/ccavResponseHandler_Android.php";*/



	/* Redirect url for delegate live */
	public static final String REDIRECT_URL_delegate = "https://www.indianseafoodexpo.com/apppg/ccavResponseHandler_Android.php";
	public static final String CANCEL_URL_delegate = "https://www.indianseafoodexpo.com/apppg/ccavResponseHandler_Android.php";





	/* Redirect url for stall registration  test */

//	public static final String REDIRECT_URL_stall = "https://www.indianseafoodexpo.com/beta2020/mobileTest/responsehandlerstallbooked_Android.php";
//	public static final String CANCEL_URL_stall = "https://www.indianseafoodexpo.com/beta2020/mobileTest/responsehandlerstallbooked_Android.php";


	/* Redirect url for stall registration  live */
	public static final String REDIRECT_URL_stall = "https://www.indianseafoodexpo.com/apppg/responsehandlerstallbooked_Android.php";
	public static final String CANCEL_URL_stall = "https://www.indianseafoodexpo.com/apppg/responsehandlerstallbooked_Android.php";





	public static  int Indiangstvalue = 2250;
	public static int Indianmoney = 12500;



	public static   double forgstfee = 0.18;
	public static double foreginregfee = 180.0;

}




