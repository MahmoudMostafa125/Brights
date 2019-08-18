package com.noorapp.noor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.LoginFaceModel.LoginFaceResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;

public class Face_login_Activity extends AppCompatActivity {

    CallbackManager callbackManager;
    ProgressDialog mDialog;
    TextView textView;
    ImageView imageView;
    ProfilePictureView profilePictureView;
    int gendernum;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_login_);
        // printKeyHash();
        textView = (TextView) findViewById(R.id.textViewe);
        profilePictureView = findViewById(R.id.friendProfilePicture);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        mDialog = new ProgressDialog(Face_login_Activity.this);
                        mDialog.setMessage("Retrieving data .....");
                        mDialog.show();

                        String Accesstoken = loginResult.getAccessToken().getToken();
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.d("RRRC", response.toString());
                                getData(object);


                            }
                        });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,email,gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                        //  mDialog.dismiss();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(Face_login_Activity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(Face_login_Activity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        LoginManager.getInstance().logInWithReadPermissions(Face_login_Activity.this, Arrays.asList("public_profile", "email"));
    }

    private void getData(final JSONObject object) {
        try {
            Log.e("RRR", object.getString("email"));
            Log.e("RRR", "hhhhhhhhhh");
            if (object.getString("gender") == "male") {
                gendernum = 1;
            } else {
                gendernum = 0;
            }

            textView.setText(object.getString("email"));
            Log.e("tatata", object.getString("first_name"));
            profilePictureView.setProfileId(object.getString("id"));
            profilePictureView.setPresetSize(ProfilePictureView.LARGE);
            ////////////////////////////////////////////////
            if (internetConnection.internetConnectionAvailable(1000)) {

                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String deviceToken = instanceIdResult.getToken();
                        Call<LoginFaceResponse> call = null;
                        try {
                            call = RetrofitClient.getInstance().getApi().
                                    userLoginFacebook(object.getString("first_name"),
                                            "19989819198" /*object.getString("id")*/,
                                            "1" /* String.valueOf(gendernum)*/,
                                            deviceToken,
                                            "mahmp@yahoo.com" /*object.getString("email")*/);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        call.enqueue(new Callback<LoginFaceResponse>() {
                            @Override
                            public void onResponse(Call<LoginFaceResponse> call, Response<LoginFaceResponse> response) {
//                    if (response.body() != null) {
//                        if (response.body().getResponse()) {
                                if (response.body() != null && response.body().getResponse()) {
                                    SharedPreferences sharedPref = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("user_name", response.body().getUser().getUsername());
                                    editor.putString("email", response.body().getUser().getEmail());
                                    editor.putString("api_token", response.body().getApiToken());
                                    editor.commit();
                                    Toast.makeText(Face_login_Activity.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
                                    /////////////////////////////////////////////////////
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Face_login_Activity.this);
                                    SharedPreferences.Editor editors = prefs.edit();
                                    editors.putBoolean("first_time", true);
                                    editors.commit();
                                }
                                Intent intent = new Intent(Face_login_Activity.this, MainActivity.class);
                                startActivity(intent);

//                        } else {
//                            Toast.makeText(Face_login_Activity.this, "", Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Toast.makeText(Face_login_Activity.this, "there is something wrong Email or password wrong ", Toast.LENGTH_SHORT).show();
//                    }
                            }

                            @Override
                            public void onFailure(Call<LoginFaceResponse> call, Throwable t) {
                                Log.e("fail", "FAILL");
                            }
                        });
                    }
                });
            } else {
                showAlertDialogNoInternetConnection(this.getBaseContext());
                Toast.makeText(this,  R.string.offline, Toast.LENGTH_SHORT).show();
            }
            ////////////////////////////////////////////////////////


        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("RRR", "hhhhhhhhhh");
        }
    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.noorapp.noor", PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KEYHASH", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getFacebookProfilePicture(String userID) {
        URL imageURL = null;
        try {
            imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
