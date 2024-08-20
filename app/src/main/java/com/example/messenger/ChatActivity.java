package com.example.messenger;

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

public class ChatActivity extends AppCompatActivity {


    private TextView textViewUser;
    private View userStatus;
    private RecyclerView recyclerViewChat;
    private EditText editTextMessage;
    private ImageView imageViewSendMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
    }

    private void initViews(){
         textViewUser=findViewById(R.id.textViewUser);
         userStatus=findViewById(R.id.userStatus);
         recyclerViewChat=findViewById(R.id.recyclerViewChat);
         editTextMessage=findViewById(R.id.editTextMessage);
         imageViewSendMessage=findViewById(R.id.imageViewSendMessage);
    }
}