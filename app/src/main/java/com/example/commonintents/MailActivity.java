package com.example.commonintents;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MailActivity extends AppCompatActivity {
    private EditText recipientEditText;
    private EditText subjectEditText;
    private EditText bodyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        recipientEditText = findViewById(R.id.recipientEditText);
        subjectEditText = findViewById(R.id.subjectEditText);
        bodyEditText = findViewById(R.id.bodyEditText);
    }

    private void composeEmail(String[] addresses, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(intent);
    }

    public void sendEmail(View view) {
        String recipient = recipientEditText.getText().toString();
        String subject = subjectEditText.getText().toString();
        String body = bodyEditText.getText().toString();
        String[] addresses = recipient.split(",");
        if (addresses.length == 0) {
            Toast.makeText(this, "Please enter at least one recipient", Toast.LENGTH_SHORT).show();
            return;
        }
        // Delete space in addresses
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = addresses[i].trim();
        }
        if (subject.isEmpty()) {
            Toast.makeText(this, "Please enter a subject", Toast.LENGTH_SHORT).show();
            return;
        }
        if (body.isEmpty()) {
            Toast.makeText(this, "Please enter a body", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            composeEmail(addresses, subject, body);
            Toast.makeText(this, "Email sent successfully", Toast.LENGTH_SHORT).show();
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }
}
