package com.example.madtlab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddNoteActivity extends AppCompatActivity {

    private EditText noteNameEditText, noteContentEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        // Initialize UI elements
        noteNameEditText = findViewById(R.id.noteNameEditText);
        noteContentEditText = findViewById(R.id.noteContentEditText);
        saveButton = findViewById(R.id.saveNoteButton);

        // Set click listener for the Save button
        saveButton.setOnClickListener(view -> {
            String noteName = noteNameEditText.getText().toString().trim();
            String noteContent = noteContentEditText.getText().toString().trim();

            if (noteName.isEmpty() || noteContent.isEmpty()) {
                Toast.makeText(this, R.string.empty_field_warning, Toast.LENGTH_SHORT).show();
            } else {
                // Return the new note to MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newNote", noteName + ": " + noteContent);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}

