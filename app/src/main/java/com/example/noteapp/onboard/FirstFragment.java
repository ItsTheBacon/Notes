package com.example.noteapp.onboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.noteapp.R;
import com.example.noteapp.databinding.FragmentHomeBinding;
import com.example.noteapp.databinding.FragmentOnboardFirstBinding;
import com.example.noteapp.databinding.FragmentThreeBinding;
import com.example.noteapp.utisl.PreferncesHelper;

public class FirstFragment extends Fragment {
    private FragmentOnboardFirstBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOnboardFirstBinding.inflate(inflater, container, false);
        setClickListener();
        return binding.getRoot();
    }

    private void setClickListener() {
        binding.send.setOnClickListener(v -> {
            PreferncesHelper sharedPref = new PreferncesHelper();
            sharedPref.init(requireContext());
            sharedPref.OnSaveOnboardState();
            close();
        });
    }

        private void close() {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_onBoadFragment_to_nav_home);
        }
}