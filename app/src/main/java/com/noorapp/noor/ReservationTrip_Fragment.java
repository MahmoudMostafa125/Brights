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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.adapter.ReservationTrip_Adapter;
import com.noorapp.noor.adapter.TripsAdapter;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.FavouriteModel.FavouriteResponse;
import com.noorapp.noor.models.ReservationModel.ReservationResponse;
import com.noorapp.noor.models.ReservationsModel.ReservationsResponse;
import com.noorapp.noor.models.ReservationsModel.Trip;

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
public class ReservationTrip_Fragment extends Fragment {

    private List<Trip> placesListFavouriteT;
    public static RecyclerView recyclerViewT;
    public ReservationTrip_Adapter adapter4T;
    ProgressBar progressBarT;
    TextView txtnodata;


    public ReservationTrip_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_reservation_trip_, container, false);

        txtnodata = view.findViewById(R.id.messagenodata);
        txtnodata.setVisibility(View.GONE);
        recyclerViewT = view.findViewById(R.id.dicsplaces);
        recyclerViewT.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        progressBarT = view.findViewById(R.id.progressBar2);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String API = sharedPref.getString("api_token", null);

        Log.e("APIAAA", API);
        if (internetConnection.internetConnectionAvailable(1000)) {

            Call<ReservationsResponse> call = RetrofitClient.getInstance()
                    .getApi().ReservationApi(API/*"f09b6b00e5649c2dfcdb8f233cfffcd1niiacOXQAGxsODCnvHeSD0rEYECBGqRx"*/, Constants.lang);
            call.enqueue(new Callback<ReservationsResponse>() {
                @Override
                public void onResponse(Call<ReservationsResponse> call, Response<ReservationsResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getResponse().toString().equals("true")) {
                            placesListFavouriteT = new ArrayList<>();
                            for (int i = 0; i < response.body().getReservations().getTrips().size(); i++) {
                                placesListFavouriteT.add(response.body().getReservations().getTrips().get(i));
                            }
                            adapter4T = new ReservationTrip_Adapter(getActivity(), placesListFavouriteT);
                            recyclerViewT.setAdapter(adapter4T);
                            if (placesListFavouriteT.size() == 0){
                                txtnodata.setVisibility(View.VISIBLE);
                            }
                        } else {
                            txtnodata.setVisibility(View.VISIBLE);
                        }

                    } else {
                        txtnodata.setVisibility(View.VISIBLE);
                    }
                    progressBarT.setVisibility(getView().GONE);
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
