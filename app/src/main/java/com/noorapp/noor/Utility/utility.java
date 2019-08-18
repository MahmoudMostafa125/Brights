package com.noorapp.noor.Utility;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

public class utility {
    public static Boolean is_gps_permission = false;
    public static final int GPSPermission_id = 1;
    static Activity activity;
    public static void showAlertDialogNoInternetConnection(Context cox) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(cox);
        builder.setTitle("You Are Offline");
        builder.setMessage("You are not connected to the internet ,check your connection !!!");
        // add a button
        builder.setPositiveButton("OK", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static void  GPSpermission(final Context context){
        activity = (Activity) context;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!activity.shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION )) {
                        activity.requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},GPSPermission_id);
                    }else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                            android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                            alertBuilder.setCancelable(true);
                            alertBuilder.setTitle("Permission necessary");
                            alertBuilder.setMessage("GPS permission is necessary");
                            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                            GPSPermission_id);
                                }
                            });
                            android.support.v7.app.AlertDialog alert = alertBuilder.create();
                            alert.show();
                        }
                    }
                }
                return;
            }else{
                is_gps_permission = true;
            }
        }else{
            is_gps_permission = true;
        }

    }

    public void setApplicationLanguage(String newLanguage) {
        Resources activityRes = getApplicationContext().getResources();
        Configuration activityConf = activityRes.getConfiguration();
        Locale newLocale = new Locale(newLanguage);
        activityConf.setLocale(newLocale);
        activityRes.updateConfiguration(activityConf, activityRes.getDisplayMetrics());

        Resources applicationRes = getApplicationContext().getResources();
        Configuration applicationConf = applicationRes.getConfiguration();
        applicationConf.setLocale(newLocale);
        applicationRes.updateConfiguration(applicationConf,
                applicationRes.getDisplayMetrics());
    }

    public static void OpenGps(final Context cox) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(cox);
        builder.setTitle("GPS IS OFF");
        builder.setMessage("Please Open GPS to get the nearest places");
        builder.setCancelable(true);
        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                cox.startActivity(intent);
            }
        });

       /* builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
