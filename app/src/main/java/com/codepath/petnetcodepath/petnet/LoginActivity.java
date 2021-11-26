package com.codepath.petnetcodepath.petnet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    //create a tag for logging
    public static final String TAG = "LoginActivity";

    // add reference to all of the widgets
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    Button btnSignup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // if the user is already logged in then just go to the main activity otherwise the user will be asked to log in each time
        if(ParseUser.getCurrentUser() != null)
        {
            goToMainActivity();
        }


        // inside the onCreate refer to the widgets using findViewByID
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Onclick Signup Button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signupUser(username, password);
            }
        });

        //set a on CLICK LISTENER on the button add the "new View.OnClickListener()" as a parameter
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logging data in case of a bug
                Log.i(TAG, "Onclick Login Button");

                //onclick grab the username and the password and pass it to the login user method

                // get the test from the widget and convert to a string
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });
    }

    private void signupUser(String username, String password) {
        Log.i(TAG, "Attempting to signup the user: " + username);

        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    goToProfileSetupActivity();
                    Toast.makeText(LoginActivity.this, "Successfully signed up!", Toast.LENGTH_SHORT).show();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG, "Issue with signup", e);
                    Toast.makeText(LoginActivity.this, "Issue with Sign Up", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // this method will take the username and password from the widgets when the login button is clicked
    // and attempt to login the user. IF correct navigate to the main activity
    private void loginUser(String username, String password) {
        //log statement for debugging purposes
        Log.i(TAG, "Attempting to log in the user " + username);

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // if e is not null the login did not succeeded
                if(e != null)
                {
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
                // if succeeded navigate to the main activity
                goToMainActivity();
                Toast.makeText(LoginActivity.this, "Successfully Logged In!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToMainActivity() {
        //using intent
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish(); // finish the login activity once the login succeeded
    }


    private void goToProfileSetupActivity() {
        //using intent
        Intent i = new Intent(this, ProfileSetupActivity.class);
        startActivity(i);
        finish(); // finish the login activity once the login succeeded
    }
}
