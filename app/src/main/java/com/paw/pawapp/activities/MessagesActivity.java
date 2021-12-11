package com.paw.pawapp.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.ListenerRegistration;
import com.paw.pawapp.R;
import com.paw.pawapp.adapters.MessagesAdapter;
import com.paw.pawapp.helpers.AlertHelper;
import com.paw.pawapp.models.Match;
import com.paw.pawapp.models.Message;
import com.paw.pawapp.models.utilities.ListObserver;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    public static final String KEY_MATCH = "match";
    private static final String TAG = "MessagesActivity";

    private RecyclerView rvMessages;
    private EditText etMessage;
    private Button btnSendMessage;

    private Match match;
    private List<Message> messages;
    private MessagesAdapter adapter;
    private ListenerRegistration listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        /**
         * Code that changes the Top bar
         */
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#b3003b"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Burgundy));
        }
        /**
         *  Ends here
         */

        rvMessages = findViewById(R.id.rv_messages);
        etMessage = findViewById(R.id.et_message);
        btnSendMessage = findViewById(R.id.btn_send_message);

        match = (Match) getIntent().getSerializableExtra(KEY_MATCH);
        messages = new ArrayList<>();
        adapter = new MessagesAdapter(this, messages);

        rvMessages.setAdapter(adapter);
        rvMessages.setLayoutManager(new LinearLayoutManager(this));

        retrieveMessages();
        setOnClickListener();
    }

    private void retrieveMessages() {
        listener = Message.findAllFromUser(match.getOther(), new ListObserver<Message>() {
            @Override
            public void onUpdate(List<Message> newMessages) {
                Log.d(TAG, String.format("Found %d messages", newMessages.size()));
                // Update the view with messages (this is called whenever the list of messages changes)
                adapter.clear();
                adapter.addAll(newMessages);
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Error while finding messages", e);
                AlertHelper.present(MessagesActivity.this, "Cannot Get Messages", e.getLocalizedMessage());
            }
        });
    }

    private void setOnClickListener() {
        btnSendMessage.setOnClickListener(view -> {
            String content = etMessage.getText().toString();

            btnSendMessage.setEnabled(false);

            sendMessage(content)
                    .addOnCompleteListener(task -> {
                        // Always re-enable text field
                        btnSendMessage.setEnabled(true);
                    })
                    .addOnSuccessListener(unused -> {
                        // Message sent successfully
                        etMessage.setText("");
                    })
                    .addOnFailureListener(e -> {
                        // Message failed to send
                        AlertHelper.present(this, "Cannot Send Message", e.getLocalizedMessage());
                    });
        });
    }

    @SuppressLint("NewApi")
    private Task<Void> sendMessage(String content) {
        Message message = new Message(content, match.getMe(), match.getOther());
        return message.save();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listener != null) {
            listener.remove();
        }
    }
}