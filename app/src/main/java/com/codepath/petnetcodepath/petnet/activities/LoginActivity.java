package com.codepath.petnetcodepath.petnet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codepath.petnetcodepath.petnet.R;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    Button btnSignup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Skip the login activity if the user is already signed in.
        if (ParseUser.getCurrentUser() != null) {
            launchActivity(MainActivity.class);
            return;
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnLogin.setOnClickListener(view -> {
            Log.d(TAG, "Login button clicked");
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            logInUser(username, password);
        });

        btnSignup.setOnClickListener(view -> {
            Log.d(TAG, "Sign up button clicked");
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            signUpUser(username, password);
        });
    }

    private void logInUser(String username, String password) {
        Log.i(TAG, String.format("Logging in user “%s”", username));

        ParseUser.logInInBackground(username, password, (user, e) -> {
            if (e == null) {
                Toast.makeText(this, "Signed in successfully!", Toast.LENGTH_SHORT).show();
                launchActivity(MainActivity.class);
            } else {
                Log.e(TAG, "Cannot sign in user", e);
                Toast.makeText(this, String.format("Cannot sign you in: %s", e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUpUser(String username, String password) {
        Log.i(TAG, String.format("Creating account with username “%s”", username));

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(e -> {
            if (e == null) {
                Toast.makeText(this, "Signed up successfully!", Toast.LENGTH_SHORT).show();
                launchActivity(ProfileSetupActivity.class);
            } else {
                Log.e(TAG, "Cannot sign up user", e);
                Toast.makeText(this, String.format("Cannot sign you up: %s", e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchActivity(Class<?> activityClass) {
        Intent i = new Intent(this, activityClass);
        startActivity(i);
        finish(); // Finish activity so the back button doesn’t take users back here
    }
}
