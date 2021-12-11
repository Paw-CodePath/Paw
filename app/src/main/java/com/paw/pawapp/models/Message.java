package com.paw.pawapp.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.paw.pawapp.models.utilities.FirestoreCollectionName;
import com.paw.pawapp.models.utilities.FirestoreModel;
import com.paw.pawapp.models.utilities.ListObserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@FirestoreCollectionName("messages")
@RequiresApi(api = Build.VERSION_CODES.N)
public class Message extends FirestoreModel implements Serializable {
    private String id;
    private String content;
    private User author;
    private User recipient;
    private Date timestamp;

    public Message() {
    }

    public Message(String content, User author, User recipient) {
        this.id = getCollectionReference(Message.class).document().getId();
        this.content = content;
        this.author = author;
        this.recipient = recipient;
        this.timestamp = new Date();
    }

    public static ListenerRegistration findAllFromUser(User user, ListObserver<Message> observer) {
        List<String> userIds = makeUserIdList(FirebaseAuth.getInstance().getUid(), user.getId());

        Query query = getCollectionReference(Message.class)
                .whereEqualTo("userIds", userIds)
                .orderBy("timestamp", Query.Direction.ASCENDING);

        return observeAll(Message.class, query, observer);
    }

    private static List<String> makeUserIdList(String id1, String id2) {
        List<String> userIds = new ArrayList<>();
        userIds.add(id1);
        userIds.add(id2);
        return userIds.stream().sorted().collect(Collectors.toList());
    }

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
        return makeUserIdList(author.getId(), recipient.getId());
    }

    public Task<Void> save() {
        return save(id);
    }
}
