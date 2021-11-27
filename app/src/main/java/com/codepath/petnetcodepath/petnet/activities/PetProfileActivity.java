package com.codepath.petnetcodepath.petnet.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codepath.petnetcodepath.petnet.R;
import com.codepath.petnetcodepath.petnet.models.Pet;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PetProfileActivity extends AppCompatActivity {
    
    private static final String TAG = "PetProfileActivity";
    public final static int PICK_PHOTO_CODE = 1046;

    public File photoFile;

    TextView tvPetImage;
    ImageView ivPetImage;
    Button btnPetAddImage;
    TextView tvPetDescription;
    EditText etPetDescription;
    Button btnPetSave;
    EditText etPetPref;
    EditText etPetName;
    EditText etPetType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petprofile);

        tvPetImage = findViewById(R.id.tvPetImage);
        ivPetImage = findViewById(R.id.ivPetImage);
        btnPetAddImage = findViewById(R.id.btnUserAddImage);
        tvPetDescription = findViewById(R.id.tvPetDescription);
        etPetDescription = findViewById(R.id.etPetDescription);
        btnPetSave = findViewById(R.id.btnUserSave);
        etPetPref = findViewById(R.id.etPetPref);
        etPetName = findViewById(R.id.etPetName);
        etPetType = findViewById(R.id.etPetType);

        //queryPets();


        btnPetAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaunchGallery();
            }
        });

        btnPetSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = etPetDescription.getText().toString();
                String name = etPetName.getText().toString();
                String type = etPetType.getText().toString();
                String pref = etPetPref.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(PetProfileActivity.this, "Description cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (photoFile == null || ivPetImage.getDrawable() == null) {
                    Toast.makeText(PetProfileActivity.this, "There is no Image!", Toast.LENGTH_SHORT).show();
                }

                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, name, pref, type, currentUser, photoFile);
            }
        });
    }

    private void LaunchGallery() {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Uri photoUri = intent.getData();
        photoFile = new File(photoUri.getPath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }


    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if (Build.VERSION.SDK_INT > 27) {
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            Uri photoUri = data.getData();
            // Load the image located at photoUri into selectedImage
            Bitmap selectedImage = loadFromUri(photoUri);
            // Load the selected image into a preview
            //ImageView ivPreview = (ImageView) findViewById(R.id.ivPetImage);
            ivPetImage.setImageBitmap(selectedImage);
        }
    }

    private void savePost(String description, String name, String pref, String type, ParseUser currentUser, File photoFile) {
        Pet pet = new Pet();
        pet.setDescription(description);
        pet.setName(name);
        pet.setPref(pref);
        pet.setType(type);
        pet.setImage(new ParseFile(photoFile));
        pet.setUser(currentUser);

        pet.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while Saving", e);
                    Toast.makeText(PetProfileActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "Post save was successful!!");
            }
        });
    }

    private void queryPets() {
        ParseQuery<Pet> query = ParseQuery.getQuery(Pet.class);
        query.findInBackground(new FindCallback<Pet>() {
            @Override
            public void done(List<Pet> pets, ParseException e) {

                if (e != null) {
                    Log.e(TAG, "Issue with getting user", e);
                    return;
                }

                for (Pet pet : pets) {
                    Log.i(TAG, "Pet: " + pet.getDescription());
                }
            }
        });
    }
}
