package com.paw.pawapp.models;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;
import com.paw.pawapp.models.utilities.FirestoreCollectionName;
import com.paw.pawapp.models.utilities.FirestoreModel;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@FirestoreCollectionName("pets")
public class Pet extends FirestoreModel implements Serializable {
    private String id;
    private String name;
    private String description;
    private String species;
    private String imageUrl;
    private User owner;

    public Pet() {
        // Required no-argument constructor
    }

    public static Task<List<Pet>> findAll() {
        return findAll(Pet.class);
    }

    public static Task<List<Pet>> findUnseen() {
        String userId = FirebaseAuth.getInstance().getUid();
        Query swipeQuery = getCollectionReference(Swipe.class).whereEqualTo("user.id", userId);
        return findAll(Swipe.class, swipeQuery)
                .continueWithTask(task -> {
                    // Use a Firestore query to exclude pets we’ve already swiped on.
                    Log.d("Pet", "Finding pets...");

                    List<Swipe> swipes = task.getResult();

                    if (swipes.isEmpty()) {
                        return findAll(Pet.class);
                    }

                    List<String> seenPetIds = swipes.stream().map(swipe -> swipe.getPet().getId()).collect(Collectors.toList());
                    Query petQuery = Pet.getCollectionReference(Pet.class).whereNotIn("id", seenPetIds);
                    return findAll(Pet.class, petQuery);
                })
                .continueWith(task -> {
                    // Filter locally to exclude our own pets.

                    List<Pet> pets = task.getResult();
                    return pets.stream()
                            .filter(pet -> pet.getOwner().getId() != userId)
                            .collect(Collectors.toList());
                });
    }

    public static Task<Pet> findForUser(User user) {
        Query query = getCollectionReference(Pet.class)
                .whereEqualTo("owner.id", user.getId())
                .limit(1);
        Pet defaultValue = new Pet();
        defaultValue.setId(getCollectionReference(Pet.class).document().getId());
        defaultValue.setOwner(user);
        return findOne(Pet.class, query, defaultValue);
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Task<Void> save() {
        return save(id);
    }
}