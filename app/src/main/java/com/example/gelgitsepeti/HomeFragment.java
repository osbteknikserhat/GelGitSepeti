package com.example.gelgitsepeti;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gelgitsepeti.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NavController navController;

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}