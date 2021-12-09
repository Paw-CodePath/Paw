package com.codepath.petnetcodepath.petnet.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.petnetcodepath.petnet.R;
import com.codepath.petnetcodepath.petnet.models.Match;
import com.codepath.petnetcodepath.petnet.models.Message;
import com.codepath.petnetcodepath.petnet.models.utilities.ListObserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MessagesActivity extends AppCompatActivity {

    public static final String KEY_MATCH = "match";

    private Match match;
    private List<Message> allMessages;

    private Button btnSend;
    private RecyclerView rvChat;
    public TextView message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        allMessages = new ArrayList<>();
        match = (Match) getIntent().getSerializableExtra(KEY_MATCH);

        btnSend = findViewById(R.id.btnSend);
        rvChat = findViewById(R.id.rvChat);
        message = findViewById(R.id.message);

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

        btnSend.setOnClickListener(view -> {
            sendMessage(message.getText().toString());
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