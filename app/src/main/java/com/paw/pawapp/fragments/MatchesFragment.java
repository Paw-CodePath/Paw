package com.paw.pawapp.fragments;

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

import com.google.firebase.firestore.ListenerRegistration;
import com.paw.pawapp.R;
import com.paw.pawapp.adapters.MatchesAdapter;
import com.paw.pawapp.models.Match;
import com.paw.pawapp.models.utilities.ListObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment {

    private static final String TAG = "MatchesFragment";

    private RecyclerView rvMatches;

    private List<Match> matches;
    private MatchesAdapter adapter;
    ListenerRegistration listener;

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

        listener = Match.findAll(new ListObserver<Match>() {
            @Override
            public void onUpdate(List<Match> newMatches) {
                Log.d(TAG, String.format("Found %d matches", newMatches.size()));
                adapter.clear();
                adapter.addAll(newMatches);
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Error while finding matches", e);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (listener != null) {
            listener.remove();
        }
    }
}