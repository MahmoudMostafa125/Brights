package com.noorapp.noor;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.noorapp.noor.Utility.utility;
import com.noorapp.noor.models.Place;

import java.util.List;

import static com.noorapp.noor.Utility.utility.GPSpermission;

public class Map_frag extends Fragment implements OnMapReadyCallback {
    MapView mapView;
    public static GoogleMap map;
    public static List<Place> placesListMap;

    public Map_frag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_frag, container, false);
        GPSpermission(view.getContext());
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapfra);
        mapFragment.getMapAsync(this);
        Log.e("onCreateView", "onCreateView called");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);*/
        Log.e("onViewCreated", "onViewCreated called");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("onMapReady", "onMapReady called");
        map = googleMap;

        if (placesListMap != null && !placesListMap.isEmpty()) {
            for (int i = 0; i < placesListMap.size(); i++) {
                if (placesListMap.get(i).getLocation() != null) {
                    String str = placesListMap.get(i).getLocation();
                    String[] parts = str.split(",");
                    String part1 = parts[0];
                    String part2 = parts[1];
                    Log.e("lag", part1);
                    Log.e("latt", part2);
                    try {
                        if (!placesListMap.get(i).getTranslations().isEmpty()) {
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(Float.parseFloat(part1), Float.parseFloat(part2)))
                                    .title(placesListMap.get(i).getTranslations().get(0).getName()));
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Float.parseFloat(part1), Float.parseFloat(part2)), 7.0f));
                        }

                    } catch (NumberFormatException e) {

                        Log.e("hayayata", "catcchh");
                    }
                }
            }
        }
    }


    public static void refreshmap() {
        if (map != null) {

            map.clear();
            for (int i = 0; i < placesListMap.size(); i++) {
                if (placesListMap.get(i).getLocation() != null) {
                    String str = placesListMap.get(i).getLocation();
                    String[] parts = str.split(",");
                    String part1 = parts[0];
                    String part2 = parts[1];
                    Log.e("lag", part1);
                    Log.e("latt", part2);
                    try {
                        if (!placesListMap.get(i).getTranslations().isEmpty()) {
                            map.addMarker(new MarkerOptions()
                                    .position(new LatLng(Float.parseFloat(part1), Float.parseFloat(part2)))
                                    .title(placesListMap.get(i).getTranslations().get(0).getName()));
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Float.parseFloat(part1), Float.parseFloat(part2)), 7.0f));
                        }

                    } catch (NumberFormatException e) {

                        Log.e("hayayata", "catcchh");
                    }
                }
            }
        }
    }

    public static void getlist(List<Place> placesList) {
        placesListMap = placesList;
    }

}
