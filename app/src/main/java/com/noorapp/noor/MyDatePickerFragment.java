package com.noorapp.noor;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import static com.noorapp.noor.Book_Date_Activity.datec;

public class MyDatePickerFragment extends DialogFragment {
    int myear;
    int mmonth;
    int mday;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        myear = c.get(Calendar.YEAR);
        mmonth = c.get(Calendar.MONTH);
        mday = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateSetListener, myear, mmonth, mday);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        return dialog;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {

//                    SpannableString spannableStr = new SpannableString("selected date is : " );
//                    ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
//                    spannableStr.setSpan(foregroundColorSpan, 0, 15, Spanned.SPAN_COMPOSING);
                    if (year < myear || (month + 1) < mmonth + 1 || day < mday) {
                        //if (day < mday) {
                        Toast.makeText(getActivity(), "invalid date , You can't choose past date !!!", Toast.LENGTH_LONG).show();
                        return;
                        //  }
                    } else {
                        datec = view.getYear() +
                                "/" + (view.getMonth() + 1) +
                                "/" + view.getDayOfMonth();
                        String x;
                        x = view.getYear() +
                                "/" + (view.getMonth() + 1) +
                                "/" + view.getDayOfMonth();
                        Book_Date_Activity.mketext(x);


                        Toast.makeText(getActivity(), "selected date is " + view.getYear() +
                                "/" + (view.getMonth() + 1) +
                                "/" + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();

                    }
                }
            };

}
