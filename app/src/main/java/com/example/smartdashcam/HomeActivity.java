package com.example.smartdashcam;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawers;

//    Button Signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//      Signout = (Button) findViewById(R.id.btnsignout);
        Toolbar toolbars = findViewById(R.id.toolbars);
        setSupportActionBar(toolbars);

        drawers = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_views);
        navigationView.setNavigationItemSelectedListener(this);



//        RotateAnimation in menu bar icons
//        toggle is to take care of the rotate
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawers, toolbars,
                R.string.navigationopen, R.string.navigationclose);
        drawers.addDrawerListener(toggle);

        toggle.syncState();



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
                    new FragmentHomeActivity()).commit();
            navigationView.setCheckedItem(R.id.navigation_home);

        }

//        Signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem i) {
        switch (i.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
                        new FragmentHomeActivity()).commit();
                break;
            case R.id.navigation_record:
                record();
                break;

            case R.id.navigation_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
                        new FragmentSettingActivity()).commit();
                break;

            case R.id.navigation_weather:
                weather();
                break;

            case R.id.navigation_path:
                path();
                break;

            case R.id.navigation_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
                        new FragmentAboutActivity()).commit();
                break;

            case R.id.navigation_share:
                Toast.makeText(this,"Shared Successfully ！",Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_signout:
                signout();
                break;
        }

        drawers.closeDrawer(GravityCompat.START);
//        item been selected so is true
        return true;

    }

    @Override
    public void onBackPressed() {
        if (drawers.isDrawerOpen(GravityCompat.START)){
            drawers.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }



    public void signout() {
        Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
        startActivity(intent);
    }

    public void record() {
        Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
        startActivity(intent);
    }

    public void weather() {
        Intent intent = new Intent(HomeActivity.this, WeatherActivity.class);
        startActivity(intent);
    }

    public void path() {
        Intent intent = new Intent(HomeActivity.this, PathActivity.class);
        startActivity(intent);
    }


}