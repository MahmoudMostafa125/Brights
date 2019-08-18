package com.noorapp.noor;import android.content.Context;import android.content.Intent;import android.content.SharedPreferences;import android.os.Bundle;import android.preference.PreferenceManager;import android.support.v7.app.AppCompatActivity;import android.util.Log;import android.widget.Toast;import com.google.android.gms.auth.api.Auth;import com.google.android.gms.auth.api.signin.GoogleSignInAccount;import com.google.android.gms.auth.api.signin.GoogleSignInOptions;import com.google.android.gms.auth.api.signin.GoogleSignInResult;import com.google.android.gms.common.api.GoogleApiClient;import com.google.android.gms.tasks.OnSuccessListener;import com.google.firebase.iid.FirebaseInstanceId;import com.google.firebase.iid.InstanceIdResult;import com.noorapp.noor.Utility.internetConnection;import com.noorapp.noor.api.RetrofitClient;import com.noorapp.noor.models.LoginFaceModel.LoginFaceResponse;import com.noorapp.noor.models.LoginGoogleLogin.GoogleLoginResponse;import retrofit2.Call;import retrofit2.Callback;import retrofit2.Response;import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;public class Google_Login_Activity extends AppCompatActivity {    GoogleApiClient mGoogleApiClient;    int RC_SIGN_IN = 10;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_google__login_);        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)                .requestEmail()                .build();        mGoogleApiClient = new GoogleApiClient.Builder(this)                //.enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)                .build();        signIn();    }    private void signIn() {        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);        startActivityForResult(signInIntent, RC_SIGN_IN);    }    @Override    public void onActivityResult(int requestCode, int resultCode, Intent data) {        super.onActivityResult(requestCode, resultCode, data);        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);        if (requestCode == RC_SIGN_IN) {            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);            if (internetConnection.internetConnectionAvailable(1000)) {                handleSignInResult(result);            } else {                showAlertDialogNoInternetConnection(this.getBaseContext());                Toast.makeText(this,  R.string.offline, Toast.LENGTH_SHORT).show();            }            //////////////////////////////////////////////        }    }    private void handleSignInResult(GoogleSignInResult result) {        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());        if (result.isSuccess()) {            // Signed in successfully, show authenticated UI.            final GoogleSignInAccount acct = result.getSignInAccount();            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));            // updateUI(true);            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {                @Override                public void onSuccess(InstanceIdResult instanceIdResult) {                    String deviceToken = instanceIdResult.getToken();                    Call<GoogleLoginResponse> call = RetrofitClient.getInstance().getApi().                            userLoginGoogle(acct.getDisplayName(),                                    "85855285"/*acct.getIdToken() */,                                    "1",                                    deviceToken,                                    "mm@y.com"/*acct.getEmail()*/);                    call.enqueue(new Callback<GoogleLoginResponse>() {                        @Override                        public void onResponse(Call<GoogleLoginResponse> call, Response<GoogleLoginResponse> response) {                            if (response.body() != null && response.body().getResponse()) {                                SharedPreferences sharedPref = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);                                SharedPreferences.Editor editor = sharedPref.edit();                                editor.putString("user_name", response.body().getUser().getUsername());                                editor.putString("email", response.body().getUser().getEmail());                                editor.putString("api_token", response.body().getApiToken());                                editor.commit();                                Toast.makeText(Google_Login_Activity.this, "Login Successfully ", Toast.LENGTH_SHORT).show();                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Google_Login_Activity.this);                                SharedPreferences.Editor editors = prefs.edit();                                editors.putBoolean("first_time", true);                                editors.commit();                            }                        }                        @Override                        public void onFailure(Call<GoogleLoginResponse> call, Throwable t) {                            Log.e("fail", "FAILL");                        }                    });                }            });            ///////////////////////////////////////////////////            Toast.makeText(this, "Welcome " + acct.getDisplayName(), Toast.LENGTH_LONG).show();            Toast.makeText(this, "You Logged successfully", Toast.LENGTH_LONG).show();            Intent intent = new Intent(Google_Login_Activity.this, MainActivity.class);            startActivity(intent);        } else {            // Signed out, show unauthenticated UI.            //updateUI(false);            Toast.makeText(this, "REJECTED , please try again", Toast.LENGTH_LONG).show();        }    }}