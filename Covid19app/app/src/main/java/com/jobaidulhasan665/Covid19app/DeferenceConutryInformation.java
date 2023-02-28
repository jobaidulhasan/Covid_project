package com.jobaidulhasan665.Covid19app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DeferenceConutryInformation extends AppCompatActivity {
    TextView country_name,total_cases,total_death,total_active,total_recoverd,today_cases,today_death,casesOnPerMilion,deathOnPerMilion,total_test,cretical,lastUpdate;
    String r_flage,r_country_name,r_total_cases,r_total_death,r_total_active,r_toatl_recoverd,r_today_cases,r_today_death,r_caseOnperMilion,r_deathOnPerMilion,r_total_test,r_cretecal;
    long r_lastUpdate;
    ImageView flag;
    Toolbar toolbar;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deference_conutry_information);
        //////////////and Banner ads------------------------->
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //////////////////////Banner Ads add Successfully--------------------------------->
        ////Ads InInterstitialAd -------------------------------------------------------->

        load_add();
        flag=findViewById(R.id.details_img);
        country_name=findViewById(R.id.detail_country);
        lastUpdate=findViewById(R.id.lastUpdate);
        total_cases=findViewById(R.id.details_total_cases);
        total_death=findViewById(R.id.detail_Total_death);
        total_active=findViewById(R.id.details_total_active);
        total_recoverd=findViewById(R.id.detail_total_recoverd);
        today_cases=findViewById(R.id.detail_today_cases);
        today_death=findViewById(R.id.detail_today_death);
        casesOnPerMilion=findViewById(R.id.detail_casesOnperMilion);
        deathOnPerMilion=findViewById(R.id.detail_deathOnperMilion);
        total_test=findViewById(R.id.detail_total_test);
        cretical=findViewById(R.id.detail_cretecel);
        toolbar=findViewById(R.id.toolvarid);
        ////////////////////////End Find Workd------------------------------------------------------>
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            r_flage=bundle.getString("img");
            r_country_name=bundle.getString("country_name");
            r_total_cases=bundle.getString("total_cases");
            r_lastUpdate=bundle.getLong("lastUpdate");
            r_total_death= String.valueOf(bundle.getInt("total_death"));
            r_total_active=bundle.getString("total_active");
            r_toatl_recoverd=bundle.getString("toatl_recoverd");
            r_today_cases=bundle.getString("today_cases");
            r_today_death=bundle.getString("today_death");
            r_caseOnperMilion=bundle.getString("caseOnperMilion");
            r_deathOnPerMilion=bundle.getString("deathOnPerMilion");
            r_total_test=bundle.getString("total_test");
            r_cretecal=bundle.getString("cretecal");
        }
        toolbar.setTitle(r_country_name+" Details");
        lastUpdate.setText("Last Update\n"+getDate(r_lastUpdate));
        country_name.setText(r_country_name);
        total_cases.setText(r_total_cases);
        total_death.setText(r_total_death);
        total_active.setText(r_total_active);
        total_recoverd.setText(r_toatl_recoverd);
        today_cases.setText(r_today_cases);
        today_death.setText(r_today_death);
        casesOnPerMilion.setText(r_caseOnperMilion);
        deathOnPerMilion.setText(r_deathOnPerMilion);
        total_test.setText(r_total_test);
        cretical.setText(r_cretecal);
        Glide.with(this).
                load(r_flage).
                apply(new RequestOptions().override(90,50)).
                into(flag);
    }
    public static String getDate(long date)
    {
        SimpleDateFormat formatter=new SimpleDateFormat("EEE, dd-MM-yyy  hh:mm:ss aaa");
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return formatter.format(calendar.getTime());

    }

    private void load_add()
    {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4854312329985830/6278637857");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public void onBackPressed() {

        if(mInterstitialAd.isLoaded())
        {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener(){

                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    finish();
                }
            });
        }
        else{
            super.onBackPressed();
        }
    }
}
