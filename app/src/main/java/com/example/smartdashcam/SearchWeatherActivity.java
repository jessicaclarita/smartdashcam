package com.example.smartdashcam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchWeatherActivity extends AppCompatActivity {

    ImageView Btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_weather);


        final EditText search=findViewById(R.id.searchforcity);
        Btnback = (ImageView) findViewById(R.id.btngoback);

        Btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String newCities= search.getText().toString();
                Intent intent=new Intent(SearchWeatherActivity.this, WeatherActivity.class);
                intent.putExtra("City",newCities);
                startActivity(intent);



                return false;
            }
        });

    }
}