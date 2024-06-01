package com.example.commonintents;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AlarmActivity extends AppCompatActivity {
    private TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alarm);
        timePicker = findViewById(R.id.timePicker);
    }
    private void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        startActivity(intent);
        Toast.makeText(this, "Alarm set for " + hour + ":" + minutes, Toast.LENGTH_SHORT).show();
    }

    public void setAlarm(View view) {
        //get the current select hour and minute in the time picker
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        //set the alarm
        createAlarm("Wake Up", hour, minute);
    }
}


