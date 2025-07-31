package com.example.gelgitsepeti.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gelgitsepeti.databinding.ItemCategoryBinding;
import com.example.gelgitsepeti.model.Category;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VH> {

    private final List<Category> categories;
    private final OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(Category category);
    }

    public CategoryAdapter(List<Category> categories, OnClickListener listener) {
        this.categories = categories;
        this.onClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding b = ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
        );
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Category c = categories.get(position);
        holder.binding.ivCategoryImage.setImageResource(c.getImageResId());
        holder.binding.tvCategoryName.setText(c.getName());
        holder.binding.getRoot().setOnClickListener(v ->
                onClickListener.onClick(c)
        );
    }

    static class VH extends RecyclerView.ViewHolder {
        final ItemCategoryBinding binding;
        VH(@NonNull ItemCategoryBinding b) {
            super(b.getRoot());
            this.binding = b;
        }
    }
}
