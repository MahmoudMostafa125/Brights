package com.noorapp.noor;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.adapter.PlacesNearAdaptercard;
import com.noorapp.noor.adapter.TripsAdapter;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.FavouriteModel.FavouriteResponse;
import com.noorapp.noor.models.Place;
import com.noorapp.noor.models.TransportationModel.Trip;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteTrip_Fragment extends Fragment {

    private List<Trip> placesListFavouriteT;
    public static RecyclerView recyclerViewT;
    public TripsAdapter adapter4T;
    ProgressBar progressBarT;
    TextView txtnodataf;
    public FavouriteTrip_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_favourite_trip_, container, false);
        txtnodataf = view.findViewById(R.id.messagenodatapf);
        txtnodataf.setVisibility(View.GONE);
        recyclerViewT = view.findViewById(R.id.dicsplaces);
        recyclerViewT.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        progressBarT = view.findViewById(R.id.progressBar2);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String API = sharedPref.getString("api_token", null);

        Log.e("APIAAA", API);
        if (internetConnection.internetConnectionAvailable(1000)) {

            Call<FavouriteResponse> call = RetrofitClient.getInstance()
                    .getApi().FavouriteApi(API/*"d35dd4dba49dbf19586a7b7e955903edSCvYEgyWg0nYJB14UWCYJSbLq5isUlyE"*/, Constants.lang, "T");
            call.enqueue(new Callback<FavouriteResponse>() {
                @Override
                public void onResponse(Call<FavouriteResponse> call, Response<FavouriteResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getResponse().toString().equals("true")) {
                            placesListFavouriteT = response.body().getFavorites().getTransportations().get(0).getTransportation();
                            adapter4T = new TripsAdapter(view.getContext(), placesListFavouriteT);
                            recyclerViewT.setAdapter(adapter4T);
                        } else {
                            txtnodataf.setVisibility(View.VISIBLE);
                        }

                    } else {
                        txtnodataf.setVisibility(View.VISIBLE);
                    }
                    progressBarT.setVisibility(getView().GONE);
                    txtnodataf.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<FavouriteResponse> call, Throwable t) {
                    Log.e("error_retrofit", call.toString());
                }
            });
        } else {
            showAlertDialogNoInternetConnection(view.getContext());
            Toast.makeText(view.getContext(),  R.string.offline, Toast.LENGTH_SHORT).show();
        }
        return view;

    }

}
