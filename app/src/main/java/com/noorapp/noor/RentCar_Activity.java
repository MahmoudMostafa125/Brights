package com.noorapp.noor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;
import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.adapter.CarsAdapter;
import com.noorapp.noor.adapter.CarsRentAdapter;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.TransportationModel.Transportation;
import com.noorapp.noor.models.TransportationModel.TransportationRespose;

import com.noorapp.noor.models.TripModel.TripResponse;
import com.noorapp.noor.models.TripModel.Trip;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;
import static com.noorapp.noor.adapter.CarsRentAdapter.getid;
import static com.noorapp.noor.adapter.CarsRentAdapter.getprice;
import static com.noorapp.noor.adapter.CarsRentAdapter.setid;
import static com.noorapp.noor.adapter.CarsRentAdapter.setprice;

public class RentCar_Activity extends AppCompatActivity {
    private Context mContext;
    private List<Transportation> carsList, crsWithoutnull;
    private List<com.noorapp.noor.models.TripModel.Trip> TripList;
    private List<com.noorapp.noor.models.TripModel.Trip> TripListFinal;
    private List<Boolean> tripswithounull;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Context context;
    ImageView imageView;
    private CarsRentAdapter carsRentAdapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car_);
        mContext = this;

        imageView = (ImageView) findViewById(R.id.back);
        recyclerView = findViewById(R.id.rentC);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        context = this.getApplication();
        progressBar = findViewById(R.id.progressBar2);
        if (internetConnection.internetConnectionAvailable(1000)) {

            Call<TripResponse> call = RetrofitClient.getInstance().getApi().TripsAPI("", Constants.lang,Constants.status);
            call.enqueue(new Callback<TripResponse>() {
                @Override
                public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {
                    if (response.isSuccessful()) {
                        TripList = response.body().getTrips();
                        tripswithounull = new ArrayList<Boolean>();
                        TripListFinal = new ArrayList<>();
                        for (int i = 0; i < TripList.size(); i++) {
                            String str = TripList.get(i).getPlaces();

                            String[] parts = str.split(",");
                            for (String s : parts) {
                                Log.e("hyhthth", s);
                                if (s.equals("1")) {
                                    Log.e("hyhthth", "kaknum");
                                    TripListFinal.add(response.body().getTrips().get(i));
                                    tripswithounull.add(i, false);
                                }
                            }
                        }
                        Log.e("hhhhmmmmmmm", response.body().toString());
                    }
                    Log.d("THATTRER", "onResponse: " + TripListFinal.size());
                    carsRentAdapter = new CarsRentAdapter(context, TripListFinal, tripswithounull);
                    recyclerView.setAdapter(carsRentAdapter);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<TripResponse> call, Throwable t) {
                    Log.e("error_retrofit_trans", call.toString());
                }
            });

        } else {
            showAlertDialogNoInternetConnection(this.getBaseContext());
            Toast.makeText(this,  R.string.offline, Toast.LENGTH_SHORT).show();
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void Confrim(View view) {
        if (getprice() != null) {
            Toast.makeText(context, getprice() + "   " + getid(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("MYPRICE", getprice());
            intent.putExtra("MYID", getid());
            setResult(1, intent);
            setprice(null);
            setid(null);
            finish();
        } else {
            Toast.makeText(context, "You Should Choose Car ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("baaaaaaak", "onBackPressed: ");
        Intent intent = new Intent();
        intent.putExtra("MYPRICE", "");
        intent.putExtra("MYID", "");
        setResult(2, intent);
    }
}
