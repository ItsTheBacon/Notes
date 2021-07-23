package com.example.noteapp.onboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.noteapp.databinding.FragmentOnBoadBinding;

import org.jetbrains.annotations.NotNull;


public class OnBoadFragment extends Fragment {
    private FragmentOnBoadBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOnBoadBinding.inflate(getLayoutInflater(), container, false);
        init_viewpager_adapter();
        return binding.getRoot();
    }

    private void init_viewpager_adapter() {
        if (binding.viewPager != null) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());
            binding.viewPager.setAdapter(adapter);
            binding.wormDotsIndicator.setViewPager2(binding.viewPager);
        }

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