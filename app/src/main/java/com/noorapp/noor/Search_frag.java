package com.noorapp.noor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.noorapp.noor.adapter.PlacesNearAdaptercard;
import com.noorapp.noor.models.Place;

import java.util.ArrayList;
import java.util.List;

import static com.noorapp.noor.Discover_frag.recyclerView;
import static com.noorapp.noor.MainActivity.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
/*public class Search_frag extends Fragment implements View.OnClickListener {

    Button mubtn, trainbtn, stadbtn, shopbtn;
    TextView mutxt, traintxt, stadtxt, shoptxt;
    Boolean mub = false;
    Boolean trainb = false;
    Boolean stadb = false;
    Boolean shopb = false;
    public static List<String> filtercheck;
    public static List<Place> placesListSearch;
  //  public static List<Place> placesListSearchFiltered;
    public static PlacesNearAdaptercard adapterFiltered;

    public Search_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search_frag, container, false);
       /* filtercheck = new ArrayList<>();
        placesListSearchFiltered = new ArrayList<Place>();
        mubtn = view.findViewById(R.id.museum);
        mubtn.setOnClickListener(this);

        trainbtn = view.findViewById(R.id.train);
        trainbtn.setOnClickListener(this);

        stadbtn = view.findViewById(R.id.stad);
        stadbtn.setOnClickListener(this);

        shopbtn = view.findViewById(R.id.shopping);
        shopbtn.setOnClickListener(this);


        mutxt = view.findViewById(R.id.mutxt);
        traintxt = view.findViewById(R.id.traintxt);
        stadtxt = view.findViewById(R.id.stadtxt);
        shoptxt = view.findViewById(R.id.shoppingtxt);


        /*mubtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mubtn.setBackgroundResource(R.drawable.roundedfilter);

            }
        });*/

     //   return view;
 //   }


   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.museum:
                if (mub == false) {
                    mubtn.setBackgroundResource(R.drawable.roundedfilter);
                    filtercheck.add("mosque");
                    filterList(filtercheck, placesListSearch);
                    mub = true;
                } else {
                    mubtn.setBackgroundResource(R.drawable.roundedfilterwhite);
                    filtercheck.remove("mosque");
                    // placesListSearch.clear();
                    filterList(filtercheck, placesListSearch);
                    Log.d("iinmos", String.valueOf(filtercheck.size()));
                    Log.d("iinmos", String.valueOf(placesListSearch.size()));
                    mub = false;
                }
                break;
            case R.id.train:
                if (trainb == false) {
                    trainbtn.setBackgroundResource(R.drawable.roundedfilter);
                    filtercheck.add("park");
                    filterList(filtercheck, placesListSearch);
                    trainb = true;
                } else {
                    trainbtn.setBackgroundResource(R.drawable.roundedfilterwhite);
                    filtercheck.remove("park");
                    filterList(filtercheck, placesListSearch);
                    trainb = false;
                }
                break;
            case R.id.stad:
                if (stadb == false) {
                    stadbtn.setBackgroundResource(R.drawable.roundedfilter);
                    filtercheck.add("zoo");
                    filterList(filtercheck, placesListSearch);
                    stadb = true;
                } else {
                    stadbtn.setBackgroundResource(R.drawable.roundedfilterwhite);
                    filtercheck.remove("zoo");
                    filterList(filtercheck, placesListSearch);

                    stadb = false;
                }
                break;
            case R.id.shopping:
                if (shopb == false) {
                    shopbtn.setBackgroundResource(R.drawable.roundedfilter);
                    filtercheck.add("t");
                    filterList(filtercheck, placesListSearch);
                    shopb = true;
                } else {
                    shopbtn.setBackgroundResource(R.drawable.roundedfilterwhite);
                    filtercheck.remove("t");
                    filterList(filtercheck, placesListSearch);
                    shopb = false;
                }

        }
    }*/

   /* public void filterList(List<String> filtercheck, List<Place> placesListfilter) {
        placesListSearchFiltered.clear();
        for (int i = 0; i < filtercheck.size(); i++) {
            for (int x = 0; x < placesListfilter.size(); x++) {
                if (placesListfilter.get(x).getGuide().get(0).getTranslations().get(0).getName().equals(filtercheck.get(i)) ) {
                    Log.d("adddddddedd", filtercheck.get(i));
                    placesListSearchFiltered.add(placesListfilter.get(x));
                }
                Log.d("inner", filtercheck.get(i)+"   "+placesListfilter.get(x).getGuide().get(0).getTranslations().get(0).getName());
            }
            // Log.d("outer", filtercheck.get(i)+"   "+placesListfilter.get(x).getGuide().get(0).getTranslations().get(0).getName());
        }
        if (placesListSearchFiltered.isEmpty()) {
            Log.d("notdatahere", "filterList: ");
            adapterFiltered = new PlacesNearAdaptercard(getActivity(), placesListSearch);
            recyclerView.setAdapter(adapterFiltered);
            adapterFiltered.notifyDataSetChanged();
        } else {
            adapterFiltered = new PlacesNearAdaptercard(getActivity(), placesListSearchFiltered);
            recyclerView.setAdapter(adapterFiltered);
            adapterFiltered.notifyDataSetChanged();
        }
    }

    public static void getlistSearch(List<Place> placesList) {
        Log.e("henanana", String.valueOf(placesList.get(0).getId()));
        placesListSearch = placesList;
    }*/
//}
