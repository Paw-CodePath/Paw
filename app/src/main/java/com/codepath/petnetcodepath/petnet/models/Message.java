package com.codepath.petnetcodepath.petnet.models;

import com.codepath.petnetcodepath.petnet.models.utilities.FirestoreCollectionName;
import com.codepath.petnetcodepath.petnet.models.utilities.FirestoreModel;
import com.codepath.petnetcodepath.petnet.models.utilities.ListObserver;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FirestoreCollectionName("messages")
public class Message extends FirestoreModel implements Serializable {
    private String id;
    private String content;
    private User author;
    private User recipient;
    private Date timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    // Database query helper
    public List<String> getUserIds() {
        List<String> userIds = new ArrayList<>();
        userIds.add(author.getId());
        userIds.add(recipient.getId());
        return userIds;
    }

    public static void findAllFromUser(User user, ListObserver<Message> observer) {
        List<String> userIds = new ArrayList<>();
        userIds.add(FirebaseAuth.getInstance().getUid());
        userIds.add(user.getId());

        Query query = getCollectionReference(Message.class)
                .whereArrayContainsAny("userIds", userIds)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        observeAll(Message.class, query, observer);
    }

    public Task<Void> save() {
        return save(id);
    }
}
