package com.noorapp.noor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;
import com.noorapp.noor.models.Reservation_Activity;

import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AboutusActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView txttitle;
    LinearLayout lincont,linus,linweb;

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        imgBack = findViewById(R.id.back);
        lincont = findViewById(R.id.contentlin);
        linweb = findViewById(R.id.aboutid);
        txttitle = findViewById(R.id.toolbar_title);
        linus= findViewById(R.id.contus);
        txttitle.setText(R.string.aboutnoor);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermsActivity.class);
                startActivity(intent);
            }
        });
        linus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactUs_Activity.class);
                startActivity(intent);
            }
        });
        lincont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TermsWebviewActivity.class);
                startActivity(intent);
            }
        });
    }
}
