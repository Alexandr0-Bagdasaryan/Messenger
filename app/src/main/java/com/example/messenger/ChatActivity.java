package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.adapter.MessagesAdapter;
import com.example.messenger.model.Message;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static String EXTRA_CURRENT_USER_ID = "currentId";
    private static String EXTRA_OTHER_USER_ID = "otherId";


    private TextView textViewUser;
    private View userStatus;
    private RecyclerView recyclerViewChat;
    private EditText editTextMessage;
    private ImageView imageViewSendMessage;

    private MessagesAdapter messagesAdapter;


    private String currentUserId;
    private String otherUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
        recyclerViewChat.setAdapter(messagesAdapter);
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        messagesAdapter = new MessagesAdapter(currentUserId);
        recyclerViewChat.setAdapter(messagesAdapter);
        initMessages();
    }

    private void initViews() {
        textViewUser = findViewById(R.id.textViewUser);
        userStatus = findViewById(R.id.userStatus);
        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
    }


    private void initMessages() {
        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Message message = new Message("Message " + i, otherUserId, currentUserId);
            Message message1 = new Message("Message " + i, currentUserId, otherUserId);
            messages.add(message1);
            messages.add(message);
        }
        messagesAdapter.setMessages(messages);
    }


    public static Intent newIntent(Context context, String currId, String otherId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherId);
        return intent;
    }
}