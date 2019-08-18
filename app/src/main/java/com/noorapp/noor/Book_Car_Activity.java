package com.noorapp.noor;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.ContactusModel.ContactusResponse;
import com.noorapp.noor.models.Reservation_Activity;
import com.noorapp.noor.models.TransplaceModel.TransPlace;
import com.noorapp.noor.models.TransplaceModel.transresponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.COLORATION;
import static com.noorapp.noor.Utility.App.getContext;

public class Book_Car_Activity extends AppCompatActivity {
    private Context mContext;

    int place_id;
    String price, name, desc, currency;
    public static String dateChoosed, Timechhose;
    static TextView txtshowdate, dateex, timex, timeis, nameplace, descplace, title, total;
    String API, phone;
    EditText phonetxt;
    AwesomeValidation mAwesomeValidation;
    ImageView imageView;
    LinearLayout linf;
    LinearLayout lint;
    static LinearLayout linseledate;
    LinearLayout linseletime;


    Spinner spinnerDropDownFrom, spinnerDropDownTo;
    public static List<String> spinnercitiesFrom;
    public static List<String> spinnercitiesTo;
    private List<TransPlace> cityList;
    String From, To;


    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__car_);
        spinnerDropDownFrom = findViewById(R.id.spinnerfrom);
        spinnerDropDownTo = findViewById(R.id.spinnerto);
        linf = findViewById(R.id.fr);
        lint = findViewById(R.id.to);
        linseledate = findViewById(R.id.datesele);
        linseletime = findViewById(R.id.timesele);

        spinnercitiesFrom = new ArrayList<>();
        spinnercitiesTo = new ArrayList<>();
        cityList = new ArrayList<>();
        mContext = this;

        txtshowdate = findViewById(R.id.datechoosee);
        timex = findViewById(R.id.timechoose);
        timeis = findViewById(R.id.timechoosee);
        txtshowdate.setVisibility(View.GONE);
        timeis.setVisibility(View.GONE);
        dateex = findViewById(R.id.datechoose);
        phonetxt = findViewById(R.id.phoneid);
        mAwesomeValidation = new AwesomeValidation(COLORATION);
        mAwesomeValidation.setColor(Color.YELLOW);
        mAwesomeValidation.addValidation(this, R.id.phoneid, "[0-9]{5,}$", R.string.err_tel);
        nameplace = findViewById(R.id.carnaem);
        descplace = findViewById(R.id.cardesc);
        title = findViewById(R.id.toolbar_title);
        title.setText(R.string.book);
        total = findViewById(R.id.totalM);
        imageView = findViewById(R.id.back);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            place_id = bundle.getInt("placeID");
            price = bundle.getString("placePRICE");
            name = bundle.getString("placeNAME");
            desc = bundle.getString("placeDESC");
            currency = bundle.getString("placeISO");
            Log.e("thattrip", String.valueOf(place_id));
        }
        nameplace.setText(name);
        descplace.setText(desc);
        total.setText(price + " " + currency);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        phone = sharedPref.getString("phone", null);
        API = sharedPref.getString("api_token", null);
        if (phone != null) {
            phonetxt.setText(phone);

        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Call<transresponse> call = RetrofitClient.getInstance().getApi()
                .TransplaceApi();
        call.enqueue(new Callback<transresponse>() {
            @Override
            public void onResponse(Call<transresponse> call, Response<transresponse> response) {

                cityList = response.body().getTransPlaces();
                if (cityList.isEmpty()) {
                    linf.setVisibility(View.GONE);
                    lint.setVisibility(View.GONE);
                } else {
                    for (int i = 0; i < cityList.size(); i++) {
                        spinnercitiesFrom.add(cityList.get(i).getName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.
                            R.layout.simple_spinner_dropdown_item, spinnercitiesFrom);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDropDownFrom.setAdapter(adapter);
                    spinnerDropDownTo.setAdapter(adapter);
                    spinnerDropDownFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {

                            From = spinnerDropDownFrom.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub
                            Log.d("thenon", "onNothingSelected: ");
                        }
                    });
                    spinnerDropDownTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                            To = spinnerDropDownTo.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub
                            Log.d("thenon", "onNothingSelected: ");
                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<transresponse> call, Throwable t) {
                Log.e("fail", "FAILL");
            }
        });
    }

    public static void getDate(String datec) {
        dateex.setText(datec);
        dateChoosed = datec;
        txtshowdate.setVisibility(View.VISIBLE);
        linseledate.setVisibility(View.VISIBLE);

    }

    public void Date_Care(View view) {
        DialogFragment newFragment = new MyDatePickerCarFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

    public void Time_car(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Book_Car_Activity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (selectedMinute < 10) {
                    timex.setText(selectedHour + ": 0" + selectedMinute);
                    Timechhose = selectedHour + ": 0" + selectedMinute;
                    timeis.setVisibility(View.VISIBLE);
                    linseletime.setVisibility(View.VISIBLE);
                } else {
                    timex.setText(selectedHour + ":" + selectedMinute);
                    Timechhose = selectedHour + ":" + selectedMinute;
                    timeis.setVisibility(View.VISIBLE);
                    linseletime.setVisibility(View.VISIBLE);
                }
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    public void BookCar(View view) {

        phone = String.valueOf(phonetxt.getText());

        if (phone == null || phone.matches("") || !mAwesomeValidation.validate()) {

            Toast.makeText(Book_Car_Activity.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();

        } else if (dateChoosed == null) {
            Toast.makeText(Book_Car_Activity.this, "Please Enter date of booking", Toast.LENGTH_SHORT).show();

        } else if (From == null) {
            Toast.makeText(Book_Car_Activity.this, "Please Enter place you will Take car From it", Toast.LENGTH_SHORT).show();

        } else if (To == null) {
            Toast.makeText(Book_Car_Activity.this, "Please Enter the Destination", Toast.LENGTH_SHORT).show();
        } else {
            Call<ContactusResponse> call = RetrofitClient.getInstance().getApi()
                    .BookCarApi(API, String.valueOf(place_id), price, dateChoosed, Timechhose, phone, From, To);
            call.enqueue(new Callback<ContactusResponse>() {
                @Override
                public void onResponse(Call<ContactusResponse> call, Response<ContactusResponse> response) {
                    if (response.body().getResponse().toString().equals("true")) {
                        Toast.makeText(Book_Car_Activity.this, "You book successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Reservation_Activity.class);
                        intent.putExtra("reserve", "OK");
                        startActivity(intent);
                        dateChoosed = null;
                        Timechhose = null;

                        finish();
                    } else {
                        Toast.makeText(Book_Car_Activity.this, "There is some thing wrong try again later", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ContactusResponse> call, Throwable t) {
                    Log.e("fail", "FAILL");
                }
            });
        }

    }
}
