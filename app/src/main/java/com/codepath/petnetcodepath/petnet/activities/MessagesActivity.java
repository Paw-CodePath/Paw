package com.codepath.petnetcodepath.petnet.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.petnetcodepath.petnet.R;
import com.codepath.petnetcodepath.petnet.models.Match;
import com.codepath.petnetcodepath.petnet.models.Message;
import com.codepath.petnetcodepath.petnet.models.utilities.ListObserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    public static final String KEY_MATCH = "match";

    private Match match;
    private List<Message> allMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        allMessages = new ArrayList<>();
        match = (Match) getIntent().getSerializableExtra(KEY_MATCH);

        Message.findAllFromUser(match.getOther(), new ListObserver<Message>() {
            @Override
            public void onUpdate(List<Message> messages) {
                // Update the view with messages (this is called whenever the list of messages changes)
                allMessages.clear();
                allMessages.addAll(messages);
            }

            @Override
            public void onError(Exception e) {
                // Handle the error
            }
        });
    }

    private void sendMessage(String content) {
        Message message = new Message();
        message.setContent(content);
        message.setAuthor(match.getMe());
        message.setRecipient(match.getOther());
        message.setTimestamp(new Date());

        message.save()
                .addOnSuccessListener(unused -> {
                    // Message sent successfully
                })
                .addOnFailureListener(e -> {
                    // Message failed to send
                });
    }
}