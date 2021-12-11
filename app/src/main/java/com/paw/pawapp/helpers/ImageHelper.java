package com.paw.pawapp.helpers;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class ImageHelper {
    private static final StorageReference storage = FirebaseStorage.getInstance().getReference();

    public static Task<Uri> uploadImage(String path, File imageFile) {
        StorageReference imageReference = storage.child(path);
        Uri imageUri = Uri.fromFile(imageFile);

        return imageReference.putFile(imageUri)
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return imageReference.getDownloadUrl();
                    }
                });
    }
}
