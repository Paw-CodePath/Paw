package com.codepath.petnetcodepath.petnet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.petnetcodepath.petnet.adapters.Matches;
import com.codepath.petnetcodepath.petnet.adapters.MatchesAdapter;
import com.codepath.petnetcodepath.petnet.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    public static final String TAG = "MessagesFragment";
    private RecyclerView rvMatches;
    protected MatchesAdapter adapter;
    protected List<Matches> allMatches;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Getting recycler view contents
        rvMatches = view.findViewById(R.id.rvMatches);
        allMatches = new ArrayList<>();
        adapter = new MatchesAdapter(getContext(), allMatches);


        /**
         * Implement Refresh listener here
         */

        rvMatches.setAdapter(adapter);
        rvMatches.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }

    protected void queryPosts() {
        ParseQuery<Matches> query = ParseQuery.getQuery(Matches.class);
        query.include(Matches.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Matches.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Matches>() {
            @Override
            public void done(List<Matches> matches, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Issue with getting matches", e);
                    return;
                }
                for(Matches match : matches) {
                    Log.i(TAG, "Matches: " + match.getText() + ", username: " + match.getUser().getUsername());
                }
                allMatches.addAll(matches);
                adapter.notifyDataSetChanged();
            }
        });

    }
}