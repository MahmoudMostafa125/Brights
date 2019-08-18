package com.noorapp.noor;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.adapter.CarsAdapter;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.TransportationModel.Transportation;
import com.noorapp.noor.models.TransportationModel.TransportationRespose;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cars_frag extends Fragment {

    private List<Transportation> carsList, crsWithoutnull;
    RecyclerView recyclerView;
    private CarsAdapter adapter4;
    ProgressBar progressBar;
    TextView txtnodatap;

    public Cars_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cars_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        txtnodatap = view.findViewById(R.id.messagenodatap);
        txtnodatap.setVisibility(View.GONE);
        recyclerView = (RecyclerView) view.findViewById(R.id.crsrec);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        if (internetConnection.internetConnectionAvailable(1000)) {

            Call<TransportationRespose> call = RetrofitClient.getInstance().getApi().TransportationApi(Constants.lang, Constants.status);
            call.enqueue(new Callback<TransportationRespose>() {
                @Override
                public void onResponse(Call<TransportationRespose> call, Response<TransportationRespose> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (!response.body().getTransportations().isEmpty()) {
                                carsList = response.body().getTransportations();
                                crsWithoutnull = new ArrayList<Transportation>();
                                for (int i = 0; i < carsList.size(); i++) {

                                    if (!carsList.get(i).getTranslations().isEmpty()) {
                                        crsWithoutnull.add(carsList.get(i));
                                        Log.e("hawwwwwa", "111111");
                                    }
                                }
                            } else {
                                txtnodatap.setVisibility(View.VISIBLE);
                            }
                        } else {
                            txtnodatap.setVisibility(View.VISIBLE);
                        }
                        if (crsWithoutnull.isEmpty()){
                            txtnodatap.setVisibility(View.VISIBLE);
                        }
                        Log.e("hhhhmmmmmmm", response.body().toString());
                        adapter4 = new CarsAdapter(getActivity(), crsWithoutnull);
                        recyclerView.setAdapter(adapter4);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        txtnodatap.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<TransportationRespose> call, Throwable t) {
                    Log.e("error_retrofit_trans", call.toString());
                }
            });
        } else {
            showAlertDialogNoInternetConnection(view.getContext());
            Toast.makeText(view.getContext(), R.string.offline, Toast.LENGTH_SHORT).show();
        }
    }
}

