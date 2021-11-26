package com.codepath.petnetcodepath.petnet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    private static final String TAG = "UserProfileActivity";
    TextView tvUserImage;
    ImageView ivUserImage;
    Button btnUserAddImage;
    TextView tvUserDescription;
    EditText etUserDescription;
    Button btnUserSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        tvUserImage = findViewById(R.id.tvUserImage);
        ivUserImage = findViewById(R.id.ivUserImage);
        btnUserAddImage = findViewById(R.id.btnUserAddImage);
        tvUserDescription = findViewById(R.id.tvUserDescription);
        etUserDescription = findViewById(R.id.etUserDescription);
        btnUserSave = findViewById(R.id.btnUserSave);
        //queryUsers();

        btnUserSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = etUserDescription.getText().toString();
                if(description.isEmpty()){
                    Toast.makeText(UserProfileActivity.this, "Description cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, currentUser);
            }
        });
    }

    private void savePost(String description, ParseUser currentUser) {

    }

    protected void queryUsers() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if(e != null)
                {
                    Log.e(TAG, "Issue with getting user", e);
                    return;
                }

                for(ParseUser user : users) {
                    Log.i(TAG, "User: " + user.getUsername() + "Desc: " + user.get("userdesc"));
                }
            }
        });
    }

}
