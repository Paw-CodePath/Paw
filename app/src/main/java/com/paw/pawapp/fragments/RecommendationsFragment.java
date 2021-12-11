package com.paw.pawapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.paw.pawapp.R;
import com.paw.pawapp.adapters.PetsAdapter;
import com.paw.pawapp.models.Pet;
import com.paw.pawapp.models.Swipe;
import com.paw.pawapp.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendationsFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private SwipeFlingAdapterView swipeView;
    private FloatingActionButton fabSwipeLeft;
    private FloatingActionButton fabSwipeRight;

    private List<Pet> pets;
    private PetsAdapter adapter;

    private User user;

    public RecommendationsFragment() {
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

        swipeView = view.findViewById(R.id.swipe_view);
        fabSwipeLeft = view.findViewById(R.id.fab_swipe_left);
        fabSwipeRight = view.findViewById(R.id.fab_swipe_right);

        pets = new ArrayList<>();
        adapter = new PetsAdapter(getContext(), R.layout.item_pet, pets);

        setOnClickListeners();
        configureSwipeView();
        loadData();
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
                addSwipe(pet, false);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Pet pet = (Pet) dataObject;
                addSwipe(pet, true);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // TODO: Ask for more data or show an empty state screen here.
            }
        });
    }

    private void loadData() {
        User.getCurrentUser()
                .addOnSuccessListener(user -> {
                    this.user = user;
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Cannot load user", e);
                    swipeView.setEnabled(false);
                    Toast.makeText(getContext(), String.format("Cannot load user: %s", e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
                });

        Pet.findUnseen()
                .addOnSuccessListener(foundPets -> {
                    Toast.makeText(getContext(), String.format("Loaded %d pets", foundPets.size()), Toast.LENGTH_SHORT).show();
                    adapter.clear();
                    adapter.addAll((List<Pet>) foundPets);
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Cannot load pets", e);
                    Toast.makeText(getContext(), String.format("Cannot load pets: %s", e.getLocalizedMessage()), Toast.LENGTH_SHORT).show();
                });
    }

    private void addSwipe(Pet pet, boolean isLike) {
        Swipe swipe = new Swipe(user, pet, isLike);
        swipe.save()
                .addOnSuccessListener(unused -> {
                    String direction = isLike ? "right" : "left";
                    Toast.makeText(getActivity(), String.format("Swiped %s on %s!", direction, pet.getName()), Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to record swipe", e);

                    // Add back into list so we can swipe again.
                    adapter.add(swipe.getPet());
                    adapter.notifyDataSetChanged();
                });
    }
}