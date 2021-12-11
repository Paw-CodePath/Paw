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
import com.paw.pawapp.models.User;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {

    private final Context context;
    private final List<Match> matches;

    public MatchesAdapter(Context context, List<Match> matches) {
        this.context = context;
        this.matches = matches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_match, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Match match = matches.get(position);
        holder.bind(match);
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlContainer;
        TextView tvName;
        TextView tvLastText;
        ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlContainer = itemView.findViewById(R.id.rl_item_match);
            tvName = itemView.findViewById(R.id.tv_name);
            tvLastText = itemView.findViewById(R.id.tv_last_text);
            ivImage = itemView.findViewById(R.id.iv_image);
        }

        public void bind(Match match) {
            User other = match.getOther();
            tvName.setText(other.getName());
            // TODO: Set the last text here.
            Glide.with(context).load(other.getImageUrl()).into(ivImage);

            rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, MessagesActivity.class);
                    i.putExtra(MessagesActivity.KEY_MATCH, match);
                    context.startActivity(i);
                }
            });
        }
    }

    public void clear() {
        matches.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Match> matchesList) {
        matches.addAll(matchesList);
        notifyDataSetChanged();
    }
}
