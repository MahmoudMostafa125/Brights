package com.noorapp.noor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.noorapp.noor.adapter.PlacesFilterAdaptercard;
import com.noorapp.noor.adapter.PlacesNearAdaptercard;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.CityModel.Cite;
import com.noorapp.noor.models.CityModel.CityResponse;
import com.noorapp.noor.models.DefaultResponse;
import com.noorapp.noor.models.GuideModel.GuideResponse;
import com.noorapp.noor.models.Place;
import com.noorapp.noor.models.PlaceFilterModel;
import com.noorapp.noor.models.StateModel.State;
import com.noorapp.noor.models.StateModel.StateResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.noorapp.noor.Map_frag.getlist;
import static com.noorapp.noor.Map_frag.refreshmap;
import static com.noorapp.noor.Utility.utility.GPSpermission;

public class Discover_frag extends Fragment {
    List<Place> placesticketList;
    Button button1, button2;
    public static RecyclerView recyclerView, recyclerViewFilter;
    List<Place> placesticketListrecived;
    public PlacesNearAdaptercard adapter4;
    public PlacesFilterAdaptercard adapterfilter;
    ////////////////////////////////////////////////////
    public static List<Place> placesListSearchFiltered;
    public static List<Place> Filtersofspinner;
    public static List<Place> Filtersofside;
    public static List<Place> AllFilters;

    private List<com.noorapp.noor.models.GuideModel.Guide> placesListfff;
    ////////////////////////////////////////////////////////
    SendMplaces SMP;
    public static ProgressBar progressBar;

    private static Context context = null;
    Fragment fragment = new Map_frag();
    public static FragmentTransaction ft, ft2;

    public boolean iscreated = false;
    public boolean ismenuesearch = true;
    ScrollView filterscroll;
    ///////////
    private List<PlaceFilterModel> placesnamefilter;
    private List<String> placesnamefilterstr;
    public static List<String> spinnercities;
    public static List<Integer> spinnercitiesID;
    private List<State> cityList;
    public static PlacesNearAdaptercard adapterFiltered;
    public static String Cityfilterstr;
    public static List<String> sideListstr;
    public static TextView txtNodatad;

    ///////////
    //////////////////////////////
    Spinner spinnerDropDown;
    //////////////////////////////
    CardView cardViewfilter;
    ////////////////////
    FrameLayout ClickAnyWhere;

    public Discover_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_discover_frag, container, false);
        //GPSpermission(view.getContext());
        context = getActivity();
        placesListfff = new ArrayList<>();
        placesListSearchFiltered = new ArrayList<Place>();
        Filtersofspinner = new ArrayList<Place>();
        Filtersofside = new ArrayList<Place>();
        sideListstr = new ArrayList<>();
        txtNodatad = view.findViewById(R.id.messagenodatap);
        spinnerDropDown = view.findViewById(R.id.spinner1);
        button1 = view.findViewById(R.id.lbtn);
        button2 = view.findViewById(R.id.rbtn);
        progressBar = view.findViewById(R.id.progressBar2);
        recyclerView = view.findViewById(R.id.dicsplaces);
        recyclerViewFilter = view.findViewById(R.id.scrollfilter);
        cardViewfilter = view.findViewById(R.id.cardfilteration);
        recyclerViewFilter.setVisibility(view.GONE);
        cardViewfilter.setVisibility(view.GONE);
        final boolean ismenuesearch = true;
        ClickAnyWhere = view.findViewById(R.id.clickanywhere);
        ///////////////////////////////////////////////////////////////
        //this function make onOptionsItemSelected() works in Fragment
        setHasOptionsMenu(true);
        ///////////

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String value = prefs.getString("Lang", null);
        if (value != null) {
            if (!value.equals("ar")) {
                button2.setBackgroundResource(R.drawable.floatsetr);
                button1.setBackgroundResource(R.drawable.floatbtn);

                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        button2.setBackgroundResource(R.drawable.floatbtnr);
                        button1.setBackgroundResource(R.drawable.floatset);
                        if (ft == null) {
                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frsgplacer, fragment);
                            ft.commit();
                            iscreated = true;
                        } else {
                            ft = getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            // ft.show(fragment);
                            //ft.commit();
                            ft.detach(fragment)
                                    .attach(fragment)
                                    .show(fragment)
                                    .commit();
                        }
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        button1.setBackgroundResource(R.drawable.floatbtn);
                        button2.setBackgroundResource(R.drawable.floatsetr);
                        View mapview = getActivity().findViewById(R.id.frsgplacer);
                        if (iscreated) {
                            ft = getFragmentManager().beginTransaction();
                            ft = getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            ft.hide(fragment);
                            ft.commit();
                            Log.e("ana", "e5feha");
                        }
                        //ft.hide (fragment);
                        // fragment.getView().setVisibility(View.GONE);
                    }
                });
            } else if (value.equals("ar")) {
                button2.setBackgroundResource(R.drawable.floatset);
                button1.setBackgroundResource(R.drawable.floatbtnr);

                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        button2.setBackgroundResource(R.drawable.floatbtn);
                        button1.setBackgroundResource(R.drawable.floatsetr);
                        if (ft == null) {
                            ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.frsgplacer, fragment);
                            ft.commit();
                            iscreated = true;
                        } else {
                            ft = getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            // ft.show(fragment);
                            //ft.commit();
                            ft.detach(fragment)
                                    .attach(fragment)
                                    .show(fragment)
                                    .commit();
                        }
                    }
                });
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        button1.setBackgroundResource(R.drawable.floatbtnr);
                        button2.setBackgroundResource(R.drawable.floatset);
                        View mapview = getActivity().findViewById(R.id.frsgplacer);
                        if (iscreated) {
                            ft = getFragmentManager().beginTransaction();
                            ft = getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            ft.hide(fragment);
                            ft.commit();
                        }
                        //ft.hide (fragment);
                        // fragment.getView().setVisibility(View.GONE);
                    }
                });
            }

        } else {
            button2.setBackgroundResource(R.drawable.floatsetr);
            button1.setBackgroundResource(R.drawable.floatbtn);

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    button2.setBackgroundResource(R.drawable.floatbtnr);
                    button1.setBackgroundResource(R.drawable.floatset);
                    if (ft == null) {
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frsgplacer, fragment);
                        ft.commit();
                        iscreated = true;
                    } else {
                        ft = getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        // ft.show(fragment);
                        //ft.commit();
                        ft.detach(fragment)
                                .attach(fragment)
                                .show(fragment)
                                .commit();
                    }
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    button1.setBackgroundResource(R.drawable.floatbtn);
                    button2.setBackgroundResource(R.drawable.floatsetr);
                    View mapview = getActivity().findViewById(R.id.frsgplacer);
                    if (iscreated) {
                        ft = getFragmentManager().beginTransaction();
                        ft = getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        ft.hide(fragment);
                        ft.commit();
                    }
                    //ft.hide (fragment);
                    // fragment.getView().setVisibility(View.GONE);
                }
            });
        }
        return view;
    }

    ///////////////////////////////////////////////////////////
    ///filter with list in side by category name {park ,zoo,...
    public static void filterList(List<String> filtercheck, List<Place> placesListfilter) {
        placesListSearchFiltered.clear();
        sideListstr.clear();
        for (int i = 0; i < filtercheck.size(); i++) {
            sideListstr.add(filtercheck.get(i));
            for (int x = 0; x < placesListfilter.size(); x++) {
                if (placesListfilter.get(x).getGuide().get(0)
                        .getTranslations().get(0).getName()
                        .equals(filtercheck.get(i)) &&
                        placesListfilter.get(x).getCity().getTranslations().get(0).getName().equals(Cityfilterstr)) {
                    placesListSearchFiltered.add(placesListfilter.get(x));
                }
            }
        }
        if (placesListSearchFiltered.isEmpty()) {
            for (int i = 0; i < filtercheck.size(); i++) {
                for (int x = 0; x < placesListfilter.size(); x++) {
                    if (placesListfilter.get(x).getGuide().get(0)
                            .getTranslations().get(0).getName()
                            .equals(filtercheck.get(i)) && placesListfilter.get(x).getCity().getTranslations().get(0).getName().equals(Cityfilterstr)) {
                        Filtersofspinner.add(placesListfilter.get(x));
                        placesListSearchFiltered.add(placesListfilter.get(x));
                    }
                }
            }

            adapterFiltered = new PlacesNearAdaptercard(context, placesListSearchFiltered);
            recyclerView.setAdapter(adapterFiltered);
            adapterFiltered.notifyDataSetChanged();
            if (placesListfilter.get(0).getDistance() != null) {
                Collections.sort(placesListSearchFiltered, new SortbyNearmap());
            }
            if (!placesListSearchFiltered.isEmpty()) {
                getlist(placesListSearchFiltered);
            }
//            Map_frag.map.clear();
            refreshmap();

        } else {
            adapterFiltered = new PlacesNearAdaptercard(context, placesListSearchFiltered);
            recyclerView.setAdapter(adapterFiltered);
            adapterFiltered.notifyDataSetChanged();
            Collections.sort(placesListSearchFiltered, new SortbyNearmap());
            getlist(placesListSearchFiltered);
            // Map_frag.map.clear();
            refreshmap();
        }
        if (sideListstr.isEmpty()) {
            filterListwithCity(Cityfilterstr, placesListfilter);
        }
    }

    ////filter with spinner by city name
    public static void filterListwithCity(String filtercheck, List<Place> placesListfilter) {
        placesListSearchFiltered.clear();
        Cityfilterstr = filtercheck;
        if (!sideListstr.isEmpty()) {
            for (int i = 0; i < sideListstr.size(); i++) {
                for (int x = 0; x < placesListfilter.size(); x++) {
                    if (placesListfilter.get(x).getGuide().get(0)
                            .getTranslations().get(0).getName()
                            .equals(sideListstr.get(i))
                            && placesListfilter.get(x).getState().getTranslations().get(0).getName().equals(filtercheck)) {


                        Filtersofspinner.add(placesListfilter.get(x));
                        placesListSearchFiltered.add(placesListfilter.get(x));
                    }
                }
            }
        } else {
            for (int x = 0; x < placesListfilter.size(); x++) {
                if (placesListfilter.get(x).getCity().getTranslations().get(0).getName().equals(filtercheck)) {
                    Filtersofspinner.add(placesListfilter.get(x));
                    placesListSearchFiltered.add(placesListfilter.get(x));
                }
            }
        }


        if (placesListSearchFiltered.isEmpty()) {
            adapterFiltered = new PlacesNearAdaptercard(context, placesListSearchFiltered);
            recyclerView.setAdapter(adapterFiltered);
            adapterFiltered.notifyDataSetChanged();
        } else {
            adapterFiltered = new PlacesNearAdaptercard(context, placesListSearchFiltered);
            recyclerView.setAdapter(adapterFiltered);
            adapterFiltered.notifyDataSetChanged();
        }
    }

    //////////////////////////////////////////////////////////////

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewFilter.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        SMP = (SendMplaces) getActivity();

    }

    protected void displayReceivedData(final List<Place> placesticketList1) {
        placesticketListrecived = new ArrayList<Place>();
        placesticketList = new ArrayList<Place>();
        placesnamefilterstr = new ArrayList<String>();
        placesnamefilter = new ArrayList<PlaceFilterModel>();
        spinnercities = new ArrayList<>();
        cityList = new ArrayList<>();
        ////////////////////////////////////////////////////////////////////////////////////////////
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

        Call<DefaultResponse> call = null;
        if (currentLat != 0.0) {
            call = RetrofitClient.getInstance().getApi().
                    noorApi2(String.valueOf(currentLat) + "," + String.valueOf(currentLng), Constants.lang,
                            Constants.status, Constants.ticket);
        } else {
            call = RetrofitClient.getInstance().getApi().
                    noorApi2("", Constants.lang, Constants.status, Constants.ticket);
        }

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getPlaces().isEmpty()) {
                        placesticketList = response.body().getPlaces();
                        Log.e("SIZEEE", String.valueOf(placesticketList.size()));
                        /////////////////////////////////////////////////////////////////////////////////////////
                        for (int i = 0; i < placesticketList.size(); i++) {
                            if (!placesticketList.get(i).getTranslations().isEmpty()) {
                                placesticketListrecived.add(placesticketList.get(i));
                            }
                        }
///////////////////////////////////////////////////
                  /*      for (int i = 0; i < placesticketListrecived.size(); i++) {
                            if (placesnamefilterstr.contains(placesticketListrecived.get(i).getGuide().get(0).getTranslations().get(0).getName())) {
                            } else {
                                placesnamefilterstr.add(placesticketListrecived.get(i).getGuide().get(0).getTranslations().get(0).getName());
                            }
                        }*/
                        Call<GuideResponse> call5 = RetrofitClient.getInstance().getApi().getGuideApi(Constants.lang, Constants.status);

                        call5.enqueue(new Callback<GuideResponse>() {
                            @Override
                            public void onResponse(Call<GuideResponse> call, Response<GuideResponse> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        Log.e("here", "guide response");
                                        if (!response.body().getGuide().isEmpty()) {
                                            placesListfff = response.body().getGuide();
                                        }

                                        for (int i = 0; i < placesListfff.size(); i++) {

                                            placesnamefilterstr.add(placesListfff.get(i).getTranslations().get(0).getName());
                                        }
                                        for (int i = 0; i < placesnamefilterstr.size(); i++) {
                                            placesnamefilter.add(
                                                    new PlaceFilterModel(false, placesnamefilterstr.get(i)));
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<GuideResponse> call, Throwable t) {
                                Log.e("fail", "FAILL");
                            }
                        });

                    }

                    if (placesticketListrecived != null) {
                        adapterfilter = new PlacesFilterAdaptercard(getActivity(), placesnamefilter, placesticketListrecived);
                        // adapter4 = new PlacesNearAdaptercard(getActivity(), placesticketListrecived);
                        //  recyclerView.setAdapter(adapter4);
                        recyclerViewFilter.setAdapter(adapterfilter);
                        SMP.sendMData(placesticketListrecived);

                        //sort list with best place and send to Map_frag
                        Collections.sort(placesticketListrecived, new SortbyNearmap());
                        getlist(placesticketListrecived);
                    } else {
                        txtNodatad.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(getView().GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Log.e("error_retrofit", call.toString());
            }
        });


/////////////////////////////////////////////////////////
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
                                    if (cityList.size() != 0) {
                                        if (!cityList.get(i).getTranslations().isEmpty()) {
                                            spinnercities.add(cityList.get(i).getTranslations().get(0).getName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.
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
                            filterListwithCity(selected, placesticketListrecived);
                            progressBar.setVisibility(getView().GONE);
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


    }

    interface SendMplaces {
        void sendMData(List<Place> placesList);
    }

    static class SortbyNearmap implements Comparator<Place> {
        // Used for sorting in ascending order of
        // Best number
        public int compare(Place a, Place b) {
            // return (Double.parseDouble(a.getDistance().toString()) -Double.parseDouble( b.getDistance().toString()));
            if (a.getDistance() != null) {
                double delta = a.getDistance() - b.getDistance();
                if (delta > 0.00001) return 1;
                if (delta < -0.00001) return -1;
            }
            return 0;
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
        } else if (id == R.id.search) {
            Intent intent = new Intent(getActivity(), Search_Activity.class);
            getActivity().startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
