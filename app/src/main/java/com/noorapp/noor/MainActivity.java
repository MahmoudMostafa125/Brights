package com.noorapp.noor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;
import com.noorapp.noor.adapter.ViewPagerAdapter;
import com.noorapp.noor.models.Place;

import java.util.List;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MainActivity extends AppCompatActivity implements Recent_frag.SendMessage, Recent_frag.SendMessage2, Discover_frag.SendMplaces {
    private Context mContext;
    TextView toolbartitle;
    public static ViewPager viewPager;
    ImageView imageView;
    public static BottomNavigationView navigation;
    Recent_frag recent_frag;
    Discover_frag discover_frag;
    Guide_frag guide_frag;
    Cars_frag cars_frag;
    LoginFragment login_frag;
    MenuItem prevMenuItem;
    MenuItem item, helpitem;


    boolean menuState = false;
   public  static boolean menuState2 = false;

    public static String TAG = null;

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
      /*  getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);*/
       // getWindow().setStatusBarColor(Color.TRANSPARENT);
        imageView = findViewById(R.id.action_help);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("responseeeesaha", token);

        //tool bar
        toolbartitle = findViewById(R.id.toolbar_title);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //viewpager and bottomnav
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_inbox:
                                toolbartitle.setText(R.string.recent);
                                menuState = false;
                                menuState2 = true;
                                invalidateOptionsMenu();
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.nav_sent:
                                toolbartitle.setText(R.string.attraction);
                                menuState = true;
                                menuState2 = true;
                                invalidateOptionsMenu();
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.nav_trash:
                                toolbartitle.setText(R.string.Guide);
                                menuState = false;
                                menuState2 = true;
                                invalidateOptionsMenu();

                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.nav_car:
                                toolbartitle.setText(R.string.Cars);
                                menuState = false;
                                menuState2 = true;
                                invalidateOptionsMenu();
                                viewPager.setCurrentItem(3);
                                break;
                            case R.id.nav_profile:
//                                Intent intent = new Intent(getApplicationContext(), Login_option_Activity.class);
//                                startActivity(intent);
                                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                                String API = sharedPref.getString("api_token", null);
                                if (API == null) {
                                    Intent intent = new Intent(getApplicationContext(), Login_option_Activity.class);
                                    startActivity(intent);
                                } else {
                                    toolbartitle.setText(R.string.profile);
                                    menuState = false;
                                    menuState2 = false;
                                    invalidateOptionsMenu();
                                    viewPager.setCurrentItem(4);
                                }
                                break;
                        }
                        return false;
                    }
                });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                navigation.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(position);

                if (position == 0) {
                    Log.d("pagewewewe", "onPageSelected: " + position);
                    toolbartitle.setText(R.string.recent);
                    menuState = false;
                    menuState2 = true;
                    imageView.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                } else if (position == 1) {
                    toolbartitle.setText(R.string.attraction);
                    menuState = true;
                    menuState2 = true;
                    imageView.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                } else if (position == 2) {
                    toolbartitle.setText(R.string.Guide);
                    menuState = false;
                    menuState2 = true;
                    imageView.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                } else if (position == 3) {
                    toolbartitle.setText(R.string.Cars);
                    menuState = false;
                    menuState2 = true;
                    imageView.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                } else if (position == 4) {
                    toolbartitle.setText(R.string.profile);
                    menuState = false;
                    menuState2 = false;
                    imageView.setVisibility(View.GONE);
                    invalidateOptionsMenu();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), HelpActivity.class);
                startActivity(intent1);
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        recent_frag = new Recent_frag();
        discover_frag = new Discover_frag();
        guide_frag = new Guide_frag();
        cars_frag = new Cars_frag();
        login_frag = new LoginFragment();
        adapter.addFragment(recent_frag);
        adapter.addFragment(discover_frag);
        adapter.addFragment(guide_frag);
        adapter.addFragment(cars_frag);
        adapter.addFragment(login_frag);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.action_search);
        item.setVisible(false);


        if (menuState == true) {
            item.setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void sendData(List<Place> placesticketList) {
        String tag = "android:switcher:" + R.id.viewpager + ":" + 1;
        Discover_frag f = (Discover_frag) getSupportFragmentManager().findFragmentByTag(tag);
        if (f == null) {
            Log.e("hahay", "its null here");
        }
        f.displayReceivedData(placesticketList);

    }

    @Override
    public void sendData2(List<Place> placeList) {
        String tag = "android:switcher:" + R.id.viewpager + ":" + 2;

        //  Guide_frag f = (Guide_frag) getSupportFragmentManager().findFragmentByTag(tag);
//        Guide_frag favoritesFragment = (Guide_frag) getSupportFragmentManager().getFragments().get(2);
        // Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + viewPager.getCurrentItem());
        // favoritesFragment.displayReceivedData2(placeList);
       /* if (page != null) {
            switch (viewPager.getCurrentItem()) {
                case 2:
                    guide_frag = (Guide_frag) page;
                    Log.e("aaahhh", "nooooooooooooooooooooooooooo");
                    guide_frag.displayReceivedData2(placeList); //this is public method of Fragment OneFragment.
                    break;
            }

        } else {
            Log.e("yalla", "offff");
        }*/
    /*    Guide_frag f = (Guide_frag) getSupportFragmentManager().findFragmentByTag(tag);
        if (f == null) {
            Log.e("hahay", "its null here");
        }
        f.displayReceivedData2(placeList);*/

    }

    @Override
    public void sendMData(List<Place> placesList) {
        String tag = "android:switcher:" + R.id.viewpager + ":" + 2;
        Guide_frag f = (Guide_frag) getSupportFragmentManager().findFragmentByTag(tag);
//        f.displayReceivedData2(placesList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Intent intent = new Intent(this, Search_Activity.class);
                startActivity(intent);
                break;
            case R.id.action_help:
                Intent intent1 = new Intent(this, HelpActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }

}
