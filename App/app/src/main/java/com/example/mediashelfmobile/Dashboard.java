// This file contains generated code for debugging
package com.example.mediashelfmobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class Dashboard extends AppCompatActivity implements MediaAdapter.OnItemLongClickListener {

    private MediaRepository repository;
    private int currentUserId;
    private RecyclerView recyclerView;
    private MediaAdapter adapter;
    private String currentType = "Game";
    private TextInputEditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);

        repository = new MediaRepository(this);
        currentUserId = getIntent().getIntExtra("USER_ID", -1);

        if (currentUserId == -1) {
            finish();
            return;
        }

        recyclerView = findViewById(R.id.recycler_view_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MediaAdapter(this);
        recyclerView.setAdapter(adapter);

        searchInput = findViewById(R.id.search_input);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        FloatingActionButton fab = findViewById(R.id.fab_add);

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_games) {
                    currentType = "Game";
                } else if (id == R.id.nav_movies) {
                    currentType = "Movie";
                } else if (id == R.id.nav_music) {
                    currentType = "Music";
                }
                loadItems();
                return true;
            }
        });

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, add_edit.class);
            intent.putExtra("USER_ID", currentUserId);
            startActivity(intent);
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadItems();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        loadItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }

    @Override
    public void onItemLongClick(MediaItem item) {
        Intent intent = new Intent(Dashboard.this, add_edit.class);
        intent.putExtra("USER_ID", currentUserId);
        intent.putExtra("MEDIA_ID", item.mediaId);
        startActivity(intent);
        Toast.makeText(this, "Editing " + item.title, Toast.LENGTH_SHORT).show();
    }

    private void loadItems() {
        String searchTerm = "";
        if (searchInput.getText() != null) {
            searchTerm = searchInput.getText().toString();
        }

        List<MediaItem> items = repository.getMediaByTypeAndSearch(currentUserId, currentType, searchTerm);
        adapter.setItems(items);
    }
}