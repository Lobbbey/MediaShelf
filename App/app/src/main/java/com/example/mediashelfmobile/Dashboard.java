package com.example.mediashelfmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mediashelfmobile.database.MediaItem;
import com.example.mediashelfmobile.database.MediaRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    private MediaRepository repository;
    private int currentUserId;
    private RecyclerView recyclerView;
    // NOTE: You will need to create a RecyclerAdapter. For now, this logic assumes one exists
    // private MediaAdapter adapter;
    private String currentType = "Game"; // Default view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Corrected layout reference to match the existing file "fragment_dashboard.xml"
        setContentView(R.layout.fragment_dashboard);

        // 1. Init Repository and User
        repository = new MediaRepository(this);
        currentUserId = getIntent().getIntExtra("USER_ID", -1);

        if (currentUserId == -1) {
            // Error handling if user isn't passed correctly
            finish();
            return;
        }

        // 2. Setup UI Elements
        recyclerView = findViewById(R.id.recycler_view_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // adapter = new MediaAdapter(new ArrayList<>());
        // recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        FloatingActionButton fab = findViewById(R.id.fab_add);

        // 3. Setup Navigation Listener
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

        // 4. Setup Add Button
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, add_edit.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });

        // 5. Initial Load
        loadItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when returning from Add/Edit screen
        loadItems();
    }

    private void loadItems() {
        // Fetch data from DB based on the selected Tab (currentType)
        List<MediaItem> items = repository.getMediaByType(currentUserId, currentType);

        // Debugging toast to verify it works before you make the adapter
        Toast.makeText(this, "Loaded " + items.size() + " " + currentType + "s", Toast.LENGTH_SHORT).show();

        // Update Adapter (Uncomment once you have the adapter)
        // adapter.setItems(items);
        // adapter.notifyDataSetChanged();
    }
}