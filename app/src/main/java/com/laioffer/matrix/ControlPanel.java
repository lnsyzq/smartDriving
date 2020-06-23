package com.laioffer.matrix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;

public class ControlPanel extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.baseline_home_black_18dp);

        drawerLayout = findViewById(R.id.drawer_layout);

        // location tracker
        final LocationTracker mLocationTracker = new LocationTracker(this);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                // Response when the drawer's position changes
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                final TextView user_textview = (TextView) drawerView.findViewById(R.id.user_name);
                final TextView location_textview = (TextView) drawerView.findViewById(R.id.user_location);
                // Respond when the drawer is opened
                mLocationTracker.getLocation();
                final double longtitute = mLocationTracker.getLongitute();
                final double laitute = mLocationTracker.getLatitute();

                if (Config.username == null) {
                    user_textview.setText("");
                    location_textview.setText("");
                } else {
                    user_textview.setText(Config.username);
                    location_textview.setText("Lat = " + new DecimalFormat(".##").format(laitute) + ".Lon = " + new DecimalFormat(".##").format(longtitute));
                }
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                // Respond when the drawer is closed
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                // Respond when the drawer motion state changes
            }
        });

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is lapped
                        drawerLayout.closeDrawers();
                        // update UI based on the item selected
                        // swap UI fragment
                        if (menuItem.getItemId() == R.id.drawer_layout) {
                            Config.username = null;
                            logout();
                        }
                        return true;
                    }
                }
        );
    }

    private void logout() {
        finish();
    }

}
