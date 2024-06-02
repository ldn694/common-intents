package com.example.commonintents;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    public void onClickView(View view) {
         //check if view is a material button
        if (view instanceof MaterialButton) {
            //get the id of the view
            int id = view.getId();
            if (id == R.id.SetAlarmButton) {
                startActivity(new Intent(this, AlarmActivity.class));
            }
            else if (id == R.id.ShowAlarmButton) {
                showAlarm();
            }
            else if (id == R.id.TurnOnCameraButton) {
                capturePhoto();
            }
            else if (id == R.id.TurnOnVideoButton) {
                captureVideo();
            }
            else if (id == R.id.GetLocationButton) {
                getLocation();
            }
            else if (id == R.id.NewMailButton) {
                startActivity(new Intent(this, MailActivity.class));
            }
            else if (id == R.id.YoutubeSearchButton) {
                startActivity(new Intent(this, YoutubeActivity.class));
            }
            else if (id == R.id.OpenSettingsButton) {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            }

        }
    }

    private void showAlarm() {
        Intent intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
        startActivity(intent);
    }

    public void capturePhoto() {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        startActivity(intent);
    }

    public void captureVideo() {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA);
        startActivity(intent);
    }

    static final int REQUEST_SELECT_CONTACT = 1;

    public void getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                Location location = task.getResult();
                                String geoLocation = "geo:0,0?q=" + location.getLatitude() + ", " + location.getLongitude();
                                showMap(Uri.parse(geoLocation));
                            } else {
                                Toast.makeText(MainActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }
    private void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        startActivity(intent);
    }


}