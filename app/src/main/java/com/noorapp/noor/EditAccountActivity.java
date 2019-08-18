package com.noorapp.noor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.ContactusModel.ContactusResponse;
import com.noorapp.noor.models.ProfileModel.ProfileResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;
import static com.facebook.FacebookSdk.getApplicationContext;

public class EditAccountActivity extends AppCompatActivity {

    String name, email, phone, token;
    EditText txtname, txtphone;
    TextView txtemail;
    Button btn, imgEdit;
    AwesomeValidation mAwesomeValidation;
    TextView txttitle;
    ImageView imgBack;
    String imgBase64;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        name = sharedPref.getString("user_name", null);
        email = sharedPref.getString("email", null);
        phone = sharedPref.getString("phone", null);
        token = sharedPref.getString("api_token", null);
        txttitle = findViewById(R.id.toolbar_title);
        txttitle.setText(R.string.account);
        txtname = findViewById(R.id.name);
        txtphone = findViewById(R.id.mobileid);
        txtemail = findViewById(R.id.emailid);
        btn = findViewById(R.id.button);
        imgEdit = findViewById(R.id.editimg);

        txtname.setText(name);
        txtphone.setText(phone);
        txtemail.setText(email);
        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);
        mAwesomeValidation.addValidation(this, R.id.mobileid, "[0-9]{5,}$", R.string.err_tel);
        mAwesomeValidation.addValidation(this, R.id.name, "[a-zA-Z\\s]+", R.string.err_name);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAwesomeValidation.validate()) {

                    Call<ContactusResponse> call = RetrofitClient.getInstance()
                            .getApi().EditProfileApi(token, String.valueOf(txtphone.getText()), imgBase64, String.valueOf(txtname.getText()));
                    call.enqueue(new Callback<ContactusResponse>() {
                        @Override
                        public void onResponse(Call<ContactusResponse> call, Response<ContactusResponse> response) {

                            if (response.body() != null) {
                                if (response.body().getResponse()) {
                                    SharedPreferences sharedPref = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("user_name", String.valueOf(txtname.getText()));
                                    editor.putString("phone", String.valueOf(txtphone.getText()));

                                    editor.commit();

                                    Toast.makeText(EditAccountActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(getIntent());
                                    finish();

                                } else {
                                    Toast.makeText(EditAccountActivity.this, "there is some thing wrong try again later", Toast.LENGTH_SHORT).show();
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<ContactusResponse> call, Throwable t) {
                            Log.e("fail", "FAILL");
                        }
                    });

                }

            }
        });
        imgBack = findViewById(R.id.back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imgBase64 = toStringImage(bitmap);
                Log.e("sds",imgBase64);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String toStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
