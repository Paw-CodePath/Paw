package com.codepath.petnetcodepath.petnet.models;

import com.codepath.petnetcodepath.petnet.models.utilities.FirestoreCollectionName;
import com.codepath.petnetcodepath.petnet.models.utilities.FirestoreModel;
import com.google.android.gms.tasks.Task;

import java.io.Serializable;

@FirestoreCollectionName("swipes")
public class Swipe extends FirestoreModel implements Serializable {
    private final String id;
    private final User user;
    private final Pet pet;
    private final boolean isMatch;

    public Swipe(User user, Pet pet, boolean isMatch) {
        this.id = getCollectionReference(Swipe.class).document().getId();
        this.user = user;
        this.pet = pet;
        this.isMatch = isMatch;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Pet getPet() {
        return pet;
    }

    public boolean isMatch() {
        return isMatch;
    }

    public Task<Void> save() {
        return save(id);
    }
}
