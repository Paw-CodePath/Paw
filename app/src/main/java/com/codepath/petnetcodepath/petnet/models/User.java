package com.codepath.petnetcodepath.petnet.models;

import com.codepath.petnetcodepath.petnet.models.utilities.FirestoreCollectionName;
import com.codepath.petnetcodepath.petnet.models.utilities.FirestoreModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

@FirestoreCollectionName("users")
public class User extends FirestoreModel implements Serializable {
    private String id;
    private String name;
    private String description;
    private String imageUrl;

    public User() {
        // Required no-argument constructor
    }

    public User(String id, String name, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Task<Void> save() {
        return save(id);
    }

    public static Task<User> getCurrentUser() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        User defaultValue = new User(id, "", "", "");
        return findById(User.class, id, defaultValue);
    }
}
