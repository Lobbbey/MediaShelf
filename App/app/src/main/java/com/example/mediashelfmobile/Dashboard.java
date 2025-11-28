// This file contains generated code to assist with debugging and logic
package com.example.mediashelfmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediashelfmobile.database.MediaItem;
import com.example.mediashelfmobile.database.MediaRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class Dashboard extends AppCompatActivity {

    private MediaRepository repository;
    private int currentUserId;
    private RecyclerView recyclerView;
    private String currentType = "Game";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);

        repository = new MediaRepository(this);
        currentUserId = getIntent().getIntExtra("USER_ID", -1);

        if (currentUserId == -1) {
            // Error handling if user isn't passed correctly
            finish();
            return;
        }

        recyclerView = findViewById(R.id.recycler_view_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        FloatingActionButton fab = findViewById(R.id.fab_add);

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_games) {
                    currentType = "Game";
                    loadItems();
                    return true;
                } else if (id == R.id.nav_movies) {
                    currentType = "Movie";
                    loadItems();
                    return true;
                } else if (id == R.id.nav_music) {
                    currentType = "Music";
                    loadItems();
                    return true;
                }
                return false;
            }
        });

        fab.setOnClickListener(v -> {
            // Navigate to Add Activity
            Intent intent = new Intent(Dashboard.this, add_edit.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });

        loadItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }

    private void loadItems() {
        List<MediaItem> items = repository.getMediaByType(currentUserId, currentType);
        Toast.makeText(this, "Loaded " + items.size() + " " + currentType + "s", Toast.LENGTH_SHORT).show();
    }
}