package com.codepath.petnetcodepath.petnet.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.petnetcodepath.petnet.R;
import com.codepath.petnetcodepath.petnet.adapters.MatchesAdapter;
import com.codepath.petnetcodepath.petnet.models.Match;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment {

    private static final String TAG = "MessagesFragment";

    private RecyclerView rvMatches;

    private List<Match> matches;
    private MatchesAdapter adapter;

    public MatchesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matches, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMatches = view.findViewById(R.id.rv_matches);

        matches = new ArrayList<>();
        adapter = new MatchesAdapter(getContext(), matches);

        rvMatches.setAdapter(adapter);
        rvMatches.setLayoutManager(new LinearLayoutManager(getContext()));

        Match.findAllForCurrentUser()
                .addOnSuccessListener(newMatches -> {
                    Log.d(TAG, String.format("Found %d matches", newMatches.size()));
                    adapter.clear();
                    adapter.addAll(newMatches);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error while finding matches", e);
                });

    }
}