package com.paw.pawapp.adapters;

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
import com.paw.pawapp.R;
import com.paw.pawapp.models.Pet;

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
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvDescription = view.findViewById(R.id.tv_description);
        ImageView ivImage = view.findViewById(R.id.iv_image);

        tvName.setText(pet.getName());
        tvDescription.setText(pet.getDescription());
        Glide.with(context).load(pet.getImageUrl()).into(ivImage);

        return view;
    }
}