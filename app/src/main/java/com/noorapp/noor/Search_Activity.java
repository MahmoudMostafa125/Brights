package com.noorapp.noor;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;
import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.adapter.PlacesNearAdapter;
import com.noorapp.noor.adapter.PlacesNearAdaptercard;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.DefaultResponse;
import com.noorapp.noor.models.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;

public class Search_Activity extends AppCompatActivity {
    private Context mContext;

    private List<Place> placesListSearch;
    private List<Place> placesListWithout;

    private PlacesNearAdaptercard adapter;
    private RecyclerView recyclerViews;
    ImageView imgsearch;
    EditText txtsearch;
    Context cox;
    ProgressBar progressBar;
    TextView txtnodatap;

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        txtnodatap = findViewById(R.id.messagenodatap);
        txtnodatap.setVisibility(View.GONE);
        mContext = this;
        cox = this;
        progressBar = findViewById(R.id.prgs);
        recyclerViews = findViewById(R.id.research);
        imgsearch = findViewById(R.id.searchorder);
        txtsearch = findViewById(R.id.edit);
        progressBar.setVisibility(View.GONE);
        recyclerViews.setLayoutManager(new LinearLayoutManager(cox, LinearLayoutManager.VERTICAL, false));
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtsearch.getText().toString().trim().length() != 0) {
                    progressBar.setVisibility(View.VISIBLE);

                    if (internetConnection.internetConnectionAvailable(1000)) {

                        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().SearchApi(String.valueOf(txtsearch.getText()), Constants.lang,Constants.status);
                        call.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                if (response.isSuccessful()) {
                                    placesListSearch = response.body().getPlaces();
                                    placesListWithout = new ArrayList<Place>();
                                    for (int i = 0; i < placesListSearch.size(); i++) {
                                        if (!placesListSearch.get(i).getTranslations().isEmpty()) {
                                            placesListWithout.add(placesListSearch.get(i));
                                        }
                                    }
                                    adapter = new PlacesNearAdaptercard(cox, placesListWithout);
                                    recyclerViews.setAdapter(adapter);
                                    progressBar.setVisibility(View.GONE);
                                    if (placesListWithout.size() == 0){
                                        txtnodatap.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    Toast.makeText(Search_Activity.this, "@string/there_is_no_data", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    txtnodatap.setVisibility(View.VISIBLE);
                                }

                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                Log.e("error_retrofit", call.toString());
                            }
                        });
                    } else {
                        showAlertDialogNoInternetConnection(v.getContext());
                        Toast.makeText(v.getContext(),  R.string.offline, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(cox, "Please type what you want to search for ..", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (txtsearch.getText().toString().trim().length() != 0) {
                        progressBar.setVisibility(View.VISIBLE);

                        if (internetConnection.internetConnectionAvailable(1000)) {

                            Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().SearchApi(String.valueOf(txtsearch.getText()), Constants.lang,Constants.status);
                            call.enqueue(new Callback<DefaultResponse>() {
                                @Override
                                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                    if (response.isSuccessful()) {
                                        placesListSearch = response.body().getPlaces();
                                        placesListWithout = new ArrayList<Place>();
                                        for (int i = 0; i < placesListSearch.size(); i++) {
                                            if (!placesListSearch.get(i).getTranslations().isEmpty()) {
                                                placesListWithout.add(placesListSearch.get(i));
                                            }
                                        }
                                        adapter = new PlacesNearAdaptercard(cox, placesListWithout);
                                        recyclerViews.setAdapter(adapter);
                                        progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(Search_Activity.this, "@string/there_is_no_data", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                    Log.e("error_retrofit", call.toString());
                                }
                            });
                        } else {
                            showAlertDialogNoInternetConnection(v.getContext());
                            Toast.makeText(v.getContext(),  R.string.offline, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(cox, "Please type what you want to search for ..", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

    }
}
