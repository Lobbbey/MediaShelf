// This file contains Generated Code to help with storing login sessions
package com.example.mediashelfmobile;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mediashelfmobile.database.MediaRepository;
import com.example.mediashelfmobile.database.User;
import com.google.android.material.textfield.TextInputEditText;

public class login_signup extends Fragment {

    private TextInputEditText usernameInput, passwordInput;
    private Button loginButton, signupButton;
    private MediaRepository mediaRepo;

    // Default Constructor
    public login_signup() {

    }

    public static login_signup newInstance(String param1, String param2) {
        return new login_signup();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getContext() != null){
            mediaRepo = new MediaRepository(getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_signup, container, false);

        // Find UI elements
        usernameInput = view.findViewById(R.id.username_edit_text);
        passwordInput = view.findViewById(R.id.password_edit_text);
        loginButton = view.findViewById(R.id.login_button);
        signupButton = view.findViewById(R.id.signup_button);

        // Set up listeners
        loginButton.setOnClickListener(v -> handleLogin());
        signupButton.setOnClickListener(v -> handleSignup());

        return view;
    }


    private void saveUserSession(int userId) {
        if (getActivity() != null) {
            SharedPreferences prefs = getActivity().getSharedPreferences("MediaShelfPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("USER_ID", userId);
            editor.apply();
        }
    }

    private void handleLogin(){
        String username = usernameInput.getText().toString().trim();
        String password= passwordInput.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = mediaRepo.loginUser(username, password);

        if(user != null){// Successful login

            saveUserSession(user.uid);

            Intent intent = new Intent(getActivity(), Dashboard.class);
            intent.putExtra("USER_ID", user.uid);
            startActivity(intent);

            if(getActivity() != null){// Finish activity so user can't go back to login
                getActivity().finish();
            }
        }
        else{// Failed login
            Toast.makeText(getContext(), "Username or Password was incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSignup(){
        String username = usernameInput.getText().toString().trim();
        String password= passwordInput.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(getContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mediaRepo.checkUserExists(username)){
            Toast.makeText(getContext(), "Username has been taken", Toast.LENGTH_SHORT).show();
        }
        else {
            mediaRepo.RegisterUser(username, password);
            Toast.makeText(getContext(), "Account Created! Please login", Toast.LENGTH_SHORT).show();
        }
    }
}