package com.noorapp.noor;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.adapter.PlacesNearAdapter;
import com.noorapp.noor.adapter.PlacesTicketAdapter;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.DefaultResponse;
import com.noorapp.noor.models.Guide;
import com.noorapp.noor.models.Place;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.app.ActivityCompat.invalidateOptionsMenu;
import static com.noorapp.noor.Discover_frag.txtNodatad;
import static com.noorapp.noor.MainActivity.menuState2;
import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class Recent_frag extends Fragment {
    View view;
    private RecyclerView recyclerView, recyclerView2;
    private List<Guide> resultsList;
    private List<Place> placesList;
    private List<Place> placesListWithBest;
    private List<Place> placesListWithNear;

    private PlacesNearAdapter adapter;
    private PlacesNearAdapter adapter2;
    private PlacesTicketAdapter adapter3;

    ImageView places, guides;
    ImageButton cars;
    float[] distance;
    ProgressBar progressBar, progressBar2;
    SendMessage SM;
    SendMessage2 SM2;
    private ViewPager viewPager;
    BottomNavigationView navigation;
    Context context;
    ////////////////
    TextView txtNoData, txtNoData2;


    ////////////////
    public Recent_frag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
  /*      MenuItem item = menu.findItem(R.id.action_help);
        item.setVisible(true);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_recent_frag, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerrecent);
        recyclerView2 = view.findViewById(R.id.recyclernear);
        viewPager = view.findViewById(R.id.viewpager);
        navigation = view.findViewById(R.id.navigation);
        places = view.findViewById(R.id.imageViewplaces);
        guides = view.findViewById(R.id.imageViewguide);
        cars = view.findViewById(R.id.imageButton);
        progressBar = view.findViewById(R.id.prgs);
        progressBar2 = view.findViewById(R.id.prgs2);
        txtNoData = view.findViewById(R.id.messagenodatap);
        txtNoData2 = view.findViewById(R.id.messagenodatap2);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView2.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        SharedPreferences sharedPref = context.getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String value = sharedPref.getString("email", null);
        double currentLat = 0.0;
        double currentLng = 0.0;
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (statusOfGPS) {
            GPSHelper gpsHelper = new GPSHelper(getActivity());
            LatLng currentLatLng = gpsHelper.getMyLocation();
            currentLat = currentLatLng.latitude;
            currentLng = currentLatLng.longitude;
        } else {

        }
        ///////////////
        if (internetConnection.internetConnectionAvailable(1000)) {
            Call<DefaultResponse> call = null;
            if (currentLat != 0.0) {
                call = RetrofitClient.getInstance().getApi().
                        noorApi(String.valueOf(currentLat) + "," + String.valueOf(currentLng), Constants.lang,
                                Constants.status);
            } else {
                call = RetrofitClient.getInstance().getApi().
                        noorApi("", Constants.lang, Constants.status);
            }

            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getPlaces() != null) {
                            placesList = response.body().getPlaces();
                            placesListWithBest = new ArrayList<Place>();
                            for (int i = 0; i < placesList.size(); i++) {
                                if (Integer.valueOf(placesList.get(i).getBest()) == 1 && !placesList.get(i).getTranslations().isEmpty()) {
                                    placesListWithBest.add(placesList.get(i));
                                }
                            }
                            placesListWithNear = new ArrayList<Place>();
                            for (int i = 0; i < placesList.size(); i++) {
                                if (!placesList.get(i).getTranslations().isEmpty()) {
                                    placesListWithNear.add(placesList.get(i));
                                }
                            }
                            SM.sendData(placesList);
                            adapter = new PlacesNearAdapter(getActivity(), placesListWithBest);
                            recyclerView.setAdapter(adapter);
                            if (response.body().getPlaces().get(0).getDistance() != null) {
                                Collections.sort(placesList, new SortbyNearest());
                            }
                            adapter2 = new PlacesNearAdapter(getActivity(), placesListWithNear);
                            recyclerView2.setAdapter(adapter2);
                        } else {
                            txtNoData.setVisibility(View.VISIBLE);
                            txtNoData2.setVisibility(View.VISIBLE);
                            txtNodatad.setVisibility(View.VISIBLE);
                            Discover_frag.progressBar.setVisibility(View.GONE);


                        }
                    } else {
                        txtNoData.setVisibility(View.VISIBLE);
                        txtNoData2.setVisibility(View.VISIBLE);
                        txtNodatad.setVisibility(View.VISIBLE);
                        Discover_frag.progressBar.setVisibility(View.GONE);

                    }
                    progressBar.setVisibility(View.GONE);
                    progressBar2.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                    Log.e("error_retrofit", call.toString());
                }
            });
        } else {
            showAlertDialogNoInternetConnection(view.getContext());
            Toast.makeText(view.getContext(), R.string.offline, Toast.LENGTH_SHORT).show();
        }
        places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navigation.setSelectedItemId(R.id.nav_sent);
               /* View view = MainActivity.navigation.findViewById(R.id.nav_sent);
                view.performClick();
                MainActivity.TAG = "discover";*/
            }
        });
        guides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navigation.setSelectedItemId(R.id.nav_trash);
               /* View view = MainActivity.navigation.findViewById(R.id.nav_sent);
                view.performClick();
                MainActivity.TAG = "discover";*/
            }
        });
        cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navigation.setSelectedItemId(R.id.nav_car);
               /* View view = MainActivity.navigation.findViewById(R.id.nav_sent);
                view.performClick();
                MainActivity.TAG = "discover";*/
            }
        });
    }


    class SortbyNearest implements Comparator<Place> {
        // Used for sorting in ascending order of
        // Best number
        public int compare(Place a, Place b) {
            // return (Double.parseDouble(a.getDistance().toString()) -Double.parseDouble( b.getDistance().toString()));
            if (a.getDistance() != null) {
                Log.e("bnbnbn", "no null");
                double delta = a.getDistance() - b.getDistance();
                if (delta > 0.00001) return 1;
                if (delta < -0.00001) return -1;
            }
            return 0;
        }
    }

    interface SendMessage {
        void sendData(List<Place> placesList);

    }

    interface SendMessage2 {
        void sendData2(List<Place> placesList);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            SM2 = (SendMessage2) getActivity();
            SM = (SendMessage) getActivity();

        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}
