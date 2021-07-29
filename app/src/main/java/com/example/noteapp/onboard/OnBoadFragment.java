package com.example.noteapp.onboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.noteapp.R;
import com.example.noteapp.databinding.FragmentOnBoadBinding;
import com.example.noteapp.utisl.PreferncesHelper;

import org.jetbrains.annotations.NotNull;


public class OnBoadFragment extends Fragment {
    private FragmentOnBoadBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOnBoadBinding.inflate(getLayoutInflater(), container, false);
        init_viewpager_adapter();
        viewpager_tr_onboard();
        setClickListener();

        return binding.getRoot();
    }

    private void viewpager_tr_onboard() {
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        binding.start.setVisibility(View.GONE);
                        binding.send.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        binding.start.setVisibility(View.GONE);
                        binding.send.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        binding.start.setVisibility(View.VISIBLE);
                        binding.send.setVisibility(View.GONE);
                        break;
                }
                super.onPageSelected(position);
            }

        });
    }

    private void init_viewpager_adapter() {
        if (binding.viewPager != null) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());
            binding.viewPager.setAdapter(adapter);
            binding.wormDotsIndicator.setViewPager2(binding.viewPager);
        }

    }
    private void setClickListener() {
        binding.send.setOnClickListener(v -> {
            close();
        });
        binding.start.setOnClickListener(v -> {
            close(); 
        });
    }

    private void close() {
        PreferncesHelper sharedPref = new PreferncesHelper();
        sharedPref.init(requireContext());
        sharedPref.OnSaveOnboardState();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigateUp();
    }

    class ViewPagerAdapter extends FragmentStateAdapter {
        private int COUNT = 3;

        public ViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NotNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new FirstFragment();
                    break;
                case 1:
                    fragment = new SecondFragment();
                    break;
                case 2:
                    fragment = new ThreeFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getItemCount() {
            return COUNT;
        }
    }

}