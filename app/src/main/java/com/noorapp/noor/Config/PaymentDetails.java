package com.noorapp.noor.Config;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.noorapp.noor.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {

    TextView txtID, txtAmount, txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        txtID = findViewById(R.id.txt1);
        txtAmount = findViewById(R.id.txt2);
        txtStatus = findViewById(R.id.txt3);
        Intent intent =getIntent();

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            txtID.setText(Integer.parseInt(response.getString("id")));
            txtID.setText(Integer.parseInt(response.getString("state")));
            txtID.setText(Integer.parseInt(response.getString(String.format("$%s",paymentAmount))));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
