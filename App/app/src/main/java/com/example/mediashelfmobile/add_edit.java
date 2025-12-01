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
    private int mediaIdToEdit = -1; // -1 means we are creating a NEW item
    private MediaItem currentItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_edit);

        // Retrieve USER_ID and MEDIA_ID from the Intent
        userId = getIntent().getIntExtra("USER_ID", -1);
        mediaIdToEdit = getIntent().getIntExtra("MEDIA_ID", -1);

        if (userId == -1) {
            Toast.makeText(this, "Error: User not identified", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        repository = new MediaRepository(this);

        // Initialize Views
        typeSpinner = findViewById(R.id.spinner_media_type);
        titleInput = findViewById(R.id.edit_text_title);
        creatorInput = findViewById(R.id.edit_text_creator);
        genreInput = findViewById(R.id.edit_text_genre);
        platformInput = findViewById(R.id.edit_text_platform);
        notesInput = findViewById(R.id.edit_text_notes);
        ratingBar = findViewById(R.id.rating_bar);
        saveButton = findViewById(R.id.button_save);
        cancelButton = findViewById(R.id.button_cancel);

        // Setup Spinner
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"Game", "Movie", "Music"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        // CHECK FOR EDIT MODE: If mediaIdToEdit is valid, load the item
        if (mediaIdToEdit != -1) {
            currentItem = repository.getMediaItemById(mediaIdToEdit);
            if (currentItem != null) {
                // Populate fields with existing data
                titleInput.setText(currentItem.title);
                creatorInput.setText(currentItem.creator);
                genreInput.setText(currentItem.genre);
                platformInput.setText(currentItem.platform);
                notesInput.setText(currentItem.notes);
                ratingBar.setRating(currentItem.rating);

                // Set spinner to the correct type
                int spinnerPos = adapter.getPosition(currentItem.type);
                typeSpinner.setSelection(spinnerPos);

                // Change button text to indicate an update
                saveButton.setText("Update Item");
            }
        }

        // Setup Button Listeners
        saveButton.setOnClickListener(v -> saveMediaItem());
        cancelButton.setOnClickListener(v -> finish()); // Close activity on cancel
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
            // EDIT MODE: Update the existing item object
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
            // ADD MODE: Create a new item object
            // Note: The constructor order must match MediaItem.java logic exactly
            MediaItem newItem = new MediaItem(
                    title, type, creator, platform, genre, rating, notes, "Owned", userId
            );
            repository.addMediaItem(newItem);
            Toast.makeText(this, "Item saved!", Toast.LENGTH_SHORT).show();
        }

        // Close the activity and return to the Dashboard list
        finish();
    }
}