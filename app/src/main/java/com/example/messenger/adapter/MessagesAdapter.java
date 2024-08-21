package com.example.messenger.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.R;
import com.example.messenger.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {


    private static final int VIEW_TYPE_MY_MESSAGE = 100;
    private static final int VIEW_TYPE_OTHER_MESSAGE = 101;

    private String currentUserId;

    public MessagesAdapter(String currentUserID) {
        this.currentUserId = currentUserID;
    }

    private List<Message> messages = new ArrayList<>();

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutResId;
        if (viewType == VIEW_TYPE_MY_MESSAGE){
            layoutResId=R.layout.my_message_item;
        }
        else {
            layoutResId=R.layout.other_message_item;
        }
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(layoutResId,parent,false);
        return new MessagesViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getSenderId().equals(currentUserId)){
            return VIEW_TYPE_MY_MESSAGE;
        }
        else {
            return VIEW_TYPE_OTHER_MESSAGE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.textViewMassage.setText(message.getTextMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessagesViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewMassage;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMassage=itemView.findViewById(R.id.textViewMassage);
        }
    }
}
