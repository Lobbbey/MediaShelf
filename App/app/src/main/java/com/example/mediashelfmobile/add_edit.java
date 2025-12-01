// This file contains generated code for debugging
package com.example.mediashelfmobile;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mediashelfmobile.database.MediaItem;
import com.example.mediashelfmobile.database.MediaRepository;

public class add_edit extends AppCompatActivity {

    private Spinner typeSpinner;
    private EditText titleInput, creatorInput, genreInput, platformInput, notesInput;
    private RatingBar ratingBar;
    private Button saveButton, cancelButton;

    private MediaRepository repository;
    private int userId;
    private int mediaIdToEdit = -1;
    private MediaItem currentItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_edit);

        userId = getIntent().getIntExtra("USER_ID", -1);
        mediaIdToEdit = getIntent().getIntExtra("MEDIA_ID", -1);

        if (userId == -1) {
            Toast.makeText(this, "Error: User not identified", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        repository = new MediaRepository(this);

        typeSpinner = findViewById(R.id.spinner_media_type);
        titleInput = findViewById(R.id.edit_text_title);
        creatorInput = findViewById(R.id.edit_text_creator);
        genreInput = findViewById(R.id.edit_text_genre);
        platformInput = findViewById(R.id.edit_text_platform);
        notesInput = findViewById(R.id.edit_text_notes);
        ratingBar = findViewById(R.id.rating_bar);
        saveButton = findViewById(R.id.button_save);
        cancelButton = findViewById(R.id.button_cancel);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Game", "Movie", "Music"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        if (mediaIdToEdit != -1) {
            currentItem = repository.getMediaItemById(mediaIdToEdit);
            if (currentItem != null) {
                titleInput.setText(currentItem.title);
                creatorInput.setText(currentItem.creator);
                genreInput.setText(currentItem.genre);
                platformInput.setText(currentItem.platform);
                notesInput.setText(currentItem.notes);
                ratingBar.setRating(currentItem.rating);

                int spinnerPos = adapter.getPosition(currentItem.type);
                typeSpinner.setSelection(spinnerPos);

                saveButton.setText("Update Item");
            }
        }

        saveButton.setOnClickListener(v -> saveMediaItem());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveMediaItem() {
        String type = typeSpinner.getSelectedItem().toString();
        String title = titleInput.getText().toString().trim();
        String creator = creatorInput.getText().toString().trim();
        String genre = genreInput.getText().toString().trim();
        String platform = platformInput.getText().toString().trim();
        String notes = notesInput.getText().toString().trim();
        int rating = (int) ratingBar.getRating();

        if (title.isEmpty()) {
            titleInput.setError("Title is required");
            return;
        }

        if (mediaIdToEdit != -1 && currentItem != null) {
            currentItem.type = type;
            currentItem.title = title;
            currentItem.creator = creator;
            currentItem.platform = platform;
            currentItem.genre = genre;
            currentItem.rating = rating;
            currentItem.notes = notes;

            repository.updateMediaItem(currentItem);
            Toast.makeText(this, "Item updated!", Toast.LENGTH_SHORT).show();

        } else {
            MediaItem newItem = new MediaItem(
                    title, type, creator, platform, genre, rating, notes, "Owned", userId
            );
            repository.addMediaItem(newItem);
            Toast.makeText(this, "Item saved!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}