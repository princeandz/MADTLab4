package com.example.madtlab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeleteNoteActivity extends AppCompatActivity {

    private ListView deleteNotesListView;
    private ArrayAdapter<String> deleteNotesAdapter;
    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        deleteNotesListView = findViewById(R.id.deleteNotesListView);
        notesList = getIntent().getStringArrayListExtra("notesList");

        deleteNotesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        deleteNotesListView.setAdapter(deleteNotesAdapter);

        deleteNotesListView.setOnItemClickListener((parent, view, position, id) -> {
            String noteToDelete = notesList.get(position);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("deletedNote", noteToDelete);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}

