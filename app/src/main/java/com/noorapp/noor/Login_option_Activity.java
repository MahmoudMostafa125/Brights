package com.noorapp.noor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class Login_option_Activity extends AppCompatActivity {
    private Context mContext;
    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======","attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option_);
        mContext = this;

    }
    public void getVisitor(View view) {
       /* SharedPreferences sharedPref = this.getSharedPreferences("MYPREF",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("visitor", "good");
        editor.commit();
        if (sharedPref.contains("visitor")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "no found", Toast.LENGTH_SHORT).show();
        }*/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("first_time", true);
        editor.commit();
        Intent intent =new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void reg(View view) {
        Intent intent =new Intent(this,SignUp_Activity.class);
        startActivity(intent);
    }

    public void loginEmail(View view) {
        Intent intent =new Intent(this,Login_Activity.class);
        startActivity(intent);
    }
    public void loginFace(View view) {
        Intent intent =new Intent(this,Face_login_Activity.class);
        startActivity(intent);
    }
    public void loginGoogle(View view) {
        Intent intent =new Intent(this,Google_Login_Activity.class);
        startActivity(intent);
    }


}
