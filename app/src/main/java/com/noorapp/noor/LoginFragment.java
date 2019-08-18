package com.noorapp.noor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.LoginModel.LoginResponse;
import com.noorapp.noor.models.ProfileModel.ProfileResponse;
import com.noorapp.noor.models.Reservation_Activity;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    Button login, Lang;
    TextView txtview, country, city, favo, ress, point, txtfav;
    ImageView img;
    LinearLayout linfav, linres, linlan, imgGo, account, linout;
    Context cox;
    ProgressBar bar;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("responseeeee", "on resume");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        cox = view.getContext();
        img = view.findViewById(R.id.image1);
        imgGo = view.findViewById(R.id.imggo);
        linfav = view.findViewById(R.id.favcl);
        linres = view.findViewById(R.id.reserva);
        linlan = view.findViewById(R.id.lan);
        linout = view.findViewById(R.id.logout);
        txtview = view.findViewById(R.id.name);
        txtfav = view.findViewById(R.id.txtfav);
        country = view.findViewById(R.id.country);
        city = view.findViewById(R.id.city);
        favo = view.findViewById(R.id.fav);
        ress = view.findViewById(R.id.res);
        point = view.findViewById(R.id.points);
        Lang = view.findViewById(R.id.lang);
        bar = view.findViewById(R.id.progressProfile);
        bar.setVisibility(View.VISIBLE);
        account = view.findViewById(R.id.acc);
        // final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String API = sharedPref.getString("api_token", null);
        String value = sharedPref.getString("user_name", null);

        txtfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Favourite_Activity.class);
                startActivity(intent);
            }
        });
        linfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Favourite_Activity.class);
                startActivity(intent);
            }
        });

        linres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Reservation_Activity.class);
                startActivity(intent);
            }
        });
        linlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale();
            }
        });
        imgGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutusActivity.class);
                startActivity(intent);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditAccountActivity.class);
                startActivity(intent);
            }
        });
        linout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle(R.string.exit)
                        .setMessage(R.string.exitnow)
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {

                                SharedPreferences settings = v.getContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                                settings.edit().clear().apply();
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                                prefs.edit().clear().apply();
                                ///////////////////////////
                                Intent i = v.getContext().getPackageManager().getLaunchIntentForPackage(v.getContext().getPackageName());
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                getActivity().finish();
                            }
                        }).create().show();

            }
        });
        if (internetConnection.internetConnectionAvailable(1000)) {

            Call<ProfileResponse> call = RetrofitClient.getInstance().getApi().userProfile(API);
            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                    if (response.body() != null) {

                        if (response.body().getResponse()) {
                            String avtar = response.body().getProfile().get(0).getAvatar();
                            Picasso.with(view.getContext())
                                    .load(Constants.mainLink + avtar)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .into(img);
                            String username = response.body().getProfile().get(0).getUsername();

                            String favstr = response.body().getProfile().get(0).getFavoritesCount();
                            String resstr = response.body().getProfile().get(0).getReservationsCount();
                            String pointstr = response.body().getProfile().get(0).getPoints();
                            txtview.setText(username);
                            favo.setText(favstr);
                            ress.setText(resstr);
                            point.setText(pointstr);
                            if (response.body().getProfile().get(0).getCountry() != null) {
                                String countrystr = response.body().getProfile().get(0).getCountry();
                                country.setText(countrystr);
                            } else {
                                country.setVisibility(view.GONE);
                            }
                            if (response.body().getProfile().get(0).getCity() != null) {
                                String citystr = response.body().getProfile().get(0).getCity();
                                city.setText(citystr);
                            } else {
                                city.setVisibility(view.GONE);
                            }
                        }
                    }
                    bar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    Log.e("fail", "FAILL");
                    bar.setVisibility(View.GONE);
                }
            });
        } else {
            showAlertDialogNoInternetConnection(view.getContext());
            Toast.makeText(view.getContext(), R.string.offline, Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public void setLocale() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFragment = new Language();
        dialogFragment.show(ft, "dialog");

    }

}
