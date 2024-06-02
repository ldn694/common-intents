package com.example.commonintents;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    private EditText hourText;
    private EditText minuteText;
    private EditText secondText;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private Button startButton;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        hourText = findViewById(R.id.edit_hour);
        minuteText = findViewById(R.id.edit_minute);
        secondText = findViewById(R.id.edit_second);
        hourText.setText("00");
        minuteText.setText("00");
        secondText.setText("00");
        progressBar = findViewById(R.id.progress_bar);
        startButton = findViewById(R.id.start_button);
    }

    public void toggleStartButton(View view) {
        if (isRunning) {
            startButton.setText("Start");
            //check if the timer is running and cancel it
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            hourText.setEnabled(true);
            minuteText.setEnabled(true);
            secondText.setEnabled(true);
        } else {
            String hourString = hourText.getText().toString();
            String minuteString = minuteText.getText().toString();
            String secondString = secondText.getText().toString();
            //check if the input is empty
            if (hourString.isEmpty() || minuteString.isEmpty() || secondString.isEmpty()) {
                Toast.makeText(this, "Please enter all values", Toast.LENGTH_SHORT).show();
                return;
            }
            int hour = Integer.parseInt(hourString);
            int minute = Integer.parseInt(minuteString);
            int second = Integer.parseInt(secondString);
            //check if the input is valid
            if (hour < 0 || minute < 0 || second < 0 || hour > 99 || minute > 59 || second > 59) {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
                return;
            }
            startButton.setText("Stop");
            int totalSeconds = hour * 3600 + minute * 60 + second;
            progressBar.setMax(totalSeconds * 1000);
            countDownTimer = new CountDownTimer(totalSeconds * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int progress = (int) ((totalSeconds - millisUntilFinished / 1000) * 1000);
                    progressBar.setProgress(progress);
                    // Update hour, minute and second text views
                    int hours = (int) (millisUntilFinished / (1000 * 60 * 60));
                    int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                    int seconds = (int) ((millisUntilFinished / 1000) % 60);
                    hourText.setText(String.format("%02d", hours));
                    minuteText.setText(String.format("%02d", minutes));
                    secondText.setText(String.format("%02d", seconds));
                }
                @Override
                public void onFinish() {
                    progressBar.setProgress(0);
                    startButton.setText("Start");
                    isRunning = false;
                }
            };
            countDownTimer.start();
            hourText.setEnabled(false);
            minuteText.setEnabled(false);
            secondText.setEnabled(false);
        }
        isRunning = !isRunning;
    }

    public void reset(View view) {
        progressBar.setProgress(0);
        hourText.setText("00");
        minuteText.setText("00");
        secondText.setText("00");
        startButton.setText("Start");
        isRunning = false;
        hourText.setEnabled(true);
        minuteText.setEnabled(true);
        secondText.setEnabled(true);
        if (countDownTimer != null)
            countDownTimer.cancel();
    }
}
