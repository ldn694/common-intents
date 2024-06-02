package com.example.commonintents;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class SMSActivity extends AppCompatActivity {
    private EditText phoneNumberEditText;
    private EditText smsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        smsEditText = findViewById(R.id.smsEditText);
    }

    public void sendSMS(View view) {
        String phoneNumber = phoneNumberEditText.getText().toString();
        String sms = smsEditText.getText().toString();

        if (phoneNumber.isEmpty() || sms.isEmpty()) {
            Toast.makeText(this, "Please enter both phone number and SMS", Toast.LENGTH_SHORT).show();
        }

        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:" + phoneNumber));
            intent.putExtra(Intent.EXTRA_TEXT, sms);
            startActivity(intent);
            Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "SMS not supported on this device", Toast.LENGTH_SHORT).show();
        }
    }
}
