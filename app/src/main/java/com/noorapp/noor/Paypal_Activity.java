package com.noorapp.noor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;

import java.util.Locale;

public class Paypal_Activity extends AppCompatActivity {
    private Context mContext;
    private static final int REQUEST_CODE = 1234;
    String Amount, Token = "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiIwOGQ4YWNjMGQ0M2I2YjAwODQ3MGZkNmZjY2JkMDY0YTA3NTY1NDZlYjVhYzM1MDU1NjIyNDI4OWVmNDA2ZmVjfGNsaWVudF9pZD1jbGllbnRfaWQkc2FuZGJveCQ0ZHByYmZjNnBoNTk1Y2NqXHUwMDI2Y3JlYXRlZF9hdD0yMDE5LTAyLTEyVDAwOjA4OjEzLjAzMTA3NzU0MyswMDAwXHUwMDI2bWVyY2hhbnRfaWQ9ZnBud3I0cGprNXAyM3I2dyIsImNvbmZpZ1VybCI6Imh0dHBzOi8vYXBpLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb206NDQzL21lcmNoYW50cy9mcG53cjRwams1cDIzcjZ3L2NsaWVudF9hcGkvdjEvY29uZmlndXJhdGlvbiIsImdyYXBoUUwiOnsidXJsIjoiaHR0cHM6Ly9wYXltZW50cy5zYW5kYm94LmJyYWludHJlZS1hcGkuY29tL2dyYXBocWwiLCJkYXRlIjoiMjAxOC0wNS0wOCJ9LCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzL2ZwbndyNHBqazVwMjNyNncvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vb3JpZ2luLWFuYWx5dGljcy1zYW5kLnNhbmRib3guYnJhaW50cmVlLWFwaS5jb20vZnBud3I0cGprNXAyM3I2dyJ9LCJ0aHJlZURTZWN1cmVFbmFibGVkIjpmYWxzZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoidGVzdCBmYWNpbGl0YXRvcidzIFRlc3QgU3RvcmUiLCJjbGllbnRJZCI6IkFRcFBYNmtVQWlnMXo0SElDTFdkZkZmMXgwUnpXRzVaZkltUks5ekxPNFFoZlA2MVVKUDVVbjB5R3RRVjRzUFYwcGRScjhCSnZ4aFZDM2VYIiwicHJpdmFjeVVybCI6Imh0dHBzOi8vZXhhbXBsZS5jb20iLCJ1c2VyQWdyZWVtZW50VXJsIjoiaHR0cHM6Ly9leGFtcGxlLmNvbSIsImJhc2VVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImFzc2V0c1VybCI6Imh0dHBzOi8vY2hlY2tvdXQucGF5cGFsLmNvbSIsImRpcmVjdEJhc2VVcmwiOm51bGwsImFsbG93SHR0cCI6dHJ1ZSwiZW52aXJvbm1lbnROb05ldHdvcmsiOmZhbHNlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6Ik1ZUiIsImN1cnJlbmN5SXNvQ29kZSI6Ik1ZUiJ9LCJtZXJjaGFudElkIjoiZnBud3I0cGprNXAyM3I2dyIsInZlbm1vIjoib2ZmIn0=";
    Button btn_pay;
    EditText edt_pay;

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_);
        mContext = this;
        btn_pay = findViewById(R.id.pay);
        edt_pay = findViewById(R.id.amountxt);

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBraintreeSubmit(v);
            }
        });

    }

    private void submitPayment() {
        /*DropInRequest dropInRequest = new DropInRequest().clientToken(Token);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);*/

    }

    public void onBraintreeSubmit(View v) {

       /* Call<PayTokenResponse> call = RetrofitClient.getInstance().getApi().PaymenttokenApi();
        call.enqueue(new Callback<PayTokenResponse>() {
            @Override
            public void onResponse(Call<PayTokenResponse> call, Response<PayTokenResponse> response) {
                DropInRequest dropInRequest = new DropInRequest().clientToken(response.body().getClientToken());
                startActivityForResult(dropInRequest.getIntent(Paypal_Activity.this), REQUEST_CODE);
               // Toast.makeText(Paypal_Activity.this, , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PayTokenResponse> call, Throwable t) {
                Log.e("error_retrofit", call.toString());
            }
        });*/

        ////////////////
      //  DropInRequest dropInRequest = new DropInRequest().clientToken(Token);
       // startActivityForResult(dropInRequest.getIntent(Paypal_Activity.this), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/*        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();
                Log.d("thatOKOK", "onActivityResult: " + strNonce);
                // use the result to update your UI and send the payment method nonce to your server
                //here i'll send ===> strNonce and Amount to the API
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.e("ErrororroHERe", String.valueOf(error));

            }
        }*/
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE){

            if (requestCode==RESULT_OK){
                DropInResult result =data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce=result.getPaymentMethodNonce();
                String strNonce =nonce.getNonce();
                if (!edt_pay.getText().toString().isEmpty()){
                    Amount=edt_pay.getText().toString();
                }
            }else if (requestCode==RESULT_CANCELED){
                Toast.makeText(this, "caaaaaanceled", Toast.LENGTH_SHORT).show();
            }else {
                Exception error=(Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.e("ErrororroHERe", String.valueOf(error));
            }
        }
    }*/
}
