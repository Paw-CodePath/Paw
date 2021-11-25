package com.codepath.petnetcodepath.petnet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.petnetcodepath.petnet.R;
import com.parse.ParseFile;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder>{

    private Context context;
    private List<Matches> matches;

    public MatchesAdapter(Context context, List<Matches> matches) {
        this.context = context;
        this.matches = matches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.matches_messages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Matches match = matches.get(position);
        holder.bind(match);
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMatchedUsername;
        private ImageView ivMatchedProfilePicture;
        private TextView tvText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatchedUsername = itemView.findViewById(R.id.tvMatchedUsername);
            ivMatchedProfilePicture = itemView.findViewById(R.id.ivMatchedProfilePicture);
            tvText = itemView.findViewById(R.id.tvText);
        }

        public void bind(Matches matches) {
            //tvText.setText(matches.getClass()); Cannot Set Text at the moment
            tvMatchedUsername.setText(matches.getUser().getUsername());
            ParseFile image = matches.getImage();
            if(image!=null) {
                Glide.with(context).load(image.getUrl()).into(ivMatchedProfilePicture);
            }
        }

        public void clear() {
            matches.clear();
            notifyDataSetChanged();
        }

        public void addAll(List<Matches> matchesList){
            matches.addAll(matchesList);
            notifyDataSetChanged();
        }
    }
}
