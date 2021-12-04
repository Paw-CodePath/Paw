package com.codepath.petnetcodepath.petnet.models;

import com.codepath.petnetcodepath.petnet.models.utilities.FirestoreCollectionName;
import com.codepath.petnetcodepath.petnet.models.utilities.FirestoreModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.Query;

import java.io.Serializable;
import java.util.List;

@FirestoreCollectionName("matches")
public class Match extends FirestoreModel implements Serializable {
    private String id;
    private User user;
    private Pet pet;

    public enum MatchType {
        PET_TO_USER, USER_TO_PET
    }

    public static Task<List<Match>> findAllForCurrentUser() {
        String userId = FirebaseAuth.getInstance().getUid();
        Query query = getCollectionReference(Match.class).whereArrayContains("userIds", userId);
        return findAll(Match.class, query);
    }

    @Exclude
    public User getMe() {
        switch (getType()) {
            case PET_TO_USER:
                return pet.getOwner();
            case USER_TO_PET:
                return user;
            default:
                return null;
        }
    }

    @Exclude
    public User getOther() {
        switch (getType()) {
            case PET_TO_USER:
                return user;
            case USER_TO_PET:
                return pet.getOwner();
            default:
                return null;
        }
    }

    @Exclude
    public MatchType getType() {
        String uid = FirebaseAuth.getInstance().getUid();

        if (pet.getOwner().getId().equals(uid)) {
            return MatchType.PET_TO_USER;
        } else if (user.getId().equals(uid)) {
            return MatchType.USER_TO_PET;
        }
        throw new IllegalStateException();
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
}
