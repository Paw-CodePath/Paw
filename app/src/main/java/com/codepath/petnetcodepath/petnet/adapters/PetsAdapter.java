package com.codepath.petnetcodepath.petnet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.codepath.petnetcodepath.petnet.R;
import com.codepath.petnetcodepath.petnet.models.Pet;

import java.util.List;

public class PetsAdapter extends ArrayAdapter<Pet> {
    public PetsAdapter(@NonNull Context context, int resource, @NonNull List<Pet> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = getContext();
        Pet pet = getItem(position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_pet, parent, false);
        TextView tvPetName = view.findViewById(R.id.tvPetName);
        TextView tvPetDescription = view.findViewById(R.id.tvPetDescription);
        ImageView ivPetImage = view.findViewById(R.id.ivPetImage);

        tvPetName.setText(pet.getName());
        tvPetDescription.setText(pet.getDescription());
        Glide.with(context).load(pet.getImage().getUrl()).into(ivPetImage);

        return view;
    }
}
