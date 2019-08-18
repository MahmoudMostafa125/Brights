package com.noorapp.noor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.DefaultResponse;
import com.noorapp.noor.models.PlacesDetailsModel.PlaceDetailsResponse;
import com.noorapp.noor.models.PlacesDetailsModel.Review;
import com.noorapp.noor.models.ReservationModel.ReservationResponse;
import com.noorapp.noor.models.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;

public class AddTicket_Activity extends AppCompatActivity {
    String tiID, tiPrice, ticity, ticountry, ticurrency, tidate;
    TextView citytxt, countrytxt, pricetxt, currencttxt, txt1, txt2, txt3, titile;

    String priceStr = null, idStr = null;
    ImageView imageView;
    private List<Ticket> TicketList;
    LinearLayout A, C, I;
    String x, y, z;
    Map m1;
    String PriceAll;
    float val, val1, val2, val3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket_);
        countrytxt = findViewById(R.id.nameIDticket);
        citytxt = findViewById(R.id.countryIDticket);
        citytxt = findViewById(R.id.countryIDticket);
        pricetxt = findViewById(R.id.textPrice);
        titile = findViewById(R.id.toolbar_title);
        titile.setText("");
        currencttxt = findViewById(R.id.textCurrency);
        imageView = findViewById(R.id.back);
        A = findViewById(R.id.TicketA);
        C = findViewById(R.id.TicketC);
        I = findViewById(R.id.TicketI);
        txt1 = findViewById(R.id.besprid);
        txt2 = findViewById(R.id.bespridd);
        txt3 = findViewById(R.id.bespriddd);


        A.setVisibility(View.GONE);
        C.setVisibility(View.GONE);
        I.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ticity = bundle.getString("citynext");
            ticountry = bundle.getString("countrynext");
            tiID = bundle.getString("idnext");
            tiPrice = bundle.getString("pricenext");
            ticurrency = bundle.getString("currenctnext");
            tidate = bundle.getString("datenext");

        }
        countrytxt.setText(ticity);
        citytxt.setText(ticountry);
        pricetxt.setText(tiPrice);
        currencttxt.setText(" " + ticurrency);


        final NumberPicker Anum = findViewById(R.id.A);
        final NumberPicker Cnum = findViewById(R.id.C);
        final NumberPicker Inum = findViewById(R.id.I);
//        np.setMinValue(0);
//        np.setMaxValue(10);
//        npp.setMinValue(0);
//        npp.setMaxValue(10);
//        nppp.setMinValue(0);
//        nppp.setMaxValue(10);
        // np.setWrapSelectorWheel(true);
        m1 = new HashMap();
        Anum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                m1.put("'a'", newVal);
                val = (float) newVal * Float.valueOf(x) + Float.valueOf(tiPrice);
                txt1.setText(String.valueOf(val) + " " + ticurrency);
                System.out.print("\t" + m1);
                Log.d("Mvlauee", "onValueChange: " + m1);
                val1 = val;
            }
        });
        Cnum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                m1.put("'c'", newVal);
                val = (float) newVal * Float.valueOf(y) + Float.valueOf(tiPrice);
                txt2.setText(String.valueOf(val) + " " + ticurrency);
                Log.d("Mvlauee", "onValueChange: " + m1);
                val2 = val;
            }
        });
        Inum.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                m1.put("'i'", newVal);
                val = (float) newVal * Float.valueOf(z) + Float.valueOf(tiPrice);
                txt3.setText(String.valueOf(val) + " " + ticurrency);
                Log.d("Mvlauee", "onValueChange: " + m1);
                val3 = val;
            }
        });

        //////////////////////////////////////////
        if (internetConnection.internetConnectionAvailable(1000)) {


            Call<PlaceDetailsResponse> call = RetrofitClient.getInstance().getApi().PlaceDetailsAPI(String.valueOf(tiID), Constants.lang,Constants.status);
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
                                Anum.setMaxValue(Integer.parseInt(TicketList.get(0).getAvailable()));
                            } else if (TicketList.get(i).getType().equals("C")) {
                                y = TicketList.get(i).getPrice();
                                C.setVisibility(View.VISIBLE);
                                Log.e("jjjj", TicketList.get(0).getAvailable());
                                Cnum.setMinValue(0);
                                Cnum.setMaxValue(Integer.parseInt(TicketList.get(1).getAvailable()));

                            } else if (TicketList.get(i).getType().equals("I")) {
                                z = TicketList.get(i).getPrice();
                                I.setVisibility(View.VISIBLE);
                                Inum.setMinValue(0);
                                Inum.setMaxValue(Integer.parseInt(TicketList.get(2).getAvailable()));
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
            showAlertDialogNoInternetConnection(this.getBaseContext());
            Toast.makeText(this,  R.string.offline, Toast.LENGTH_SHORT).show();
        }
        ////////////////////////////////////


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void carActiviyt(View view) {
        Intent intent = new Intent(this, RentCar_Activity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode != RESULT_CANCELED) {
                if (data.getStringExtra("MYPRICE") != "") {
                    priceStr = data.getStringExtra("MYPRICE");
                    idStr = data.getStringExtra("MYID");
                    // Toast.makeText(this, priceStr+" "+idStr, Toast.LENGTH_SHORT).show();
                    Log.d("MYPRICEE", "onActivityResult: " + priceStr);
                } else {
                    Log.e("sdsdsdsdsdazc", "Sdsds");
                }
            } else {

            }
        }
    }

    public void Pay(View view) {
        if (idStr == null) {
            idStr = "-1";
        }
        if (priceStr == null) {
            priceStr = "0";
        }
        final float total = val1 + val2 + val3;
        Call<ReservationResponse> call = RetrofitClient.getInstance().getApi().
                ReservationApi(tiID, String.valueOf(m1), idStr, priceStr, String.valueOf(total),
                        tidate, "00:00", "819679d67c828717ec2ba4fdc842b2907qxOd70vaQbyyN6F1LNTdx9H7YZfP9eN", "");
        call.enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(AddTicket_Activity.this, "You finish Your Reservation Successfully  You Spent : " + total + " " + ticurrency, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(AddTicket_Activity.this, , Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AddTicket_Activity.this, "Sorry There is Some thing Wrong Try again Later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                Log.e("error_retrofit", call.toString());
            }
        });
    }
}
