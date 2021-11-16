package com.example.smartdashcam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Formatter;
import java.util.Locale;

public class RecordActivity extends AppCompatActivity {

    Button Back;
    private static int CAMERA_PERMISSION = 0;
    private static int VIDEO_RECORD = 1;
    private Uri VideoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_avtivity);

       Back = (Button) findViewById(R.id.btnback);

       Back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
               startActivity(intent);
           }
       });

       if (isCamerainPhone()) {
           Log.i("VIDEO_RECORD_TAG","Camera is detected");
           getPermission();
       }
       else {
           Log.i("VIDEO_RECORD_TAG","No Camera in this phone ！");
       }

    }

    public void  recordVideoButtonPress (View view) {

        recordVideos();
    }

    //check any cam on phone
    private  boolean isCamerainPhone() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            return true;
        }
        else {
            return  false;
        }
    }

    private void getPermission(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) !=
        PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA},
                    CAMERA_PERMISSION);
        }
        else {

        }
    }

    private void recordVideos() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_RECORD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_RECORD) {
            if (resultCode == RESULT_OK) {
                VideoPath = data.getData();
                Log.i("VIDEO_RECORD_TAG", "Video is recorded and available path at : " + VideoPath );
            }

            else if (requestCode == RESULT_CANCELED) {
                Log.i("VIDEO_RECORD_TAG", "Record video is cancel :( ");
            }

            else {
                Log.i("VIDEO_RECORD_TAG", "There has some error on your phone :( ");
            }
        }
    }
}