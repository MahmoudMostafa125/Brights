package com.noorapp.noor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.ContactusModel.ContactusResponse;
import com.noorapp.noor.models.LoginModel.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUs_Activity extends AppCompatActivity {

    EditText msg, mail;
    Button btn;
    String messagestr, emailstr;
    TextView toolbartitle;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_);
        imageView = (ImageView) findViewById(R.id.back);
        msg = findViewById(R.id.message);
        mail = findViewById(R.id.email);
        btn = findViewById(R.id.btnsend);
        toolbartitle = (TextView) findViewById(R.id.toolbar_title);
        toolbartitle.setText(R.string.contactus);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void sendMessage(View view) {
        messagestr = msg.getText().toString();
        emailstr = mail.getText().toString();
        Call<ContactusResponse> call = RetrofitClient.getInstance().getApi().ContactUsApi(messagestr, emailstr);
        call.enqueue(new Callback<ContactusResponse>() {
            @Override
            public void onResponse(Call<ContactusResponse> call, Response<ContactusResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getResponse().toString().equals("true")) {
                        Toast.makeText(ContactUs_Activity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                        msg.setText("");
                        mail.setText("");

                    } else {
                        Toast.makeText(ContactUs_Activity.this, "There is some thing wrong try again later", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(ContactUs_Activity.this, "pleases enter message", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContactusResponse> call, Throwable t) {
                Log.e("fail", "FAILL");
            }
        });

    }
}
