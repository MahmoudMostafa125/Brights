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
import com.noorapp.noor.adapter.ReservationAdapter;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.FavouriteModel.FavouriteResponse;
import com.noorapp.noor.models.ReservationModel.ReservationResponse;
import com.noorapp.noor.models.ReservationsModel.Place;
import com.noorapp.noor.models.ReservationsModel.ReservationsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationPlace_Fragment extends Fragment {
    private List<Place> placesListFavourite;
    public static RecyclerView recyclerView;
    public ReservationAdapter adapter4;
    ProgressBar progressBar;
    TextView txtnodatap;

    public ReservationPlace_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_reservation_place_, container, false);
        recyclerView = view.findViewById(R.id.dicsplaces);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        progressBar = view.findViewById(R.id.progressBar2);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        txtnodatap = view.findViewById(R.id.messagenodatap);
        txtnodatap.setVisibility(View.GONE);

        String API = sharedPref.getString("api_token", null);

        Log.e("APIAAA", API);
        if (internetConnection.internetConnectionAvailable(1000)) {

            Call<ReservationsResponse> call = RetrofitClient.getInstance().getApi().
                    ReservationApi(API, Constants.lang);
            call.enqueue(new Callback<ReservationsResponse>() {
                @Override
                public void onResponse(Call<ReservationsResponse> call, Response<ReservationsResponse> response) {
                        if (response.isSuccessful()) {
                        if (response.body().getResponse().toString().equals("true")) {
                            placesListFavourite = new ArrayList<>();
                            for (int i = 0; i < response.body().getReservations().getPlaces().size(); i++) {
                                placesListFavourite.add(response.body().getReservations().getPlaces().get(i));
                            }
                            adapter4 = new ReservationAdapter(getActivity(), placesListFavourite);
                            recyclerView.setAdapter(adapter4);
                            if (placesListFavourite.size() == 0){
                                txtnodatap.setVisibility(View.VISIBLE);
                            }
                        } else {
                            txtnodatap.setVisibility(View.VISIBLE);
                        }
                        progressBar.setVisibility(getView().GONE);
                    } else {
                        progressBar.setVisibility(getView().GONE);
                        txtnodatap.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<ReservationsResponse> call, Throwable t) {
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
