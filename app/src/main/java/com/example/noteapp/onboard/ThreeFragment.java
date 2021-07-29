package com.example.noteapp.onboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.noteapp.R;
import com.example.noteapp.databinding.FragmentThreeBinding;
import com.example.noteapp.utisl.PreferncesHelper;


public class ThreeFragment extends Fragment {
    private FragmentThreeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThreeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

}