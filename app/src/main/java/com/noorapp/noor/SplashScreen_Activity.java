package com.noorapp.noor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class SplashScreen_Activity extends AppCompatActivity {
    private Context mContext;
    String value;
    ImageView splashImage;

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_);
        mContext = this;
        splashImage = findViewById(R.id.splash);

//        Log.e("thisvaluee", value);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
      /*  String x = prefs.getString("Lang", null);
        if (x.equals("ar")) {*/
           /* splashImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            splashImage.setBackgroundResource(R.drawable.splashar);
*/
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //double height = displayMetrics.heightPixels;
        double width = displayMetrics.widthPixels;

        Picasso.with(this)
                .load(R.drawable.logobright)
                .resize((int) (width*0.50), (int) (width*0.50))
                .centerInside()
                .into(splashImage);

        /*  } else {
         *//* splashImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            splashImage.setBackgroundResource(R.drawable.ns);*//*
            Picasso.with(this)
                    .load(R.drawable.ns)
                    .fit()
                    .centerInside()
                    .into(splashImage);
        }*/
        new Handler().postDelayed(new Runnable() {
            // Using handler with postDelayed called runnable run method

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                // SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF",Context.MODE_PRIVATE);
                value = prefs.getString("Lang", null);
               /* if (value.equals("en")){
                    splashImage.setBackgroundResource(R.drawable.splashar);
                }*/

                if (!prefs.getBoolean("first_time", false)) {
                    Intent i = new Intent(SplashScreen_Activity.this, Login_option_Activity.class);
                    startActivity(i);
                } else {
                    Intent x = new Intent(SplashScreen_Activity.this, MainActivity.class);
                    startActivity(x);
                }
                if (value != null) {
                    Locale myLocale = new Locale(value);
                    LanguageUtil.changeLanguageType(mContext, myLocale);
                   /* Locale myLocale = new Locale(value);
                    Log.e("sdsdsd", value);
                    Locale.setDefault(myLocale);
                    Configuration configuration = new Configuration();
                    configuration.locale = myLocale;
                    SplashScreen_Activity.this.getResources().updateConfiguration(configuration,
                            SplashScreen_Activity.this.getResources().getDisplayMetrics());*/

                    Constants.lang = value;
                } else {
                    //Locale defaultLocale = Resources.getSystem().getConfiguration().getLocales().get(0);
                    Locale myLocale = new Locale("en");
                    LanguageUtil.changeLanguageType(mContext, myLocale);
                    Constants.lang = "en";
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SplashScreen_Activity
                            .this);
                    SharedPreferences.Editor editors = prefs.edit();
                    editors.putString("Lang", "en");
                    editors.commit();
                }
                // close this activity
                finish();
            }
        }, 5 * 1000); // wait for 5 seconds
    }
}