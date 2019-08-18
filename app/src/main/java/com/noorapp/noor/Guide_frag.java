package com.noorapp.noor;


import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.adapter.PlacesAdapter2;
import com.noorapp.noor.adapter.PlacesNearAdaptercard;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.CityModel.Cite;
import com.noorapp.noor.models.CityModel.CityResponse;
import com.noorapp.noor.models.DefaultResponse;
import com.noorapp.noor.models.GuideModel.GuideResponse;
import com.noorapp.noor.models.Place;
import com.noorapp.noor.models.PlaceFilterModel;
import com.noorapp.noor.models.PlacesDetailsModel.PlaceDetailsResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.WINDOW_SERVICE;
import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;


public class Guide_frag extends Fragment {

    private PlacesAdapter2 adapter;
    public static RecyclerView recyclerView;
    private List<com.noorapp.noor.models.GuideModel.Guide> placesList;
    private static Context context = null;
    public static List<Place> Filtersofspinner;
    public static List<String> spinnercities;
    ProgressBar progressBar3;
    TextView txtnodata;

    public Guide_frag() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guide_frag, container, false);
        recyclerView = view.findViewById(R.id.recuclerguide);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        context = getActivity();
        Filtersofspinner = new ArrayList<Place>();
        txtnodata = view.findViewById(R.id.messagenodatap);
        return view;
    }

    public static int getScreenHeightInPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int height = dm.widthPixels;
        Log.e("responseWidth", String.valueOf(height));
        return height;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar3 = view.findViewById(R.id.progressBar3);

        getScreenHeightInPixels(getContext());

        if (internetConnection.internetConnectionAvailable(1000)) {

            Call<GuideResponse> call = RetrofitClient.getInstance().getApi().getGuideApi(Constants.lang, Constants.status);

            call.enqueue(new Callback<GuideResponse>() {
                @Override
                public void onResponse(Call<GuideResponse> call, Response<GuideResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Log.e("here", "guide response");
                            if (!response.body().getGuide().isEmpty()) {
                                placesList = response.body().getGuide();
                                adapter = new PlacesAdapter2(getActivity(), placesList, getScreenHeightInPixels(getContext()));
                                recyclerView.setAdapter(adapter);
                            } else {
                                txtnodata.setVisibility(View.VISIBLE);
                            }
                        } else {
                            txtnodata.setVisibility(View.VISIBLE);
                        }
                    } else {
                        txtnodata.setVisibility(View.VISIBLE);
                    }
                    progressBar3.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<GuideResponse> call, Throwable t) {
                    Log.e("fail", "FAILL");
                }
            });
        } else {
            showAlertDialogNoInternetConnection(view.getContext());
            Toast.makeText(view.getContext(), R.string.offline, Toast.LENGTH_SHORT).show();
        }
    }

}
