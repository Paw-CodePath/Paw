package com.paw.pawapp.models;

import com.google.android.gms.tasks.Task;
import com.paw.pawapp.models.utilities.FirestoreCollectionName;
import com.paw.pawapp.models.utilities.FirestoreModel;

import java.io.Serializable;

@FirestoreCollectionName("swipes")
public class Swipe extends FirestoreModel implements Serializable {
    private String id;
    private User user;
    private Pet pet;
    private boolean isLike;

    public Swipe() {
    }

    public Swipe(User user, Pet pet, boolean isLike) {
        this.id = getCollectionReference(Swipe.class).document().getId();
        this.user = user;
        this.pet = pet;
        this.isLike = isLike;
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

    public boolean getIsLike() {
        return isLike;
    }

    public Task<Void> save() {
        return save(id);
    }
}
