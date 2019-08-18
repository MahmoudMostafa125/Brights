package com.noorapp.noor;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.AboutusModel.AboutResponse;
import com.noorapp.noor.models.ReservationsModel.ReservationsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView txtContent;
    ProgressBar pcont;
    TextView txttitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        imgBack = findViewById(R.id.back);
        txtContent = findViewById(R.id.content);
        txtContent.setMovementMethod(new ScrollingMovementMethod());
        txttitle = findViewById(R.id.toolbar_title);
        txttitle.setText(R.string.about_us);
        pcont = findViewById(R.id.contentprogress);
        pcont.setVisibility(View.VISIBLE);

        Call<List<AboutResponse>> call = RetrofitClient.getInstance().getApi().
                AboutUsApi(Constants.lang);
        call.enqueue(new Callback<List<AboutResponse>>() {
            @Override
            public void onResponse(Call<List<AboutResponse>> call, Response<List<AboutResponse>> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isEmpty()) {
                        String cont = response.body().get(0).getTranslations().get(0).getValue();
                        txtContent.setText(cont);
                    }
                   /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        txtContent.setText(Html.fromHtml(cont, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        txtContent.setText(Html.fromHtml(cont));
                    }*/
                }else{
                    Toast.makeText(TermsActivity.this, "@string/there_is_no_data", Toast.LENGTH_SHORT).show();
                }
                pcont.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<AboutResponse>> call, Throwable t) {
                Log.e("error_retrofit", call.toString());
                t.printStackTrace();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
