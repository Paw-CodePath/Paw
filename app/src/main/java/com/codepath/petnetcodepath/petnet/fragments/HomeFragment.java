package com.codepath.petnetcodepath.petnet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.codepath.petnetcodepath.petnet.R;
import com.codepath.petnetcodepath.petnet.adapters.PetsAdapter;
import com.codepath.petnetcodepath.petnet.models.Pet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    SwipeFlingAdapterView swipeView;

    List<Pet> pets;
    PetsAdapter adapter;

    FloatingActionButton fabSwipeLeft;
    FloatingActionButton fabSwipeRight;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeView = view.findViewById(R.id.swipeView);
        fabSwipeLeft = view.findViewById(R.id.fabSwipeLeft);
        fabSwipeRight = view.findViewById(R.id.fabSwipeRight);

        pets = new ArrayList<>();
        adapter = new PetsAdapter(getContext(), R.layout.item_pet, pets);

        setOnClickListeners();
        configureSwipeView();
        queryPets();
    }

    private void setOnClickListeners() {
        fabSwipeLeft.setOnClickListener(l -> {
            swipeView.getTopCardListener().selectLeft();
        });
        fabSwipeRight.setOnClickListener(l -> {
            swipeView.getTopCardListener().selectRight();
        });
    }

    private void configureSwipeView() {
        swipeView.setAdapter(adapter);
        swipeView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                pets.remove(0);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(float v) {
                // Required method
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Pet pet = (Pet) dataObject;
                Toast.makeText(getActivity(), String.format("Swiped left on %s!", pet.getName()), Toast.LENGTH_SHORT);
                // TODO: Record this as a rejection.
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Pet pet = (Pet) dataObject;
                Toast.makeText(getActivity(), String.format("Swiped right on %s!", pet.getName()), Toast.LENGTH_SHORT);
                // TODO: Record this as a match.
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // TODO: Ask for more data or show an empty state screen here.
            }
        });
    }

    private void queryPets() {
        Pet.queryPets((pets, e) -> {
            if (e == null) {
                Toast.makeText(getContext(), String.format("Loaded %d pets", pets.size()), Toast.LENGTH_SHORT).show();
                adapter.clear();
                adapter.addAll(pets);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), String.format("Cannot load pets: %s", e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}