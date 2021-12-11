package com.paw.pawapp.models.utilities;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirestoreQueryController<T extends FirestoreModel> {
    Class<T> cls;

    public FirestoreQueryController(Class<T> cls) {
        this.cls = cls;
    }

    public CollectionReference getCollectionReference() {
        FirestoreCollectionName name = cls.getAnnotation(FirestoreCollectionName.class);
        if (name == null) {
            throw new IllegalArgumentException("FirestoreCollectionName not provided for " + cls.getName());
        }
        return FirebaseFirestore.getInstance().collection(name.value());
    }

    public Task<Void> save(String id, FirestoreModel item) {
        return getCollectionReference().document(id).set(item);
    }

    public Task<T> findOne(Query query, T defaultValue) {
        return query.get()
                .continueWith(new Continuation<QuerySnapshot, T>() {
                    @Override
                    public T then(@NonNull Task<QuerySnapshot> task) throws Exception {
                        QuerySnapshot snapshot = task.getResult();
                        if (snapshot.isEmpty()) {
                            return defaultValue;
                        }

                        assert snapshot.size() == 1;
                        DocumentSnapshot document = snapshot.getDocuments().get(0);
                        return document.toObject(cls);
                    }
                });
    }

    public Task<T> findById(String id, T defaultValue) {
        return getCollectionReference().document(id).get()
                .continueWith(new Continuation<DocumentSnapshot, T>() {
                    @Override
                    public T then(@NonNull Task<DocumentSnapshot> task) throws Exception {
                        DocumentSnapshot snapshot = task.getResult();
                        if (snapshot.exists()) {
                            return snapshot.toObject(cls);
                        }
                        return defaultValue;
                    }
                });
    }

    public Task<List<T>> findAll() {
        return findAll(getCollectionReference());
    }

    public Task<List<T>> findAll(Query query) {
        return query.get()
                .continueWith(new Continuation<QuerySnapshot, List<T>>() {
                    @Override
                    public List<T> then(@NonNull Task<QuerySnapshot> task) throws Exception {
                        QuerySnapshot snapshot = task.getResult();
                        List<T> items = new ArrayList<>();
                        for (DocumentSnapshot document : snapshot.getDocuments()) {
                            T item = document.toObject(cls);
                            items.add(item);
                        }
                        return items;
                    }
                });
    }

    public ListenerRegistration observeAll(Query query, ListObserver<T> observer) {
        return query.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                observer.onError(e);
                return;
            }
            List<T> items = new ArrayList<>();
            for (DocumentSnapshot document : snapshot.getDocuments()) {
                T item = document.toObject(cls);
                items.add(item);
            }
            observer.onUpdate(items);
        });
    }
}
