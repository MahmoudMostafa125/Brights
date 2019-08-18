package com.noorapp.noor.models;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.noorapp.noor.Book_Date_Activity;
import com.noorapp.noor.FavouritePlace_Fragment;
import com.noorapp.noor.FavouriteTrip_Fragment;
import com.noorapp.noor.Favourite_Activity;
import com.noorapp.noor.MainActivity;
import com.noorapp.noor.R;
import com.noorapp.noor.ReservationPlace_Fragment;
import com.noorapp.noor.ReservationTrip_Fragment;

import java.util.ArrayList;
import java.util.List;

public class Reservation_Activity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView txttitle;
    String check = null;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_);
        imgBack = (ImageView) findViewById(R.id.back);
        txttitle = findViewById(R.id.toolbar_title);
        txttitle.setText(R.string.reservations);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            check = bundle.getString("reserve");
        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check != null) {
                    if (check.equals("OK")) {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (check != null) {
            if (check.equals("OK")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }else {
            finish();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Reservation_Activity.ViewPagerAdapterF adapter = new Reservation_Activity.ViewPagerAdapterF(getSupportFragmentManager());
        adapter.addFragment(new ReservationPlace_Fragment(), getString(R.string.Places));
        adapter.addFragment(new ReservationTrip_Fragment(), getString(R.string.Trips));

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapterF extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapterF(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
