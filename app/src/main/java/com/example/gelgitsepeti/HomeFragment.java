package com.example.gelgitsepeti;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gelgitsepeti.adapter.CategoryAdapter;
import com.example.gelgitsepeti.adapter.CouponAdapter;
import com.example.gelgitsepeti.databinding.FragmentHomeBinding;
import com.example.gelgitsepeti.model.Category;
import com.example.gelgitsepeti.model.Coupon;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NavController navController;
    private PagerSnapHelper snapHelper;
    private Handler autoScrollHandler;
    private Runnable autoScrollRunnable;
    private int currentPosition;
    private static final long AUTO_SCROLL_INTERVAL = 2000L; // 2 saniye

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        // Profil ikonu
        binding.topNavBar.ivProfile.setOnClickListener(v ->
                navController.navigate(R.id.action_homeFragment_to_profileFragment)
        );

        // Sipariş Geçmişi
        binding.topNavBar.ivOrderHistory.setOnClickListener(v ->
                navController.navigate(R.id.action_homeFragment_to_historyFragment)
        );

        // Favoriler
        binding.topNavBar.ivFavorites.setOnClickListener(v ->
                navController.navigate(R.id.action_homeFragment_to_favoritesFragment)
        );

        // Sepet
        binding.topNavBar.ivCart.setOnClickListener(v ->
                navController.navigate(R.id.action_homeFragment_to_cartFragment)
        );

        // Kupon listesi hazırlığı
        List<Coupon> couponList = loadDummyCoupons();
        int realCount = couponList.size();

        CouponAdapter adapter = new CouponAdapter(couponList);
        LinearLayoutManager lm = new LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        binding.rvCoupons.setLayoutManager(lm);
        binding.rvCoupons.setAdapter(adapter);

        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvCoupons);

        // 1) Sonsuz döngü için orta pozisyona taşı
        int mid = Integer.MAX_VALUE / 2;
        int startPos = mid - (mid % realCount);
        currentPosition = startPos;
        binding.rvCoupons.scrollToPosition(currentPosition);

        // 2) Indicator’ı başlat
        binding.llIndicator.removeAllViews();
        initIndicator(realCount);

        // 3) Scroll listener sadece indicator güncellemesi için
        binding.rvCoupons.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override public void onScrollStateChanged(@NonNull RecyclerView rv, int newState) {
                super.onScrollStateChanged(rv, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(lm);
                    int pos = lm.getPosition(centerView);
                    int index = pos % realCount;
                    updateIndicator(index);
                    currentPosition = pos;
                }
            }
        });

        // 4) AUTO SCROLL başlat
        autoScrollHandler = new Handler(Looper.getMainLooper());
        autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                // bir sonraki pozisyona kaydır
                currentPosition++;
                binding.rvCoupons.smoothScrollToPosition(currentPosition);
                // tekrar planla
                autoScrollHandler.postDelayed(this, AUTO_SCROLL_INTERVAL);
            }
        };
        autoScrollHandler.postDelayed(autoScrollRunnable, AUTO_SCROLL_INTERVAL);

        // --- 5. Yemek kategorileri setup ---
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Tatlı",    R.drawable.img_tatli));
        categories.add(new Category("Pizza",    R.drawable.img_pizza));
        categories.add(new Category("Döner",    R.drawable.img_doner));
        categories.add(new Category("Salata",   R.drawable.img_salata));
// … istediğin diğer kategoriler …

        CategoryAdapter catAdapter = new CategoryAdapter(categories, category -> {
            // kategori tıklanınca yapılacak (örn. filtre uygula)
        });
        LinearLayoutManager catLm = new LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        binding.rvCategories.setLayoutManager(catLm);
        binding.rvCategories.setAdapter(catAdapter);

    }

    private List<Coupon> loadDummyCoupons() {
        // Gerçek verinizi buradan çekin. Örnek:
        List<Coupon> list = new ArrayList<>();
        list.add(new Coupon("Sıcaklara Özel Kahve İndirimi", "SB25", R.drawable.img_kahve));
        list.add(new Coupon("Öğrenci İndirimi",   "OGRENCI100",   R.drawable.img_ogrenci));
        list.add(new Coupon("Hafta Sonu Fırsatı","HAFTA200",   R.drawable.img_ogretmen));
        return list;
    }

    private void initIndicator(int count) {
        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(requireContext());
            dot.setImageResource(i == 0
                    ? R.drawable.dot_active
                    : R.drawable.dot_inactive);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            lp.setMargins(4, 0, 4, 0);
            dot.setLayoutParams(lp);

            binding.llIndicator.addView(dot);
        }
    }

    private void updateIndicator(int position) {
        for (int i = 0; i < binding.llIndicator.getChildCount(); i++) {
            ImageView dot = (ImageView) binding.llIndicator.getChildAt(i);
            dot.setImageResource(i == position
                    ? R.drawable.dot_active
                    : R.drawable.dot_inactive);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // AUTO SCROLL iptal et
        if (autoScrollHandler != null && autoScrollRunnable != null) {
            autoScrollHandler.removeCallbacks(autoScrollRunnable);
        }
        binding = null;
    }
}