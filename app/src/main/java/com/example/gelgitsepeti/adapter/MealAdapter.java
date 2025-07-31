package com.example.gelgitsepeti.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gelgitsepeti.databinding.ItemMealBinding;
import com.example.gelgitsepeti.model.Meal;

import java.util.List;

// adapter/MealAdapter.java
public class MealAdapter extends RecyclerView.Adapter<MealAdapter.VH> {
    private final List<Meal> list;
    public MealAdapter(List<Meal> list) { this.list = list; }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int i) {
        ItemMealBinding b = ItemMealBinding.inflate(
                LayoutInflater.from(p.getContext()), p, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Meal m = list.get(pos);
        h.b.ivMealImage.setImageResource(m.getImageRes());
        h.b.tvMealName.setText(m.getName());
        h.b.tvMealPrice.setText(m.getPrice());
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        final ItemMealBinding b;
        VH(ItemMealBinding b) {
            super(b.getRoot());
            this.b = b;
        }
    }
}

