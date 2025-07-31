package com.example.gelgitsepeti.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gelgitsepeti.databinding.ItemCouponBinding;
import com.example.gelgitsepeti.model.Coupon;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.CouponViewHolder> {

    private final List<Coupon> coupons;

    public CouponAdapter(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public int getItemCount() {
        // Eğer liste boşsa 0, değilse çok büyük bir sayı dön
        return coupons.isEmpty() ? 0 : Integer.MAX_VALUE;
    }

    @NonNull @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCouponBinding b = ItemCouponBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new CouponViewHolder(b);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        // position’u liste uzunluğuna göre modulo’layıp gerçek veriyi al
        Coupon c = coupons.get(position % coupons.size());
        holder.bind(c);
    }

    static class CouponViewHolder extends RecyclerView.ViewHolder {
        private final ItemCouponBinding b;
        public CouponViewHolder(@NonNull ItemCouponBinding binding) {
            super(binding.getRoot());
            this.b = binding;
        }
        void bind(Coupon c) {
            b.ivCouponImage.setImageResource(c.getImageResId());
            b.tvCouponTitle.setText(c.getTitle());
            b.tvCouponCode.setText(c.getCode());
        }
    }
}
