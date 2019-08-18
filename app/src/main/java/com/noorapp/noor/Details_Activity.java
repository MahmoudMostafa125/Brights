package com.noorapp.noor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.airbnb.lottie.L;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.noorapp.noor.Utility.LanguageUtil;
import com.noorapp.noor.Utility.MyContextWrapper;
import com.noorapp.noor.Utility.internetConnection;
import com.noorapp.noor.adapter.MyAdapter;
import com.noorapp.noor.adapter.ReviewAdapter;
import com.noorapp.noor.api.RetrofitClient;
import com.noorapp.noor.models.AddFavouriteModel.AddFavouriteResponse;
import com.noorapp.noor.models.PlacesDetailsModel.Image;
import com.noorapp.noor.models.PlacesDetailsModel.PlaceDetailsResponse;
import com.noorapp.noor.models.PlacesDetailsModel.Review;
import com.noorapp.noor.models.Ticket;

import org.w3c.dom.Element;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.noorapp.noor.Utility.utility.showAlertDialogNoInternetConnection;
import static java.lang.Math.round;

public class Details_Activity extends AppCompatActivity {
    private Context mContext;

    YouTubePlayerView youTubePlayerView;
    // YouTubePlayer.OnInitializedListener onInitializedListener;
    // YouTubeApiServiceUtil youTubeApiServiceUtil =new YouTubeApiServiceUtil();
    FrameLayout fYou;
    Configs configs = new Configs();
    int place_id;
    TextView city, nametxt, desctxt, addresstxt,
            opentxt, phonetxt, urltxt, moneytxt,
            oldpricetxt, discounttxt, bought,
            Reviewtxt, termstxt, termstitle;
    ProgressBar progressBar;
    ImageView IMG;
    TextView toolbartitle2;
    ExpandableTextView expandableTextView;
    ImageView imageView, phoneimg;
    String phone, locationINTENT;
    Button button;
    NestedScrollView scr;
    LinearLayout lin;
    //  static VideoView vid;
    private List<Image> ListImagesOfSlider;
    private List<Review> listplacesandreviews;
    private List<String> pathesList;
    private static ViewPager mPager;
    public Context context;
    RecyclerView recyclerView;
    private ReviewAdapter adapter;
    Toolbar toolbar;
    View view;
    static MediaController m;
    WebView videoWeb;
    String Countryname, Cityname, Idd, price, currency;
    String DESC;
    private List<Ticket> TicketList;
    private Menu menu;
    NestedScrollView scrollView;
    LinearLayout linphone, LinOpen;
    Fragment fragment;

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("======", "attachBaseContext");
        Locale languageType = LanguageUtil.getLanguageType(mContext);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    private YouTubePlayerSupportFragment youTubePlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);
       /* getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mContext = this;
        // youTubePlayerView = findViewById(R.id.youtube_view);
        youTubePlayerFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.youtube_player_fragment);
        fYou = findViewById(R.id.you);
        scrollView = findViewById(R.id.scroll);
        toolbar = findViewById(R.id.toolbar_details);
        linphone = findViewById(R.id.phonelin);
        LinOpen = findViewById(R.id.linopen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        phoneimg = findViewById(R.id.call);
        termstitle = findViewById(R.id.termstitle);
        recyclerView = findViewById(R.id.recyclereview);
        button = findViewById(R.id.buttonbook);
        button.setVisibility(view.GONE);
        imageView = findViewById(R.id.imgback);
        Reviewtxt = findViewById(R.id.review);
        city = findViewById(R.id.countryID);
        nametxt = findViewById(R.id.nameID);
        desctxt = findViewById(R.id.DetailsID);
        addresstxt = findViewById(R.id.addressID);
        termstxt = findViewById(R.id.terms);
        discounttxt = findViewById(R.id.discount);
        opentxt = findViewById(R.id.openclose);
        phonetxt = findViewById(R.id.phoneId);
        urltxt = findViewById(R.id.urlID);
        progressBar = findViewById(R.id.progressBar);
        moneytxt = findViewById(R.id.money);
        oldpricetxt = findViewById(R.id.old_price);
        bought = findViewById(R.id.bought);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        urltxt.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            place_id = bundle.getInt("placeID");
        }

        m = new MediaController(this);
        if (internetConnection.internetConnectionAvailable(1000)) {

            Call<PlaceDetailsResponse> call = RetrofitClient.getInstance().getApi().PlaceDetailsAPI(String.valueOf(place_id), Constants.lang, Constants.status);
            call.enqueue(new Callback<PlaceDetailsResponse>() {
                @Override
                public void onResponse(Call<PlaceDetailsResponse> call, final Response<PlaceDetailsResponse> response) {

                    if (response.isSuccessful()) {
                        TicketList = response.body().getPlace().get(0).getTickets();
                        listplacesandreviews = response.body().getPlace().get(0).getReviews();
                        city.setText(response.body().getPlace().get(0).getCountry().getTranslations().get(0).getName());
                        Countryname = response.body().getPlace().get(0).getCountry().getTranslations().get(0).getName();
                        if (!response.body().getPlace().get(0).getTranslations().isEmpty()) {
                            Idd = String.valueOf(response.body().getPlace().get(0).getId());
                            nametxt.setText(response.body().getPlace().get(0).getTranslations().get(0).getName());
                            Cityname = response.body().getPlace().get(0).getTranslations().get(0).getName();
                            if (!response.body().getPlace().get(0).getCurrency().getTranslations().isEmpty()) {
                                currency = response.body().getPlace().get(0).getCurrency().getTranslations().get(0).getIso();
                            }
                            price = response.body().getPlace().get(0).getPrice();
                            DESC = response.body().getPlace().get(0).getTranslations().get(0).getDescription();
                            desctxt.setText(DESC);
                            addresstxt.setText(getResources().getString(R.string.address) + " " + response.body().getPlace().get(0).getTranslations().get(0).getAddress());
                            locationINTENT = response.body().getPlace().get(0).getLocation();
                            String opentime = response.body().getPlace().get(0).getOpen();
                            String closetime = response.body().getPlace().get(0).getClose();

                            if (opentime == null) {
                                opentxt.setText("Open 24 Hour");
                            } else if (closetime == null) {

                                opentxt.setText(getResources().getString(R.string.open) + " " + parseTime(opentime));
                            } else {
                                opentxt.setText(getResources().getString(R.string.open) + " " + parseTime(opentime) + "  " + getResources().getString(R.string.to) + " " + parseTime(closetime));
                            }
                            if (!response.body().getPlace().get(0).getCurrency().getTranslations().isEmpty() ) {
                                if (!(Double.valueOf(response.body().getPlace().get(0).getPrice()) <= 0 )  ){

                                    moneytxt.setText(response.body().getPlace().get(0).getPrice()
                                            + " " + response.body().getPlace().get(0).getCurrency().getTranslations().get(0).getIso());
                                } else {
                                    moneytxt.setVisibility(View.GONE);
                                }


                                oldpricetxt.setPaintFlags(oldpricetxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                oldpricetxt.setText(response.body().getPlace().get(0).getOldPrice()
                                        + " " + response.body().getPlace().get(0).getCurrency().getTranslations().get(0).getIso());
                            }
                            if (response.body().getPlace().get(0).getOldPrice() == null) {
                                discounttxt.setVisibility(View.GONE);
                                oldpricetxt.setVisibility(View.GONE);
                            } else {
                                float newprice = Float.parseFloat(response.body().getPlace().get(0).getPrice());
                                float oldprice = Float.parseFloat(response.body().getPlace().get(0).getOldPrice());
                                float x = 1 - (newprice / oldprice);

                                discounttxt.setText(Math.round(x * 100) + "%");
                                discounttxt.setVisibility(View.VISIBLE);
                            }
                            if (response.body().getPlace().get(0).getPhone() != null) {
                                phone = response.body().getPlace().get(0).getPhone();
                            }
                            if (phone == null) {
                                phonetxt.setVisibility(View.GONE);
                                phoneimg.setVisibility(View.GONE);
                                linphone.setVisibility(View.GONE);
                            } else {
                                phonetxt.setText(getResources().getString(R.string.phone) + " " + phone);
                            }
                            if (response.body().getPlace().get(0).getTranslations().get(0).getTerms() == null) {
                                termstxt.setVisibility(View.GONE);
                                termstitle.setVisibility(View.GONE);
                            } else {
                                termstxt.setText(response.body().getPlace().get(0).getTranslations().get(0).getTerms());
                            }
                        } else {
                            nametxt.setVisibility(View.GONE);
                        }

                        if (response.body().getPlace().get(0).getVideos().isEmpty()) {
                            // videoWeb.setVisibility(View.GONE);
                            // youTubePlayerFragment.setUserVisibleHint(false);
                            fYou.setVisibility(View.GONE);
                        } else {

                            if (response.body().getPlace().get(0).getVideos().get(0).getLink() != null) {
                                final String link = response.body().getPlace().get(0).getVideos().get(0).getLink();
                                final String videoID = getValidYoutubeVideoId(response.body().getPlace().get(0).getVideos().get(0).getLink());
                                // videoWeb.getSettings().setJavaScriptEnabled(true);
                                //   videoWeb.getSettings().setPluginState(WebSettings.PluginState.ON);
                                //  videoWeb.loadUrl("http://www.youtube.com/embed/" + videoID + "?autoplay=1&vq=small");
                               /* videoWeb.setWebChromeClient(new WebChromeClient() {});
                                videoWeb.loadData("<html><body style='margin:0;padding:0;'><iframe width=\"100%\" height=\"100%\" src=\"" + "http://www.youtube.com/embed/" + videoID + "?autoplay=1&vq=small" + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>", "text/html", "utf-8");
*/
                                youTubePlayerFragment.initialize(Configs.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                                    @Override
                                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                                        Log.e("sdsdsdsd", videoID);
                                        if (!b) {
                                            youTubePlayer.cueVideo(videoID);
                                        }
                                    }

                                    @Override
                                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                                        Log.e("sdsdsdsd", "nooo");
                                    }
                                });

                            }
                        }
                        if (response.body().getPlace().get(0).getBought() != null && !response.body().getPlace().get(0).getBought().equals("0")) {
                            bought.setText(response.body().getPlace().get(0).getBought() + " " + getResources().getString(R.string.bought));
                        } else {
                            bought.setVisibility(View.GONE);
                        }
                        ListImagesOfSlider = response.body().getPlace().get(0).getImages();
                        pathesList = new ArrayList<String>();
                        for (int i = 0; i < ListImagesOfSlider.size(); i++) {
                            if (ListImagesOfSlider.get(i).getPath() != null) {
                                pathesList.add(Constants.mainLink + ListImagesOfSlider.get(i).getPath());
                            } else {
                                pathesList.add(ListImagesOfSlider.get(i).getLink());
                            }
                        }

                        mPager = (ViewPager) findViewById(R.id.pager);
                        mPager.setAdapter(new MyAdapter(getApplicationContext(), pathesList));
                        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                        indicator.setViewPager(mPager);

                        adapter = new ReviewAdapter(context, listplacesandreviews, getScreenHeightInPixels(getApplicationContext()));
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                        if (TicketList.size() != 0) {
                            button.setVisibility(view.VISIBLE);
                            //  setMargins(scrollView, 0, 0, 0, 60);
                        }
                        if (response.body().getPlace().get(0).getReviews().isEmpty()) {
                            Reviewtxt.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<PlaceDetailsResponse> call, Throwable t) {
                    Log.e("error_retrofit", call.toString());
                }
            });
        } else {
            showAlertDialogNoInternetConnection(this);
            Toast.makeText(this, R.string.offline, Toast.LENGTH_SHORT).show();
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* try {
                    Class.forName("android.webkit.WebView").getMethod("onPause", (Class[]) null).invoke(videoWeb, (Object[]) null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }*/
                // videoWeb..stopPlayback();
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;

        getMenuInflater().inflate(R.menu.menu_details_toolbar, menu);
        String APITc = null;
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        APITc = sharedPref.getString("api_token", null);
        if (APITc == null) {
            menu.getItem(0).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
            String APIT = sharedPref.getString("api_token", null);
            Call<AddFavouriteResponse> call = RetrofitClient.getInstance().getApi().userAddFav(APIT, Idd, "P", "1");
            call.enqueue(new Callback<AddFavouriteResponse>() {
                @Override
                public void onResponse(Call<AddFavouriteResponse> call, Response<AddFavouriteResponse> response) {
                /*    Log.e("reserseponse", String.valueOf(response.body().getResponse()));
                    if (response.body().getResponse().toString().equals("true")) {
                        Toast.makeText(Details_Activity.this, "Action clicked", Toast.LENGTH_LONG).show();
                    }*/
                    menu.getItem(0).setIcon(ContextCompat.getDrawable(Details_Activity.this, R.drawable.ic_favorite_done_24dp));
                }

                @Override
                public void onFailure(Call<AddFavouriteResponse> call, Throwable t) {
                    Log.e("error_retrofit", call.toString());
                }
            });
            return true;
        } else if (id == R.id.action_share) {

            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Noor");
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String parseTime(String date) {

        try {
            String _24HourTime = date;
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            System.out.println(_24HourDt);
            System.out.println(_12HourSDF.format(_24HourDt));
            return _12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   /* public static void playvid(String path, View view) {

        vid.setMediaController(m);
        //String pathvid = "https://www.youtube.com/watch?v=bSMZknDI6bg";
        Uri u = Uri.parse(path);
        vid.setVideoURI(u);
        vid.start();
    }*/

    public static int getScreenHeightInPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int height = dm.widthPixels;
        Log.e("responseWidth", String.valueOf(height));
        return height;
    }

    public void MakeCall(View view) {
        String phoneNUM = "tel:" + phone;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNUM));
        startActivity(intent);
    }

    public void Getgps(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + locationINTENT));
        startActivity(intent);
    }

    public void Book(View view) {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String API = sharedPref.getString("api_token", null);
        if (API == null) {
            Intent intent = new Intent(getApplicationContext(), Login_option_Activity.class);
            startActivity(intent);
        } else {
            //Toast.makeText(context, API, Toast.LENGTH_SHORT).show();
            Log.e("MYAPPPI", API);
            if (TicketList.size() == 0) {
                Toast.makeText(this, "Sorry No Tickets Available Now", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, Book_Date_Activity.class);
                intent.putExtra("city", Cityname);
                intent.putExtra("country", Countryname);
                intent.putExtra("id", Idd);
                intent.putExtra("price", price);
                intent.putExtra("currency", currency);
                startActivity(intent);
            }
        }
    }

    public void Chat(View view) {
        Intent intent = new Intent(this, Chat_Activity.class);
        startActivity(intent);

    }

    public void share(View view) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Noor");
            String shareMessage = "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static String getValidYoutubeVideoId(String youtubeUrl) {
        if (youtubeUrl == null || youtubeUrl.trim().contentEquals("")) {
            return "";
        }
        youtubeUrl = youtubeUrl.trim();
        String validYoutubeVideoId = "";
        String regexPattern = "^(?:https?:\\/\\/)?(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";
        Pattern regexCompiled = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
        Matcher regexMatcher = regexCompiled.matcher(youtubeUrl);
        if (regexMatcher.find()) {
            try {
                validYoutubeVideoId = regexMatcher.group(1);
            } catch (Exception ex) {
            }
        }
        return validYoutubeVideoId;
    }
}