// This file contains generated code for debugging
package com.example.mediashelfmobile;

import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("MediaShelfPrefs", MODE_PRIVATE);
        int userId = preferences.getInt("USER_ID", -1);

        if(userId != -1){
            Intent intent = new Intent(this, Dashboard.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
            finish();
        }
        else{
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new login_signup())
                        .commit();
            }
        }

    }
}