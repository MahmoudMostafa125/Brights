package com.noorapp.noor;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;

import java.util.Locale;

public class HelpActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView txttitle;
    LinearLayout lincont, linus, linweb, linlan, linrefund, lincancel;

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(this);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        linrefund = findViewById(R.id.refund);
        lincancel = findViewById(R.id.cancel);
        linlan = findViewById(R.id.lan);
        imgBack = findViewById(R.id.back);
        lincont = findViewById(R.id.contentlin);
        linweb = findViewById(R.id.aboutid);
        txttitle = findViewById(R.id.toolbar_title);
        linus = findViewById(R.id.contus);
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
             /*   Intent intent = new Intent(getApplicationContext(), TermsActivity.class);
                startActivity(intent);*/
            }
        });
        linus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(), ContactUs_Activity.class);
                startActivity(intent);*/
            }
        });
        lincont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getApplicationContext(), TermsWebviewActivity.class);
                startActivity(intent);*/
            }
        });
        linlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale();
            }
        });


        linrefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        /*        Intent intent = new Intent(getApplicationContext(), RefundpolicyWebview.class);
                startActivity(intent);*/
            }
        });

        lincancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent(getApplicationContext(), CancellationpolicyWebview.class);
                startActivity(intent);*/
            }
        });
    }



    public void setLocale() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFragment = new Language();
        dialogFragment.show(this.getSupportFragmentManager(), "dialog");

    }

    public void mangandcom(View view) {
        String contact = "+00 60122338070"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = view.getContext().getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(HelpActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void customer(View view) {
        String contact = "+00 60122338067"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = view.getContext().getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(HelpActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void inquir(View view) {
        String contact = "+00 601139307194"; // use country code with your phone number
        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = view.getContext().getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(HelpActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void Chat(View view) {
        Intent intent = new Intent(this, Chat_Activity.class);
        startActivity(intent);
    }
}

