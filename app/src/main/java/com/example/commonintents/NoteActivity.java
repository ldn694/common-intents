package com.example.commonintents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.actions.NoteIntents;

public class NoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note);
    }
    private void createNote(String subject, String text) {
        Intent intent = new Intent(NoteIntents.ACTION_CREATE_NOTE)
                .putExtra(NoteIntents.EXTRA_NAME, subject)
                .putExtra(NoteIntents.EXTRA_TEXT, text);
        startActivity(intent);
    }
    public void saveNote(View view) {
        String subject = ((EditText) findViewById(R.id.note_title)).getText().toString();
        String text = ((EditText) findViewById(R.id.note_content)).getText().toString();
        createNote(subject, text);
    }
}
