package com.paw.pawapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.paw.pawapp.R;
import com.paw.pawapp.activities.MessagesActivity;
import com.paw.pawapp.models.Match;
import com.paw.pawapp.models.Message;
import com.paw.pawapp.models.User;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private final Context context;
    private final List<Message> messages;

    public MessagesAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvTimestamp;
        TextView tvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_message_author);
            tvTimestamp = itemView.findViewById(R.id.tv_message_timestamp);
            tvContent = itemView.findViewById(R.id.tv_message_content);
        }

        public void bind(Message message) {
            tvName.setText(message.getAuthor().getName());
            tvTimestamp.setText(message.getTimestamp().toString());
            tvContent.setText(message.getContent());
        }
    }

    public void clear() {
        messages.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Message> newMessages) {
        messages.addAll(newMessages);
        notifyDataSetChanged();
    }
}
