package com.noorapp.noor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;
import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.LoginModel.LoginResponse;
import com.noorapp.noor.models.RegisterModel.Register_response;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;
import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;

public class Login_Activity extends AppCompatActivity {
    private Context mContext;
    EditText txtPass, txtEmail;
    AwesomeValidation mAwesomeValidation;
    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======","attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        mContext = this;
        txtEmail = findViewById(R.id.email);
        txtPass = findViewById(R.id.password);
        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);

        mAwesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        mAwesomeValidation.addValidation(this, R.id.password,
                "^.{6,}$",
                R.string.err_password);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetConnection.internetConnectionAvailable(1000)) {

                    if (mAwesomeValidation.validate()) {
                    Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin(txtEmail.getText().toString(),
                            txtPass.getText().toString());
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.body() != null) {
                                if (response.body().getResponse()) {
                                    SharedPreferences sharedPref = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("user_name", response.body().getUser().getUsername());
                                    editor.putString("email", response.body().getUser().getEmail());
                                    editor.putString("phone", response.body().getUser().getPhone());
                                    editor.putString("api_token", response.body().getApiToken());
                                    editor.putString("photo", response.body().getUser().getAvatar());
                                    editor.commit();
                                    Toast.makeText(Login_Activity.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Login_Activity.this);
                                    SharedPreferences.Editor editors = prefs.edit();
                                    editors.putBoolean("first_time", true);
                                    editors.commit();
                                    Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(Login_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Login_Activity.this, "there is something wrong Email or password wrong ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.e("fail", "FAILL");
                        }
                    });
                }
                } else {
                    showAlertDialogNoInternetConnection(v.getContext());
                    Toast.makeText(v.getContext(),  R.string.offline, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void regNow(View view) {
        Intent intent = new Intent(this, SignUp_Activity.class);
        startActivity(intent);
    }
}
