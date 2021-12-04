package com.codepath.petnetcodepath.petnet.models.utilities;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import java.util.List;

public abstract class FirestoreModel {
    protected Task<Void> save(String id) {
        return getQueryController(getClass()).save(id, this);
    }

    protected static <T extends FirestoreModel> Task<T> findOne(Class<T> cls, Query query, T defaultValue) {
        return getQueryController(cls).findOne(query, defaultValue);
    }

    protected static <T extends FirestoreModel> Task<T> findById(Class<T> cls, String id, T defaultValue) {
        return getQueryController(cls).findById(id, defaultValue);
    }

    protected static <T extends FirestoreModel> Task<List<T>> findAll(Class<T> cls) {
        return getQueryController(cls).findAll();
    }

    protected static <T extends FirestoreModel> Task<List<T>> findAll(Class<T> cls, Query query) {
        return getQueryController(cls).findAll(query);
    }

    protected static <T extends FirestoreModel> void observeAll(Class<T> cls, Query query, ListObserver<T> observer) {
        getQueryController(cls).observeAll(query, observer);
    }

    protected static <T extends FirestoreModel> CollectionReference getCollectionReference(Class<T> cls) {
        return getQueryController(cls).getCollectionReference();
    }

    protected static <T extends FirestoreModel> FirestoreQueryController<T> getQueryController(Class<T> cls) {
        return new FirestoreQueryController<>(cls);
    }
}