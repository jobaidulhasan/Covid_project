package com.jobaidulhasan665.Covid19app;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private InterstitialAd mInterstitialAd;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //////////////////Add Inters add---------------------->
        /////Find Find Value--------------------->
        navigationView=findViewById(R.id.navigation);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        drawerLayout=findViewById(R.id.drawableId);

        toolbar=findViewById(R.id.toolvarid);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
       ///////////////////set Default Fragment------------>


        if(savedInstanceState==null)
        {
            toolbar.setTitle("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.Navigation_fragment,new Home_fragment()).commit();
            show_add();

        }

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.home)
                {
                    toolbar.setTitle("Home");
                    getSupportFragmentManager().beginTransaction().replace(R.id.Navigation_fragment,new Home_fragment()).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    show_add();

                }
                else if(item.getItemId()==R.id.Country)
                {
                    toolbar.setTitle("Country");
                    getSupportFragmentManager().beginTransaction().replace(R.id.Navigation_fragment,new District_fragment()).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    show_add();
                }
                else if(item.getItemId()==R.id.feedback)
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    show_add();

                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId()==R.id.home)
        {
            toolbar.setTitle("Home");
            getSupportFragmentManager().beginTransaction().replace(R.id.Navigation_fragment,new Home_fragment()).commit();
            menuItem.setChecked(true);
            show_add();

        }
        else if(menuItem.getItemId()==R.id.Country)
        {
            toolbar.setTitle("Country");
            getSupportFragmentManager().beginTransaction().replace(R.id.Navigation_fragment,new District_fragment()).commit();
            menuItem.setChecked(true);
            show_add();
        }
        return false;
    }
    private void load_add()
    {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4854312329985830/6278637857");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private  void show_add()
    {
        load_add();
        ScheduledExecutorService scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mInterstitialAd.isLoaded())
                        {
                            mInterstitialAd.show();
                        }
                        else{
                            Log.d("Tag","Add Not Loaded");

                        }
                    }
                });
            }
        },30,30, TimeUnit.SECONDS);
    }


}


