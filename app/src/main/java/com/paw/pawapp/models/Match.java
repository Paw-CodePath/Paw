package com.paw.pawapp.models;

import com.google.firebase.firestore.ListenerRegistration;
import com.paw.pawapp.models.utilities.FirestoreCollectionName;
import com.paw.pawapp.models.utilities.FirestoreModel;
import com.paw.pawapp.models.utilities.ListObserver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

import java.io.Serializable;

@FirestoreCollectionName("matches")
public class Match extends FirestoreModel implements Serializable {
    private String id;
    private User me;
    private User other;
    private Pet pet;
    private Type type;

    public enum Type {
        USER_TO_PET, PET_TO_USER
    }

    public static ListenerRegistration findAll(ListObserver<Match> observer) {
        String userId = FirebaseAuth.getInstance().getUid();
        Query query = getCollectionReference(Match.class).whereEqualTo("me.id", userId);
        return observeAll(Match.class, query, observer);
    }

    public String getId() {
        return id;
    }

    public User getMe() {
        return me;
    }

    public User getOther() {
        return other;
    }

    public Pet getPet() {
        return pet;
    }

    public Type getType() {
        return type;
    }
}
