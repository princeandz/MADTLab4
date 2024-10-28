package com.example.madtlab4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private ListView notesListView;
    private Button addNoteButton, deleteNoteButton;
    private ArrayAdapter<String> notesAdapter;
    private ArrayList<String> notesList;
    private SharedPreferences sharedPreferences;
    private static final String NOTES_KEY = "notes_key";

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

        notesListView = findViewById(R.id.notesListView);
        addNoteButton = findViewById(R.id.addNoteButton);
        deleteNoteButton = findViewById(R.id.deleteNoteButton);
        notesList = new ArrayList<>();

        // Load notes from storage
        sharedPreferences = getSharedPreferences("notesApp", MODE_PRIVATE);
        loadNotes();

        // Set up adapter and list view
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        notesListView.setAdapter(notesAdapter);

        // Set up button listeners
        addNoteButton.setOnClickListener(view -> openAddNoteActivity());
        deleteNoteButton.setOnClickListener(view -> openDeleteNoteActivity());
    }

    private void openAddNoteActivity() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, 1);
    }

    private void openDeleteNoteActivity() {
        Intent intent = new Intent(this, DeleteNoteActivity.class);
        intent.putStringArrayListExtra("notesList", notesList);  // Pass current notes
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1 && data != null) {
                String newNote = data.getStringExtra("newNote");
                notesList.add(newNote);
                saveNotes();
                notesAdapter.notifyDataSetChanged();
            } else if (requestCode == 2 && data != null) {
                String deletedNote = data.getStringExtra("deletedNote");
                notesList.remove(deletedNote);
                saveNotes();
                notesAdapter.notifyDataSetChanged();
            }
        }
    }

    private void loadNotes() {
        notesList.addAll(sharedPreferences.getStringSet(NOTES_KEY, new HashSet<>()));
    }

    private void saveNotes() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(NOTES_KEY, new HashSet<>(notesList));
        editor.apply();
    }

}
