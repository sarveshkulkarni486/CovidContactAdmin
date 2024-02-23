package com.spam.maddy.covidcontactadmin.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.spam.maddy.covidcontactadmin.Model.Admin;
import com.spam.maddy.covidcontactadmin.Model.User;


public class Common {

    public static String topicName="News";

   public static Admin currentUser;

   public static User useruser;

  public static final int PICK_IMAGE_REQUEST=71;

    public static String restaurantSelected="";

    public static String PHONE_TEXT="userPhone";

    public  static  final String UPDATE ="Update";

    public static String DELETE="Delete";

    public static final String INTENT_FOOD_ID="FoodId";


    public static String USER_KEY="User";

    public static String PWD_KEY="password";

    public static String USER_TYPE_KEY="userTypeKey";

    public static final String BASE_URL="https://fcm.googleapis.com/";

    public static final String GOOGLE_API_URL="https://maps.googleapis.com/";



   /* public static IGoogleService getGoogleMapAPI(){
        return RetrofitClient.getGoogleClient(GOOGLE_API_URL).create(IGoogleService.class);

    }*/


    public static String convertCodeToStatus(String status) {

        if(status.equals("0"))
            return "Pending";
        else if (status.equals("1"))
            return "Accepted";
        else if (status.equals("2"))
            return "Work in Progress";
        else
            return "Drawing Completed";


    }

    public static String convertCodeToStatusPayment(String status) {

        if(status.equals("0"))
            return "Pending";
        else if (status.equals("1"))
            return "Verification Processing";
        else if (status.equals("2"))
            return "Payment Verified";
        else
            return "Payment Verification Failed.";

    }

    public static  boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo[] info=connectivityManager.getAllNetworkInfo();
            if (info != null){
                for (int i=0;i<info.length;i++){
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }


}
