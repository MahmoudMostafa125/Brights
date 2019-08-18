package com.noorapp.noor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;
import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.adapter.CarsRentAdapter;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.PaymentModel.PayTokenResponse;
import com.noorapp.noor.models.PlacesDetailsModel.PlaceDetailsResponse;
import com.noorapp.noor.models.PromoModel.PromoResponse;
import com.noorapp.noor.models.ReservationModel.ReservationResponse;
import com.noorapp.noor.models.Reservation_Activity;
import com.noorapp.noor.models.ReservationsModel.ReservationsResponse;
import com.noorapp.noor.models.Ticket;
import com.noorapp.noor.models.TransportationModel.Transportation;
import com.noorapp.noor.models.TripModel.TripResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;

public class Book_Date_Activity extends AppCompatActivity {
    private Context mContext;
    private static final int REQUEST_CODE = 1234;
    String Amount, Token = "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI5N2ZlOGM5NDQwOWEzNDdiZjU5YzI3YWJiMDc3YjNkOGY3N2ZiODEzZWE5YzFhNmJlYTVkNjA2YjA3Y2JiOWEwfGNyZWF0ZWRfYXQ9MjAxOS0wMi0xM1QxODoyNDo0MS42NDAyNDk0MTkrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJncmFwaFFMIjp7InVybCI6Imh0dHBzOi8vcGF5bWVudHMuc2FuZGJveC5icmFpbnRyZWUtYXBpLmNvbS9ncmFwaHFsIiwiZGF0ZSI6IjIwMTgtMDUtMDgifSwiY2hhbGxlbmdlcyI6W10sImVudmlyb25tZW50Ijoic2FuZGJveCIsImNsaWVudEFwaVVybCI6Imh0dHBzOi8vYXBpLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb206NDQzL21lcmNoYW50cy8zNDhwazljZ2YzYmd5dzJiL2NsaWVudF9hcGkiLCJhc3NldHNVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImF1dGhVcmwiOiJodHRwczovL2F1dGgudmVubW8uc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbSIsImFuYWx5dGljcyI6eyJ1cmwiOiJodHRwczovL29yaWdpbi1hbmFseXRpY3Mtc2FuZC5zYW5kYm94LmJyYWludHJlZS1hcGkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=";
    String API;
    String citynameget;
    String countrynameget;
    String iddget;
    String priceget;
    static String currencyget;
    TextView citytxt;
    TextView countrytxt;
    TextView title;
    TextView total;
    static TextView totalMoney;
    static TextView dateCh, cach, txtshow;
    ImageView imageView;
    public static String datec, SelectedMoney = "0", SelectedDate, CarID = "0", carm, Mcach = "0";
    public static String money = "0";
    public static String moneyPStatic = "0";
    public static String moneyPersentage = "0";
    public static String Promo = "0";
    public static String Promot = "0";
    String promo = "";
    ///////////////////////////////////////


    ImageView VandINV, imgpro;
    ProgressBar protra;
    /////////////////////////////////////////////////
    LinearLayout A, C, I;
    TextView txt1, txt2, txt3;
    Map m1;
    float val;
    static double val1;
    static double val2;
    static double val3;
    String x, y, z;
    private List<Ticket> TicketList;
    //////////////////////////////////////////////////

    LinearLayout LinearCar;
    ////////////////////////////////////////////////////**/
    private List<Transportation> carsList, crsWithoutnull;
    private List<com.noorapp.noor.models.TripModel.Trip> TripList;
    private List<com.noorapp.noor.models.TripModel.Trip> TripListFinal;
    private List<Boolean> tripswithounull;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Context context;
    private CarsRentAdapter carsRentAdapter;
    EditText txtPromo;
    TextView txtpromodone;
    static TextView oldPrice;
    Button btnpro;

    /////////////////////////////////////////////////////**/
    public static String bTotal = "0";
    public static String aTotla = "0";
    public static String bPlace = "0";
    public static String aPlace = "0";
    public static String bTrip = "0";
    public static String aTrip = "0";

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__date_);

        txtshow = findViewById(R.id.datechoosee);
        LinearCar= findViewById(R.id.linearcar);
        dateCh = findViewById(R.id.datechoose);
        //txtshow.setVisibility(View.GONE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String currentDateandTime = sdf.format(new Date());
        dateCh.setText(currentDateandTime);
        SelectedDate = currentDateandTime;
        mContext = this;
        oldPrice = findViewById(R.id.old_price);
        txtPromo = findViewById(R.id.txtpromo);
        protra = findViewById(R.id.protra);
        imgpro = findViewById(R.id.imgtra);
        btnpro = findViewById(R.id.btnPromo);
        txtpromodone = findViewById(R.id.txtproomodone);
        txtpromodone.setVisibility(View.GONE);
        protra.setVisibility(View.GONE);
        imgpro.setVisibility(View.GONE);

        VandINV = findViewById(R.id.vcode);
        total = findViewById(R.id.totalM);

        cach = findViewById(R.id.totalMS);

        title = findViewById(R.id.toolbar_title);
        title.setText(R.string.book);
        totalMoney = findViewById(R.id.totalALL);
        imageView = (ImageView) findViewById(R.id.back);
        citytxt = findViewById(R.id.countryIDbook);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            citynameget = bundle.getString("city");
            countrynameget = bundle.getString("country");
            iddget = bundle.getString("id");
            priceget = bundle.getString("price");
            currencyget = bundle.getString("currency");
            Log.d("thiusID", iddget);
        }
        countrytxt = findViewById(R.id.nameIDbook);
        citytxt.setText(countrynameget);
        countrytxt.setText(citynameget);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        API = sharedPref.getString("api_token", null);
        ////////////////////////////////////////
        A = findViewById(R.id.TicketA);
        C = findViewById(R.id.TicketC);
        I = findViewById(R.id.TicketI);
        txt1 = findViewById(R.id.besprid);
        txt2 = findViewById(R.id.bespridd);
        txt3 = findViewById(R.id.bespriddd);


        A.setVisibility(View.GONE);
        C.setVisibility(View.GONE);
        I.setVisibility(View.GONE);
        final NumberPicker Anum = findViewById(R.id.A);
        final NumberPicker Cnum = findViewById(R.id.C);
        final NumberPicker Inum = findViewById(R.id.I);
        m1 = new HashMap();

        Anum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                m1.put("'A'", newVal);
                val = (float) newVal * Float.valueOf(x);
                txt1.setText(String.valueOf(val) + " " + currencyget);
                val1 = val;
                total.setText(String.valueOf(val1 + val2 + val3) + " " + currencyget);
                SelectedMoney = String.valueOf(val1 + val2 + val3);
                if (moneyPStatic != "0") {
                    if (Float.valueOf(moneyPStatic) < (val1 + val2 + val3 + Float.valueOf(Mcach))) {
                        totalMoney.setText((String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach) - Float.valueOf(moneyPStatic))
                                + " " + currencyget));
                    } else {
                        totalMoney.setText("0.0" + " " + currencyget);
                    }
                    if ((val1 + val2 + val3 + Double.valueOf(Mcach)) == 00.00) {
                        oldPrice.setText("");
                    } else {
                        oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        oldPrice.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);
                    }
                } else if (moneyPersentage != "0") {
                    Double sim = (val1 + val2 + val3 + Double.valueOf(Mcach)) * (Double.valueOf(moneyPersentage) / 100.0);
                    totalMoney.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach)) - sim + " " + currencyget));
                    if (sim != 00.00) {
                        oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        oldPrice.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);
                    } else {
                        oldPrice.setText("");
                    }
                } else {
                    totalMoney.setText(String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach)) + " " + currencyget);
                }
                money = String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach));


                bTotal = String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach));
                bPlace = String.valueOf(val1 + val2 + val3);
            }

        });
        Cnum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                m1.put("'C'", newVal);

                val = (float) newVal * Float.valueOf(y);
                txt2.setText(String.valueOf(val) + " " + currencyget);
                Log.d("Mvlauee", "onValueChange: " + m1);
                val2 = val;
                total.setText(String.valueOf(val1 + val2 + val3) + " " + currencyget);
                SelectedMoney = String.valueOf(val1 + val2 + val3);
                if (moneyPStatic != "0") {
                    if (Float.valueOf(moneyPStatic) < (val1 + val2 + val3 + Float.valueOf(Mcach))) {
                        totalMoney.setText((String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach) - Float.valueOf(moneyPStatic))
                                + " " + currencyget));
                    } else {
                        totalMoney.setText("0.0" + " " + currencyget);
                    }
                    if ((val1 + val2 + val3 + Double.valueOf(Mcach)) == 00.00) {
                        oldPrice.setText("");
                    } else {
                        oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        oldPrice.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);
                    }
                } else if (moneyPersentage != "0") {
                    Double sim = (val1 + val2 + val3 + Double.valueOf(Mcach)) * (Double.valueOf(moneyPersentage) / 100.0);
                    totalMoney.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach)) - sim + " " + currencyget));
                    if (sim != 00.00) {
                        oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        oldPrice.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);
                    } else {
                        oldPrice.setText("");
                    }
                } else {
                    totalMoney.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);
                }
                money = String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach));

                bTotal = String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach));
                bPlace = String.valueOf(val1 + val2 + val3);

            }
        });
        Inum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                m1.put("'I'", newVal);
                val = (float) newVal * Float.valueOf(z);
                txt3.setText(String.valueOf(val) + " " + currencyget);
                val3 = val;
                total.setText(String.valueOf(val1 + val2 + val3) + " " + currencyget);
                SelectedMoney = String.valueOf(val1 + val2 + val3);

                if (moneyPStatic != "0") {
                    if (Float.valueOf(moneyPStatic) < (val1 + val2 + val3 + Float.valueOf(Mcach))) {
                        totalMoney.setText((String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach) - Float.valueOf(moneyPStatic))
                                + " " + currencyget));
                    } else {
                        totalMoney.setText("0.0" + " " + currencyget);
                    }
                    if ((val1 + val2 + val3 + Double.valueOf(Mcach)) == 00.00) {
                        oldPrice.setText("");
                    } else {
                        oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        oldPrice.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);
                    }
                } else if (moneyPersentage != "0") {
                    Double sim = (val1 + val2 + val3 + Double.valueOf(Mcach)) * (Double.valueOf(moneyPersentage) / 100.0);
                    totalMoney.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach)) - sim + " " + currencyget));
                    if (sim != 00.00) {
                        oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        oldPrice.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);
                    } else {
                        oldPrice.setText("");
                    }
                } else {
                    totalMoney.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);
                }
                money = String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach));

                bTotal = String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach));
                bPlace = String.valueOf(val1 + val2 + val3);

            }
        });
        /////////////////////////////////////////
        if (internetConnection.internetConnectionAvailable(1000)) {
            Call<PlaceDetailsResponse> call = RetrofitClient.getInstance().getApi().PlaceDetailsAPI(String.valueOf(iddget), Constants.lang, Constants.status);
            call.enqueue(new Callback<PlaceDetailsResponse>() {
                @Override
                public void onResponse(Call<PlaceDetailsResponse> call, Response<PlaceDetailsResponse> response) {
                    if (response.isSuccessful()) {
                        TicketList = response.body().getPlace().get(0).getTickets();
                        for (int i = 0; i < TicketList.size(); i++) {
                            Log.d("ttticket", TicketList.get(i).getType());
                            if (TicketList.get(i).getType().equals("A")) {
                                x = TicketList.get(i).getPrice();
                                Log.d("HHHHAAA", "onResponse: ");
                                A.setVisibility(View.VISIBLE);
                                Anum.setMinValue(0);
                                Anum.setMaxValue(Integer.parseInt(TicketList.get(i).getAvailable()));
                            } else if (TicketList.get(i).getType().equals("C")) {
                                y = TicketList.get(i).getPrice();
                                C.setVisibility(View.VISIBLE);
                                Log.e("jjjj", TicketList.get(0).getAvailable());
                                Cnum.setMinValue(0);
                                Cnum.setMaxValue(Integer.parseInt(TicketList.get(i).getAvailable()));

                            } else if (TicketList.get(i).getType().equals("I")) {
                                z = TicketList.get(i).getPrice();
                                I.setVisibility(View.VISIBLE);
                                Inum.setMinValue(0);
                                Inum.setMaxValue(Integer.parseInt(TicketList.get(i).getAvailable()));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                    Log.e("error_retrofit", call.toString());
                }
            });
        } else {
            showAlertDialogNoInternetConnection(this);
            Toast.makeText(this, R.string.offline, Toast.LENGTH_SHORT).show();
        }
        ///////////////////////////////////
        //////////////////////////////////////////////////****/
        recyclerView = findViewById(R.id.rentC);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        context = this.getApplication();
        progressBar = findViewById(R.id.progressBar2);
        if (internetConnection.internetConnectionAvailable(1000)) {
            Call<TripResponse> call2 = RetrofitClient.getInstance().getApi().TripsAPI("", Constants.lang, Constants.status);
            call2.enqueue(new Callback<TripResponse>() {
                @Override
                public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {
                    if (response.isSuccessful()) {
                        TripList = response.body().getTrips();
                        tripswithounull = new ArrayList<Boolean>();
                        TripListFinal = new ArrayList<>();
                        for (int i = 0; i < TripList.size(); i++) {
                            String str = TripList.get(i).getPlaces();

                            if (str != null) {
                                String[] parts = str.split(",");
                                for (String s : parts) {
                                    Log.e("hyhthth", s);
                                    if (s.equals(iddget)) {
                                        Log.e("hyhthth", "kaknum");
                                        TripListFinal.add(response.body().getTrips().get(i));
                                        tripswithounull.add(i, false);
                                    }
                                }
                            }else{
                                LinearCar.setVisibility(View.GONE);
                            }
                        }
                        Log.e("hhhhmmmmmmm", response.body().toString());
                        carsRentAdapter = new CarsRentAdapter(context, TripListFinal, tripswithounull);
                        recyclerView.setAdapter(carsRentAdapter);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(Book_Date_Activity.this, "@string/there_is_no_data", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                    Log.d("THATTRER", "onResponse: " + TripListFinal.size());
                }

                @Override
                public void onFailure(Call<TripResponse> call, Throwable t) {
                    Log.e("error_retrofit_trans", call.toString());
                }
            });
        } else {
            showAlertDialogNoInternetConnection(this);
            Toast.makeText(this, R.string.offline, Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, "try to check your connection", Toast.LENGTH_SHORT).show();
        }
        /////////////////////////////////////////////////////****/

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedMoney = "0";
                SelectedDate = null;
                CarID = "0";
                carm = null;
                Mcach = "0";
                money = "0";
                moneyPStatic = "0";
                moneyPersentage = "0";
                Promo = "0";
                Promot = "0";
                val1 = 0;
                val2 = 0;
                val3 = 0;

                bTotal = "0";
                aTotla = "0";
                bPlace = "0";
                aPlace = "0";
                bTrip = "0";
                aTrip = "0";

                finish();
            }
        });
    }

    public void Next(View view) {
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

    public static void mketext(String datec) {
        dateCh.setText(datec);
        SelectedDate = datec;
        txtshow.setVisibility(View.VISIBLE);
    }

    public static void mkecarp(String mdatec, String datec) {
        cach.setText(datec);
        Mcach = mdatec;
        if (moneyPStatic != "0") {
            if (Float.valueOf(moneyPStatic) < (val1 + val2 + val3 + Float.valueOf(Mcach))) {
                totalMoney.setText((String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach) - Float.valueOf(moneyPStatic))
                        + " " + currencyget));
            } else {
                totalMoney.setText("0.0" + " " + currencyget);
            }

            if ((val1 + val2 + val3 + Double.valueOf(Mcach)) == 00.00) {
                oldPrice.setText("");
            } else {
                oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                oldPrice.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);
            }

        } else if (moneyPersentage != "0") {
            Double sim = (val1 + val2 + val3 + Double.valueOf(Mcach)) * (Double.valueOf(moneyPersentage) / 100.0);
            totalMoney.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach)) - sim + " " + currencyget));
            if (sim != 00.00) {
                oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                oldPrice.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);
            } else {
                oldPrice.setText("");
            }
        } else {
            totalMoney.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);

        }

        money = String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach));

        bTotal = String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach));
        bTrip = Mcach;


    }

    public void getPromo(View view) {
        if (!txtPromo.getText().toString().isEmpty()) {

            protra.setVisibility(View.VISIBLE);
            imgpro.setVisibility(View.VISIBLE);

            if (internetConnection.internetConnectionAvailable(1000)) {

                Call<PromoResponse> call = RetrofitClient.getInstance().getApi().PromoApi(String.valueOf(txtPromo.getText()), API);
                call.enqueue(new Callback<PromoResponse>() {
                    @Override
                    public void onResponse(Call<PromoResponse> call, Response<PromoResponse> response) {
//                    Log.e("Rerere", String.valueOf(response.body().getResponse()));
                        if (response.body() != null) {
                            if (!String.valueOf(response.body().getResponse()).equals("false")) {
                                String code = response.body().getData().get(0).getCoupon();
                                if (String.valueOf(txtPromo.getText()).equals(code)) {
                                    promo = String.valueOf(txtPromo.getText());
                                    Toast.makeText(Book_Date_Activity.this, "You use the Promo Successfully", Toast.LENGTH_SHORT).show();
                                    protra.setVisibility(View.GONE);
                                    imgpro.setVisibility(View.GONE);
                                    VandINV.setImageResource(R.drawable.ic_check_circle);
                                    if (response.body().getData().get(0).getDiscountPercentage() != null) {
                                        moneyPersentage = response.body().getData().get(0).getDiscountPercentage();
                                        txtpromodone.setText("You have now : " + moneyPersentage + " %" + "OFF");

                                        Double sim = (val1 + val2 + val3 + Double.valueOf(Mcach)) * (Double.valueOf(moneyPersentage) / 100.0);
                                        totalMoney.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach)) - sim + " " + currencyget));
                                        if (sim != 00.00) {
                                            oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            oldPrice.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);
                                        } else {
                                            oldPrice.setText("");
                                        }
                                        txtpromodone.setVisibility(View.VISIBLE);
                                        btnpro.setVisibility(View.GONE);
                                        txtPromo.setVisibility(View.GONE);

                                        String LastPlace = response.body().getData().get(0).getPlaces();
                                        String LastTrip = response.body().getData().get(0).getTransportations();
                                        if (LastPlace == null && LastTrip == null) {
                                            Log.e("twonull", "the two variable in percentage equal null");
                                            aTotla = String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach)) - sim);
                                        } else {
                                            if (LastPlace != null) {
                                                String str = LastPlace;
                                                String[] parts = str.split(",");
                                                for (String s : parts) {
                                                    Log.e("hyhthth", s);
                                                    if (s.equals(iddget)) {
                                                        Log.e("placenotnull", "place is not null in percentage");
                                                        aPlace =
                                                                String.valueOf(((val1 + val2 + val3) -
                                                                        ((val1 + val2 + val3) * (Double.valueOf(moneyPersentage) / 100.0)))
                                                                       );
                                                    }
                                                }
                                            }

                                            if (LastTrip != null) {

                                                Log.e("tripnotnull", "trip is not null in percentage");
                                                aTrip = String.valueOf(
                                                        (Double.valueOf(Mcach)) -
                                                                ((Double.valueOf(Mcach)) * (Double.valueOf(moneyPersentage) / 100.0)));
                                            }
                                        }

                                    } else if (response.body().getData().get(0).getDiscountStatic() != null) {
                                        moneyPStatic = response.body().getData().get(0).getDiscountStatic();
                                        txtpromodone.setText("You have now : " + moneyPStatic + " " + currencyget + " OFF");
                                        if (Float.valueOf(moneyPStatic) < (val1 + val2 + val3 + Float.valueOf(Mcach))) {
                                            totalMoney.setText((String.valueOf(val1 + val2 + val3 + Float.valueOf(Mcach) - Float.valueOf(moneyPStatic))
                                                    + " " + currencyget));
                                        } else {
                                            totalMoney.setText("0.0" + " " + currencyget);
                                        }

                                        if ((val1 + val2 + val3 + Double.valueOf(Mcach)) == 00.00) {
                                            oldPrice.setText("");
                                        } else {
                                            oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                            oldPrice.setText(String.valueOf((val1 + val2 + val3 + Float.valueOf(Mcach))) + " " + currencyget);
                                        }

                                        txtpromodone.setVisibility(View.VISIBLE);
                                        btnpro.setVisibility(View.GONE);
                                        txtPromo.setVisibility(View.GONE);

                                        String LastPlace = response.body().getData().get(0).getPlaces();
                                        String LastTrip = response.body().getData().get(0).getTransportations();
                                        /**/
                                        if (LastPlace == null && LastTrip == null) {
                                            Log.e("twonull", "the two variable in Static equal null");
                                            aTotla = String.valueOf((val1 + val2 + val3 + Double.valueOf(Mcach)) - Double.valueOf(moneyPStatic));
                                        } else {
                                            if (LastPlace != null) {
                                                String str = LastPlace;
                                                String[] parts = str.split(",");
                                                for (String s : parts) {
                                                    Log.e("hyhthth", s);
                                                    if (s.equals(iddget)) {
                                                        Log.e("placenotnull", "place is not null in static");
                                                        aPlace = String.valueOf(val1 + val2 + val3 -Double.valueOf(moneyPStatic));
                                                    }
                                                }
                                            }

                                            if (LastTrip != null) {
                                                Log.e("tripnotnull", "trip is not null in static");
                                                aTrip = String.valueOf(
                                                        (Double.valueOf(Mcach)) - (Double.valueOf(moneyPStatic)));
                                            }
                                        }

                                    }
                                } else {
                                    Toast.makeText(Book_Date_Activity.this, "Invalid Promo Code !!!", Toast.LENGTH_SHORT).show();
                                    protra.setVisibility(View.GONE);
                                    imgpro.setVisibility(View.GONE);
                                    VandINV.setImageResource(R.drawable.ic_error_black_24dp);
                                }
                            } else {
                                Toast.makeText(Book_Date_Activity.this, "Invalid Promo Codee !!!", Toast.LENGTH_SHORT).show();
                                protra.setVisibility(View.GONE);
                                imgpro.setVisibility(View.GONE);
                                VandINV.setImageResource(R.drawable.ic_error_black_24dp);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<PromoResponse> call, Throwable t) {
                        Log.e("error_retrofit", call.toString());
                    }
                });
            } else {
                showAlertDialogNoInternetConnection(this);
                Toast.makeText(this, R.string.offline, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Book_Date_Activity.this, "you should enter promo code", Toast.LENGTH_SHORT).show();
        }

    }


    public void Pay(View view) {
        Log.e("sdswdsf", bPlace + "\n " + bTrip + "\n " + bTotal + "\n " + aPlace + "\n " + aTrip + "\n " + aTotla);

        String kkk = String.valueOf(m1);
        kkk = kkk.replace("=", ":");
        Log.e("Data_of_all", iddget + "\n" + /*String.valueOf(m1)*/kkk + "\n" + CarID + "\n" + Mcach + "\n" + SelectedMoney + "\n" + SelectedDate);
        if (SelectedMoney.equals("0") || m1.isEmpty() || (val1 + val2 + val3) == 00.00) {
            Toast.makeText(Book_Date_Activity.this, "Please select Tickets", Toast.LENGTH_SHORT).show();
        } else if (SelectedDate == null) {
            Toast.makeText(Book_Date_Activity.this, "Please select Date of reservation", Toast.LENGTH_SHORT).show();
        } else {
            if (!moneyPStatic.equals("0")) {
                Promo = moneyPStatic;
                Promot = moneyPStatic;
            } else if (!moneyPersentage.equals("0")) {
                Promo = String.valueOf(Float.valueOf(SelectedMoney) * (Float.valueOf(moneyPersentage) / 100));
                Promot = String.valueOf(Float.valueOf(Mcach) * (Float.valueOf(moneyPersentage) / 100));

            }
            Log.e("ggg", iddget + "\n" +
                    kkk + "\n" +
                    CarID + "\n" +
                    String.valueOf(Float.valueOf(Mcach) - Float.valueOf(Promot)) + "\n" +
                    String.valueOf(Float.valueOf(SelectedMoney) - Float.valueOf(Promo)) + "\n" +
                    SelectedDate + "\n" +
                    "00:00" + "\n" +
                    API + "\n" +
                    promo);
            Call<ReservationResponse> call = RetrofitClient.getInstance().getApi().
                    ReservationApi(iddget,
                            kkk,
                            CarID,
                            String.valueOf(Float.valueOf(Mcach) - Float.valueOf(Promot)),
                            String.valueOf(Float.valueOf(SelectedMoney) - Float.valueOf(Promo)),
                            SelectedDate,
                            "00:00",
                            API,
                            promo);

            call.enqueue(new Callback<ReservationResponse>() {
                @Override
                public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getResponse().toString().equals("true")) {
                            Toast.makeText(Book_Date_Activity.this, "You finish Your Reservation Successfully"  /*You Spent : "
                                    + String.valueOf((Float.valueOf(Mcach) - Float.valueOf(Promot) + (Float.valueOf(SelectedMoney) - Float.valueOf(Promo)))) + " " + currencyget*/, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Reservation_Activity.class);
                            intent.putExtra("reserve", "OK");
                            startActivity(intent);
                            /////////////////
                            SelectedMoney = "0";
                            SelectedDate = null;
                            CarID = "0";
                            carm = null;
                            Mcach = "0";
                            money = "0";
                            moneyPStatic = "0";
                            moneyPersentage = "0";
                            Promo = "0";
                            Promot = "0";
                            val1 = 0;
                            val2 = 0;
                            val3 = 0;


                            bTotal = "0";
                            aTotla = "0";
                            bPlace = "0";
                            aPlace = "0";
                            bTrip = "0";
                            aTrip = "0";
                            ///////////////////
                            finish();
                        } else {
                            Toast.makeText(Book_Date_Activity.this, "Sorry There is Some thing Wrong Try again Later!!", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(Book_Date_Activity.this, "Sorry There is Some thing Wrong Try again Later", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ReservationResponse> call, Throwable t) {
                    Log.e("error_retrofit", call.toString());
                }
            });
        }
    }

    public void onBraintreeSubmit(View v) {
        if (internetConnection.internetConnectionAvailable(1000)) {


            Call<PayTokenResponse> call = RetrofitClient.getInstance().getApi().PaymenttokenApi();
            call.enqueue(new Callback<PayTokenResponse>() {
                @Override
                public void onResponse(Call<PayTokenResponse> call, Response<PayTokenResponse> response) {
                    Log.e("Code ", response.body().getClientToken());
                    //  DropInRequest dropInRequest = new DropInRequest().clientToken(response.body().getClientToken());
                    //  startActivityForResult(dropInRequest.getIntent(Book_Date_Activity.this), REQUEST_CODE);
                    // Toast.makeText(Paypal_Activity.this, , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<PayTokenResponse> call, Throwable t) {
                    Log.e("error_retrofit", call.toString());
                }
            });
        } else {
            showAlertDialogNoInternetConnection(this);
            Toast.makeText(this, R.string.offline, Toast.LENGTH_SHORT).show();
        }
        ////////////////
//        DropInRequest dropInRequest = new DropInRequest().clientToken(Token);
//        startActivityForResult(dropInRequest.getIntent(Book_Date_Activity.this), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 /*       if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();
                Log.d("thatOKOK", "onActivityResult: " + strNonce);
                // use the result to update your UI and send the payment method nonce to your server
                //here i'll send ===> strNonce and Amount to the API
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("CAnceled", "The Result is Canceled");

                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.e("ErrororroHERe", String.valueOf(error));

            }
        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SelectedMoney = "0";
        SelectedDate = null;
        CarID = "0";
        carm = null;
        Mcach = "0";
        money = "0";
        moneyPStatic = "0";
        moneyPersentage = "0";
        Promo = "0";
        Promot = "0";
        val1 = 0;
        val2 = 0;
        val3 = 0;

        bTotal = "0";
        aTotla = "0";
        bPlace = "0";
        aPlace = "0";
        bTrip = "0";
        aTrip = "0";

    }

    public void fun(String bTotal, String aTotla, String bPlace, String aPlace, String bTrip, String aTrip) {

    }
}
