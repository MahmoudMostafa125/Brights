package com.noorapp.noor;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Language extends DialogFragment {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;
    private Context mContext;

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_language, container, false);

        mContext = view.getContext();
        //addListenerOnButton(view);
        //onRadioButtonClicked(view);
        radioGroup = view.findViewById(R.id.Rg);
        btnDisplay = view.findViewById(R.id.btng);
        addListenerOnButton(view);
        RadioButton btnAR = view.findViewById(R.id.Rb1);
        RadioButton btnEN = view.findViewById(R.id.Rb2);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String value = prefs.getString("Lang", null);
        if (value.equals("ar")) {
            btnAR.setChecked(true);
        } else {
            btnEN.setChecked(true);
        }
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;

    }
    /*public void onRadioButtonClicked(final View view) {
        // Is the button now checked?
        final boolean checked = ((RadioButton) view).isChecked();
        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch(v.getId()) {
                    case R.id.Rb1:
                        if (checked){
                            Toast.makeText(v.getContext(),
                                    "You choose Arabic", Toast.LENGTH_SHORT).show();
                        }
                        // Pirates are the best
                        break;
                    case R.id.Rb2:
                        if (checked){
                            Toast.makeText(v.getContext(),
                                    "You choose English", Toast.LENGTH_SHORT).show();
                        }
                        // Ninjas rule
                        break;
                }
            }

        });
        // Check which radio button was clicked

    }*/


    public void addListenerOnButton(View view) {


        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                int id = radioGroup.getCheckedRadioButtonId();
                if (id != -1) {
                    //get radio button view
                    View radioButton = radioGroup.findViewById(id);
                    // return index of selected radiobutton
                    int radioId = radioGroup.indexOfChild(radioButton);
                    // based on index getObject of radioButton
                    RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
                    //After getting radiobutton you can now use all its methods
                    String selection = (String) btn.getText();
                    Toast.makeText(v.getContext(), selection, Toast.LENGTH_SHORT).show();
                    if (selection.equals("Arabic") || selection.equals("العربية")) {

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                        SharedPreferences.Editor editors = prefs.edit();
                        editors.putString("Lang", "ar");
                        editors.commit();
                        ///////////////////////////////
                        Locale myLocale = new Locale("ar");
                        LanguageUtil.changeLanguageType(mContext, myLocale);
                        Constants.lang = "ar";
                        //////////////////////////////////
                        Intent i = v.getContext().getPackageManager().getLaunchIntentForPackage(v.getContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        getActivity().finish();
                    } else if (selection.equals("English") || selection.equals("الإنجليزية")) {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                        SharedPreferences.Editor editors = prefs.edit();
                        editors.putString("Lang", "en");
                        editors.commit();
                        ///////////////////////////////
                        Locale myLocale = new Locale("en");
                        LanguageUtil.changeLanguageType(mContext, myLocale);
                        Constants.lang = "en";
                        //////////////////////////////////
                        Intent i = v.getContext().getPackageManager().getLaunchIntentForPackage(v.getContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        getActivity().finish();

                    }
                }
            }

        });

    }
}
