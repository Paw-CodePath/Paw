package com.codepath.petnetcodepath.petnet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codepath.petnetcodepath.petnet.R;
import com.codepath.petnetcodepath.petnet.helpers.AlertHelper;
import com.codepath.petnetcodepath.petnet.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int PROFILE_SETUP_REQUEST_CODE = 0;

    private EditText etEmailAddress;
    private EditText etPassword;
    private Button btnSignIn;
    private Button btnCreateAccount;

    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Skip the login activity if the user is already signed in.
        if (auth.getCurrentUser() != null) {
            User.getCurrentUser().addOnCompleteListener(task -> {
                User user = task.getResult();

                // Launch the profile setup activity if the user’s profile is incomplete.
                // Otherwise, launch the main activity.
                if (user.getName() == null || user.getDescription() == null || user.getImageUrl() == null) {
                    launchProfileSetupActivity(user);
                } else {
                    launchMainActivity();
                }
            });
            return;
        }

        etEmailAddress = findViewById(R.id.et_email_address);
        etPassword = findViewById(R.id.et_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnCreateAccount = findViewById(R.id.btn_create_account);

        btnSignIn.setOnClickListener(view -> {
            String email = etEmailAddress.getText().toString();
            String password = etPassword.getText().toString();
            signIn(email, password);
        });

        btnCreateAccount.setOnClickListener(view -> {
            String email = etEmailAddress.getText().toString();
            String password = etPassword.getText().toString();
            createAccount(email, password);
        });
    }

    private void signIn(String email, String password) {
        Log.i(TAG, String.format("Logging in user “%s”", email));

        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show();
                    launchMainActivity();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Cannot sign in", e);
                    AlertHelper.present(this, "Cannot Sign In", e);
                });
    }

    private void createAccount(String email, String password) {
        Log.i(TAG, String.format("Creating account with email “%s”", email));

        auth.createUserWithEmailAndPassword(email, password)
                .continueWithTask(new Continuation<AuthResult, Task<User>>() {
                    @Override
                    public Task<User> then(@NonNull Task<AuthResult> task) throws Exception {
                        if (task.isSuccessful()) {
                            return User.getCurrentUser();
                        }
                        throw task.getException();
                    }
                })
                .addOnSuccessListener(user -> {
                    Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();
                    launchProfileSetupActivity(user);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Cannot create account", e);
                    AlertHelper.present(this, "Cannot Create Account", e);
                });
    }

    private void launchMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish(); // Finish activity so the back button doesn’t take users back here
    }

    private void launchProfileSetupActivity(User user) {
        Intent i = new Intent(this, UserProfileActivity.class);
        i.putExtra(UserProfileActivity.KEY_USER, user);
        startActivityForResult(i, PROFILE_SETUP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert requestCode == PROFILE_SETUP_REQUEST_CODE;
        launchMainActivity();
    }
}