package com.noorapp.noor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;
import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.adapter.PlacesNearAdaptercard;
import com.noorapp.noor.adapter.TripsAdapter;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.CityModel.Cite;
import com.noorapp.noor.models.CityModel.CityResponse;
import com.noorapp.noor.models.Place;
import com.noorapp.noor.models.StateModel.State;
import com.noorapp.noor.models.StateModel.StateResponse;
import com.noorapp.noor.models.TransportationModel.TransportationRespose;
import com.noorapp.noor.models.TransportationModel.Trip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.noorapp.noor.Guide_frag.spinnercities;
import static com.noorapp.noor.Utility.App.getContext;
import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;

public class Trips_Activity extends AppCompatActivity {

    private static Context mContext;
    private static List<Trip> TripList;
    static RecyclerView recyclerView;
    private static TripsAdapter adapter4;

    /////////////////////
    public int place_id;
    Context context;
    ImageView imageView;
    TextView toolbartitle;
    ProgressBar progressBar;
    TextView txtnodatap;
    ////////////////////////////
    Spinner spinnerDropDown;
    private List<State> cityList;
    public static List<String> tripspinnercities;
    public static List<Trip> TripplacesListSearchFiltered;

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips_);
        ////////////////////////
        spinnerDropDown = findViewById(R.id.spinner1);
        txtnodatap = findViewById(R.id.messagenodatap);
        txtnodatap.setVisibility(View.GONE);
        /////////////////////////
        mContext = this;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            place_id = bundle.getInt("placeID");
        }
        toolbartitle = findViewById(R.id.toolbar_title);
        toolbartitle.setText(R.string.Cars);
        imageView = findViewById(R.id.back);
        recyclerView = findViewById(R.id.dicsplaces);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        progressBar = findViewById(R.id.progressBar2);
        if (internetConnection.internetConnectionAvailable(1000)) {
            Call<TransportationRespose> call = RetrofitClient.getInstance().getApi().TransportationApi(Constants.lang,Constants.status);
            call.enqueue(new Callback<TransportationRespose>() {
                @Override
                public void onResponse(Call<TransportationRespose> call, Response<TransportationRespose> response) {

                    if (response.isSuccessful()) {
                        TripList = response.body().getTransportations().get(place_id).getTrips();
                    } else {
                        txtnodatap.setVisibility(View.VISIBLE);
                    }
                    if (TripList.isEmpty()) {
                        txtnodatap.setVisibility(View.VISIBLE);
                    }
                    // adapter4 = new TripsAdapter(getApplicationContext(), TripList);
                    // recyclerView.setAdapter(adapter4);

                    ////////////////////////////////////////////
                   /* Call<CityResponse> call2 = RetrofitClient.getInstance().getApi().CityResponseAPI(String.valueOf(1), Constants.lang,Constants.status,Constants.ticket);
                    call2.enqueue(new Callback<CityResponse>() {
                        @Override
                        public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {

                            tripspinnercities = new ArrayList<>();
                            cityList = response.body().getCountries().get(0).getCites();
                            for (int i = 0; i < cityList.size(); i++) {
                                tripspinnercities.add(cityList.get(i).getTranslations().get(0).getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.
                                    R.layout.simple_spinner_dropdown_item, tripspinnercities);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerDropDown.setAdapter(adapter);
                            spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    // Get select item
                                    Context ctxx = parent.getContext();
                                    String selected = spinnerDropDown.getSelectedItem().toString();
                                    if (TripList != null) {
                                        if (!TripList.isEmpty()) {
                                            filterListwithCity3(selected, TripList);
                                        }
                                    }
                                    Log.e("clllllll", selected);
                                    progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    // TODO Auto-generated method stub
                                    Log.d("thenon", "onNothingSelected: ");
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<CityResponse> call, Throwable t) {
                            Log.e("error_retrofit", call.toString());
                        }
                    });*/

                    Call<StateResponse> call2 = RetrofitClient.getInstance().getApi().getStateApi(Constants.lang);
                    call2.enqueue(new Callback<StateResponse>() {
                        @Override
                        public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                            tripspinnercities = new ArrayList<>();

                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    if (!response.body().getStates().isEmpty()) {
                                        if (!response.body().getStates().get(0).getTranslations().isEmpty()) {
                                            cityList = response.body().getStates();
                                            for (int i = 0; i < cityList.size(); i++) {
                                                Log.e("aaaaaaaaaaaaa", String.valueOf(cityList.size()));
                                                if (cityList.size()!=0) {
                                                    if (!cityList.get(i).getTranslations().isEmpty()) {
                                                        tripspinnercities.add(cityList.get(i).getTranslations().get(0).getName());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.
                                        R.layout.simple_spinner_dropdown_item, tripspinnercities);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerDropDown.setAdapter(adapter);
                                spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int position, long id) {
                                        // Get select item
                                        Context ctxx = parent.getContext();
                                        String selected = spinnerDropDown.getSelectedItem().toString();
                                        if (TripList != null) {
                                            if (!TripList.isEmpty()) {
                                                filterListwithCity3(selected, TripList);
                                            }
                                        }
                                        Log.e("clllllll", selected);
                                        progressBar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        // TODO Auto-generated method stub
                                        Log.d("thenon", "onNothingSelected: ");
                                    }
                                });
                            } else {
                            }
                        }

                        @Override
                        public void onFailure(Call<StateResponse> call, Throwable t) {
                            Log.e("error_retrofit", call.toString());
                        }
                    });
                }

                @Override
                public void onFailure(Call<TransportationRespose> call, Throwable t) {
                    Log.e("error_retrofit", call.toString());
                }
            });
        } else {
            showAlertDialogNoInternetConnection(this);
            Toast.makeText(this, R.string.offline, Toast.LENGTH_SHORT).show();
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void filterListwithCity3(String filtercheck, List<Trip> placesListfilter) {
        TripplacesListSearchFiltered = new ArrayList<>();
        for (int x = 0; x < placesListfilter.size(); x++) {
            if (placesListfilter.get(x).getCity().getTranslations().get(0).getName().equals(filtercheck)) {
                //   Filtersofspinner.add(placesListfilter.get(x));
                TripplacesListSearchFiltered.add(placesListfilter.get(x));
            }
        }
        if (TripplacesListSearchFiltered.isEmpty()) {
            adapter4 = new TripsAdapter(mContext, TripplacesListSearchFiltered);
            recyclerView.setAdapter(adapter4);
            adapter4.notifyDataSetChanged();
        } else {
            adapter4 = new TripsAdapter(mContext, TripplacesListSearchFiltered);
            recyclerView.setAdapter(adapter4);
            adapter4.notifyDataSetChanged();
        }
    }
}
