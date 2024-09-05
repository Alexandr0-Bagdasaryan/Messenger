package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.adapter.MessagesAdapter;
import com.example.messenger.model.Message;
import com.example.messenger.model.User;
import com.example.messenger.viewModel.ChatViewModel;
import com.example.messenger.viewModel.ChatViewModelFactory;

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

    private ChatViewModel chatViewModel;
    private ChatViewModelFactory chatViewModelFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
        recyclerViewChat.setAdapter(messagesAdapter);
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        chatViewModelFactory = new ChatViewModelFactory(currentUserId,otherUserId);
        chatViewModel= new ViewModelProvider(this,chatViewModelFactory).get(ChatViewModel.class);
        messagesAdapter = new MessagesAdapter(currentUserId);
        recyclerViewChat.setAdapter(messagesAdapter);
        observeViewModels();
        imageViewSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = editTextMessage.getText().toString();
                Message message = new Message(messageText,currentUserId,otherUserId);
                if (!messageText.isEmpty()){
                    chatViewModel.sendMessage(message);
                }
            }
        });
    }


    private void observeViewModels(){
        chatViewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messagesAdapter.setMessages(messages);
            }
        });
        chatViewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                textViewUser.setText(String.format("%s %s",user.getName(),user.getLastName()));
                int resId;
                if (user.isOnline()) {
                    resId = R.drawable.circle_online;
                } else {
                    resId = R.drawable.circle_offline;
                }
                Drawable drawable = ContextCompat.getDrawable(ChatActivity.this, resId);
                userStatus.setBackground(drawable);;
            }
        });
        chatViewModel.getMessageSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean send) {
                if(send){
                    editTextMessage.setText("");
                    Toast.makeText(ChatActivity.this,
                            "Message send successful!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        chatViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                Toast.makeText(ChatActivity.this,
                        error,
                        Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    protected void onResume() {
        super.onResume();
        chatViewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatViewModel.setUserOnline(false);
    }
}