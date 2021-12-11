package com.paw.pawapp.helpers;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.UUID;

public class CameraHelper implements ActivityResultCallback<ActivityResult> {
    private static final String TAG = "CameraHelper";

    private final Context context;
    private final ActivityResultLauncher<Intent> activityResultLauncher;
    private Callback callback;
    private File imageFile;

    public CameraHelper(ComponentActivity activity) {
        context = activity;
        activityResultLauncher = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this);
    }

    public File getImageFile() {
        if (imageFile != null && imageFile.exists()) {
            return imageFile;
        }
        return null;
    }

    public void launchCamera(Callback cameraCallback) {
        imageFile = createImageFile();
        callback = cameraCallback;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(imageFile));

        activityResultLauncher.launch(intent);
    }

    @Override
    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() != RESULT_OK) {
            return;
        }

        assert imageFile != null;
        assert callback != null;

        if (imageFile.exists()) {
            callback.onImageSelected(imageFile);
        } else {
            AlertHelper.present(context, "Cannot Select Image", "Sorry, something went wrong.");
        }
    }

    private File createImageFile() {
        // Find the package-specific image storage directory.
        // This means we won’t have to request read/write permissions.
        File imagesDirectory = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);
        assert imagesDirectory.exists() || imagesDirectory.mkdirs();

        String fileName = "image-" + UUID.randomUUID().toString() + ".jpg";

        return new File(imagesDirectory.getPath() + File.separator + fileName);
    }

    private Uri getUriForFile(File file) {
        return FileProvider.getUriForFile(context, "com.paw.pawapp.fileprovider", file);
    }

    public interface Callback {
        void onImageSelected(File newImageFile);
    }
}
