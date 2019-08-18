package com.noorapp.noor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;
import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.RegisterModel.Register_response;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;
import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;
import static java.security.AccessController.getContext;

public class SignUp_Activity extends AppCompatActivity {
    private Context mContext;

    EditText txtName, txtEmail, txtMobile, txtPass;
    AwesomeValidation mAwesomeValidation;
    String stoken = "dfdfd", SerialNumber = "dfdfd", ModelNum = "dfdfd", versionnum = "dfdfd", deviceMan = "dfdfd", devidebrand = "dfdfd",
            deviceId = "dfdfd", deviceproduct = "dfdfd";
    TelephonyManager telephonyManager;

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);
        mContext = this;
        txtName = findViewById(R.id.name);
        txtEmail = findViewById(R.id.email);
        txtMobile = findViewById(R.id.mobile);
        txtPass = findViewById(R.id.pass);

        telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);
        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);

        mAwesomeValidation.addValidation(this, R.id.name, "[a-zA-Z\\s]+", R.string.err_name);
        mAwesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        mAwesomeValidation.addValidation(this, R.id.mobile, "[0-9]{5,}$", R.string.err_tel);
        mAwesomeValidation.addValidation(this, R.id.pass,
                "^.{6,}$",
                R.string.err_password);
        mAwesomeValidation.addValidation(this, R.id.re_pass, R.id.pass, R.string.err_password_confirmation);

        findViewById(R.id.regs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetConnection.internetConnectionAvailable(1000)) {
                    if (mAwesomeValidation.validate()) {
                    /*System.getProperty("os.version"); // OS version
                    Log.e("devicxe", android.os.Build.DEVICE);
                    Log.e("devicxe", android.os.Build.MODEL);
                    Log.e("devicxe", android.os.Build.PRODUCT);*/

                  /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        // Todo Don't forget to ask the permission
                        if (ActivityCompat.checkSelfPermission(SignUp_Activity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            return;
                        }
                        SerialNumber = Build.getSerial();

                        Log.e("new", SerialNumber);
                    } else {
                        SerialNumber = Build.SERIAL;
                        ModelNum = Build.MODEL;
                        versionnum = android.os.Build.VERSION.RELEASE;
                        deviceMan = android.os.Build.MANUFACTURER;
                        devidebrand = android.os.Build.MANUFACTURER;
                        deviceId = telephonyManager.getDeviceId();
                        deviceproduct = android.os.Build.MANUFACTURER;
                        Log.e("old", SerialNumber);
                    }*/
                        // fireToken();
//                    Log.e("tahttoken", stoken);


                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                            @Override
                            public void onSuccess(InstanceIdResult instanceIdResult) {
                                //get firebase token
                                String deviceToken = instanceIdResult.getToken();
                                stoken = deviceToken;
                                String mob;
                                if (txtMobile.getText().toString().substring(0, 1).equals(String.valueOf(0))) {
                                    mob = txtMobile.getText().toString().substring(1);
                                    Log.e("gomobi", mob);

                                } else {
                                    mob = txtMobile.getText().toString();
                                }
                                Log.e("Token", stoken);
                                Log.e("nummm", txtMobile.getText().toString().substring(0, 1));
                                Call<Register_response> call = RetrofitClient.getInstance().getApi().createUser(
                                        txtName.getText().toString(),
                                        txtEmail.getText().toString(),
                                        mob,
                                        txtPass.getText().toString(),
                                        stoken,
                                        ModelNum,
                                        SerialNumber,
                                        versionnum,
                                        deviceMan,
                                        devidebrand,
                                        deviceId,
                                        deviceproduct);

                                call.enqueue(new Callback<Register_response>() {
                                    @Override
                                    public void onResponse(Call<Register_response> call, Response<Register_response> response) {
                                        if (response.body() != null) {
                                            if (response.body().getResponse()) {
                                                SharedPreferences sharedPref = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPref.edit();
                                                editor.putString("user_name", response.body().getUser().getUsername());
                                                editor.putString("email", response.body().getUser().getEmail());
                                                editor.putString("phone", response.body().getUser().getPhone());
                                                editor.putString("api_token", response.body().getApiToken());
                                                editor.commit();
                                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SignUp_Activity.this);
                                                SharedPreferences.Editor editors = prefs.edit();
                                                editors.putBoolean("first_time", true);
                                                editors.commit();
                                                Toast.makeText(SignUp_Activity.this, "YOU REGISTER SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SignUp_Activity.this, MainActivity.class);
                                                startActivity(intent);

                                            } else {
                                                Toast.makeText(SignUp_Activity.this, response.body().getMessage().getErrorInfo().get(3), Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(SignUp_Activity.this, "there is something wrong Email or Phone " +
                                                    "Exists try new Email or phone number", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Register_response> call, Throwable t) {
                                        Log.e("fail", "FAILL");
                                    }
                                });
                                ///////////////////////////////////
                            }
                        });

                    }
                    //   Toast.makeText(SignUp_Activity.this, "successfully registered" + stoken, Toast.LENGTH_SHORT).show();

                } else {
                    showAlertDialogNoInternetConnection(v.getContext());
                    Toast.makeText(v.getContext(),  R.string.offline, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void log(View view) {
        Intent intent = new Intent(this, Login_option_Activity.class);
        startActivity(intent);
    }

    public void fireToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String deviceToken = instanceIdResult.getToken();
                // Do whatever you want with your token now
                // i.e. store it on SharedPreferences or DB
                // or directly send it to server
                stoken = deviceToken;
                // Log.e("hatokenns", deviceToken);
            }
        });
    }

}
