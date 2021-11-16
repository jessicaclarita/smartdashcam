package com.example.smartdashcam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WeatherActivity extends AppCompatActivity {

    final String API_ID = "c591902c74ce752b570d2dc70352885c";
    final String WEA_URL = "https://api.openweathermap.org/data/2.5/weather";


    final long TIME = 2000; //means 2 sec
    final float DISTANCE = 1000; //1 meter
    final int REQUEST = 101;


    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView CityName, condition, Temperature;
    ImageView ImgIcon, BackButton;
    RelativeLayout Founder;


    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        BackButton = findViewById(R.id.BtnBack);
        condition = findViewById(R.id.condition);
        Temperature = findViewById(R.id.temperatures);
        ImgIcon = findViewById(R.id.ImgIcon);
        Founder = findViewById(R.id.Founder);
        CityName = findViewById(R.id.city_name);


        Founder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, SearchWeatherActivity.class);
                startActivity(intent);
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

 /*   @Override
   protected void onResume() {
       super.onResume();
       getWeatherForCurrentLocation();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        Intent mIntent=getIntent();
        String city= mIntent.getStringExtra("City");
        if(city!=null)
        {
            getWeatherForNewCity(city);
        }
        else
        {
            getWeatherForCurrentLocation();
        }


    }


    private void getWeatherForNewCity(String city)
    {
        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",API_ID);
        DoNetworking(params);

    }




    private void getWeatherForCurrentLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                RequestParams params =new RequestParams();
                params.put("lat" ,Latitude);
                params.put("lon",Longitude);
                params.put("appid",API_ID);
                DoNetworking(params);




            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //user allow the location but not able to get location
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // To Do : Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST);
            return;
        }
        locationManager.requestLocationUpdates(Location_Provider, TIME, DISTANCE, locationListener);

    }


    //check user allow location or not
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode==REQUEST)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(WeatherActivity.this,"Location Successful",Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            }
            else
            {
                Toast.makeText(WeatherActivity.this,"Location Failed",Toast.LENGTH_SHORT).show();
                //user denied the permission
            }
        }


    }



    private  void DoNetworking(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEA_URL,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast.makeText(WeatherActivity.this,"Data Get ! ",Toast.LENGTH_SHORT).show();

                DataofWeatherActivity dataofWeatherActivity= DataofWeatherActivity.fromJson(response);
                update(dataofWeatherActivity);


                // super.onSuccess(statusCode, headers, response);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });



    }

    private  void update(DataofWeatherActivity weather){


        Temperature.setText(weather.getTemperature());
        CityName.setText(weather.getCity());
        condition.setText(weather.getWeather());
        int resource=getResources().getIdentifier(weather.getIcon(),"drawable",getPackageName());
        ImgIcon.setImageResource(resource);


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager!=null)
        {
            locationManager.removeUpdates(locationListener);
        }
    }
}