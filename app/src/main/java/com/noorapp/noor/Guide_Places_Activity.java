package com.noorapp.noor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;
import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.adapter.CategoryFilterAdaptercard;
import com.noorapp.noor.adapter.GuidePlacesAdapter;
import com.noorapp.noor.adapter.PlacesNearAdaptercard;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.CategoryFilterModel;
import com.noorapp.noor.models.CategoryModel.Category;
import com.noorapp.noor.models.CategoryModel.CategoryResponse;
import com.noorapp.noor.models.CityModel.Cite;
import com.noorapp.noor.models.CityModel.CityResponse;
import com.noorapp.noor.models.GuideModel.GuideResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.noorapp.noor.models.GuideModel.Place;
import com.noorapp.noor.models.PlaceFilterModel;
import com.noorapp.noor.models.StateModel.State;
import com.noorapp.noor.models.StateModel.StateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.noorapp.noor.Map_frag.getlist;
import static com.noorapp.noor.Map_frag.refreshmap;
import static com.noorapp.noor.Utility.App.getContext;
import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;

public class Guide_Places_Activity extends AppCompatActivity {

    private Context mContext;
    int place_id;
    String place_name;
    static RecyclerView recyclerView;
    RecyclerView recyclerViewFilter;
    private List<Place> placesList, placesticketListrecived;
    private List<Category> CategroyFilter;
    private static GuidePlacesAdapter adapter4;
    private CategoryFilterAdaptercard categoryFilterAdaptercard;
    Context context;
    ImageView imageView;
    TextView toolbartitle;
    ProgressBar progressBar;
    TextView txtnodatap;
    FrameLayout ClickAnyWhere;
    CardView cardViewfilter;
    public boolean ismenuesearch = true;

    ///////////////////////////////////////
    private List<PlaceFilterModel> placesnamefilter;
    private List<String> placesnamefilterstr;
    public static List<String> spinnercities;
    private List<State> cityList;
    Spinner spinnerDropDown;
    //////////////////////////////
    public static List<Place> placesListSearchFiltered;
    public static List<Place> Filtersofspinner;
    public static List<Place> Filtersofside;
    public static List<CategoryFilterModel> categoryFilterList;

    public static String Cityfilterstr;
    public static List<String> sideListstr;


    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_attraction, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide__places_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        mContext = this;
/////////////////////////

        sideListstr = new ArrayList<>();
        placesListSearchFiltered = new ArrayList<Place>();
        Filtersofspinner = new ArrayList<Place>();
        Filtersofside = new ArrayList<Place>();

        /////////////////////////
        ClickAnyWhere = findViewById(R.id.clickanywhere);

        txtnodatap = findViewById(R.id.messagenodatap);
        txtnodatap.setVisibility(View.GONE);
        imageView = findViewById(R.id.back);
        toolbartitle = findViewById(R.id.toolbar_title);
        progressBar = findViewById(R.id.progressBar2);
        spinnerDropDown = findViewById(R.id.spinner1);
        cardViewfilter = findViewById(R.id.cardfilteration);
        recyclerView = findViewById(R.id.placesrec);
        recyclerViewFilter = findViewById(R.id.scrollfilter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerViewFilter.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            place_id = bundle.getInt("placeID");
            place_name = bundle.getString("placename");
        }
        toolbartitle.setText(place_name);



        if (internetConnection.internetConnectionAvailable(1000)) {

            Call<GuideResponse> call = RetrofitClient.getInstance().getApi().GuideDetailsAPI(String.valueOf(place_id),
                    Constants.lang, Constants.status);
            call.enqueue(new Callback<GuideResponse>() {
                @Override
                public void onResponse(Call<GuideResponse> call, Response<GuideResponse> response) {
                    if (response.isSuccessful()) {
                        if (!response.body().getGuide().isEmpty()) {
                            if (!response.body().getGuide().get(0).getPlaces().isEmpty()) {
                                placesList = response.body().getGuide().get(0).getPlaces();
                                placesticketListrecived = new ArrayList<Place>();
                                for (int i = 0; i < placesList.size(); i++) {
                                    if (!placesList.get(i).getTranslations().isEmpty()) {
                                        placesticketListrecived.add(placesList.get(i));
                                    }
                                }
                                if (placesticketListrecived.isEmpty()) {
                                    txtnodatap.setVisibility(View.VISIBLE);

                                }
                            } else {
                                txtnodatap.setVisibility(View.VISIBLE);
                            }
                        } else {
                            txtnodatap.setVisibility(View.VISIBLE);

                        }

                        placesnamefilterstr = new ArrayList<String>();
                        placesnamefilter = new ArrayList<PlaceFilterModel>();
                        spinnercities = new ArrayList<>();
                        cityList = new ArrayList<>();


///////////////////////////////////////////////////
                        /*Call<CityResponse> call2 = RetrofitClient.getInstance().getApi().CityResponseAPI(String.valueOf(1), Constants.lang, Constants.status, Constants.ticket);
                        call2.enqueue(new Callback<CityResponse>() {
                            @Override
                            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {

                                if (response.isSuccessful()) {
                                    if (!response.body().getCountries().isEmpty()) {
                                        cityList = response.body().getCountries().get(0).getCites();

                                        for (int i = 0; i < cityList.size(); i++) {
                                            if (!cityList.get(i).getTranslations().isEmpty()) {
                                                spinnercities.add(cityList.get(i).getTranslations().get(0).getName());
                                            }
                                        }
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.
                                            R.layout.simple_spinner_dropdown_item, spinnercities);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerDropDown.setAdapter(adapter);
                                    spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view,
                                                                   int position, long id) {
                                            // Get select item
                                            Context ctxx = parent.getContext();
                                            String selected = spinnerDropDown.getSelectedItem().toString();
                                            if (placesticketListrecived != null) {
                                                if (!placesticketListrecived.isEmpty()) {
                                                    filterListwithCity2(selected, placesticketListrecived);
                                                }
                                            }
                                            progressBar.setVisibility(view.GONE);
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
                            public void onFailure(Call<CityResponse> call, Throwable t) {
                                Log.e("error_retrofit", call.toString());
                            }
                        });*/

                        Call<StateResponse> call2 = RetrofitClient.getInstance().getApi().getStateApi(Constants.lang);
                        call2.enqueue(new Callback<StateResponse>() {
                            @Override
                            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {

                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        if (!response.body().getStates().isEmpty()) {
                                            if (!response.body().getStates().get(0).getTranslations().isEmpty()) {
                                                cityList = response.body().getStates();
                                                for (int i = 0; i < cityList.size(); i++) {
                                                    Log.e("aaaaaaaaaaaaa", String.valueOf(cityList.size()));
                                                    if (cityList.size()!=0) {
                                                        if (!cityList.get(i).getTranslations().isEmpty()) {
                                                            spinnercities.add(cityList.get(i).getTranslations().get(0).getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.
                                            R.layout.simple_spinner_dropdown_item, spinnercities);
                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerDropDown.setAdapter(adapter);
                                    spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view,
                                                                   int position, long id) {
                                            // Get select item
                                            Context ctxx = parent.getContext();
                                            String selected = spinnerDropDown.getSelectedItem().toString();
                                            filterListwithCity2(selected, placesticketListrecived);
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
/////////////////////////////////////////////
                        Call<CategoryResponse> call3 = RetrofitClient.getInstance().getApi().CategoryAPI(Constants.lang);
                        call3.enqueue(new Callback<CategoryResponse>() {
                            @Override
                            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                                if (response.isSuccessful()) {
                                    if (!response.body().getCategories().isEmpty()) {
                                        CategroyFilter = new ArrayList<>();
                                        categoryFilterList= new ArrayList<>();
                                        CategroyFilter = response.body().getCategories();
                                        for (int i = 0; i < CategroyFilter.size(); i++) {
                                            categoryFilterList.add
                                                    (new CategoryFilterModel
                                                            (false, CategroyFilter.get(i).getTranslations().get(0).getName()
                                                                    , String.valueOf(CategroyFilter.get(i).getTranslations().get(0).getCategoryId())));
                                        }
                                        categoryFilterAdaptercard =
                                                new CategoryFilterAdaptercard(mContext, categoryFilterList, placesticketListrecived);
                                        recyclerViewFilter.setAdapter(categoryFilterAdaptercard);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                                Log.e("error_retrofit", call.toString());
                            }
                        });

//////////////////////////////////////////////
                        progressBar.setVisibility(View.GONE);
                    } else {
                        txtnodatap.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<GuideResponse> call, Throwable t) {
                    Log.e("error_retrofit", call.toString());
                }
            });
        } else {
            showAlertDialogNoInternetConnection(this.getBaseContext());
            Toast.makeText(this, R.string.offline, Toast.LENGTH_SHORT).show();
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ClickAnyWhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewFilter.setVisibility(View.GONE);
                cardViewfilter.setVisibility(View.GONE);
                ClickAnyWhere.setVisibility(View.GONE);
                ismenuesearch = true;
            }
        });
    }
    public static void filterList2(List<String> filtercheck, List<Place> placesListfilter) {
        placesListSearchFiltered.clear();
        sideListstr.clear();
        if (placesListfilter !=null) {
            for (int i = 0; i < filtercheck.size(); i++) {
                sideListstr.add(filtercheck.get(i));
                for (int x = 0; x < placesListfilter.size(); x++) {
                    if (placesListfilter.get(x).getCategory_id().equals(filtercheck.get(i))) {
                        placesListSearchFiltered.add(placesListfilter.get(x));
                    }
                }
            }
        }

        if (placesListSearchFiltered.isEmpty()) {
            if (placesListfilter !=null) {
                for (int i = 0; i < filtercheck.size(); i++) {
                    for (int x = 0; x < placesListfilter.size(); x++) {
                        if (placesListfilter.get(x).getCategory_id().equals(filtercheck.get(i))) {
                            Filtersofspinner.add(placesListfilter.get(x));
                            placesListSearchFiltered.add(placesListfilter.get(x));
                        }
                    }
                }
            }

            adapter4= new GuidePlacesAdapter(getContext(), placesListSearchFiltered);
            recyclerView.setAdapter(adapter4);
            adapter4.notifyDataSetChanged();
        } else {
            adapter4 = new GuidePlacesAdapter(getContext(), placesListSearchFiltered);
            recyclerView.setAdapter(adapter4);
            adapter4.notifyDataSetChanged();
        }
        if (sideListstr.isEmpty()) {
            filterListwithCity2(Cityfilterstr, placesListfilter);
        }
    }

    public static void filterListwithCity2(String filtercheck, List<Place> placesListfilter) {
        placesListSearchFiltered.clear();
        Cityfilterstr = filtercheck;
        if (!sideListstr.isEmpty()) {
            for (int i = 0; i < sideListstr.size(); i++) {
                for (int x = 0; x < placesListfilter.size(); x++) {
                    if (placesListfilter.get(x).getCategory_id().equals(filtercheck)) {

                        Filtersofspinner.add(placesListfilter.get(x));
                        placesListSearchFiltered.add(placesListfilter.get(x));
                    }
                }
            }
        } else {
            if (placesListfilter !=null) {
            for (int x = 0; x < placesListfilter.size(); x++) {
                if (placesListfilter.get(x).getCity().getTranslations().get(0).getName().equals(filtercheck)) {
                    Filtersofspinner.add(placesListfilter.get(x));
                    placesListSearchFiltered.add(placesListfilter.get(x));
                }
            }
        }
        }


        if (placesListSearchFiltered.isEmpty()) {

            adapter4= new GuidePlacesAdapter(getContext(), placesListSearchFiltered);
            recyclerView.setAdapter(adapter4);
            adapter4.notifyDataSetChanged();
        } else {

            adapter4= new GuidePlacesAdapter(getContext(), placesListSearchFiltered);
            recyclerView.setAdapter(adapter4);
            adapter4.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            if (ismenuesearch) {
                recyclerViewFilter.setVisibility(View.VISIBLE);
                cardViewfilter.setVisibility(View.VISIBLE);
                ClickAnyWhere.setVisibility(View.VISIBLE);
                ismenuesearch = false;
            } else {
                recyclerViewFilter.setVisibility(View.GONE);
                cardViewfilter.setVisibility(View.GONE);
                ClickAnyWhere.setVisibility(View.GONE);
                ismenuesearch = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
