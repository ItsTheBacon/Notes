package com.example.noteapp.ui.share;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.noteapp.R;
import com.example.noteapp.databinding.FragmentHomeBinding;
import com.example.noteapp.databinding.FragmentShareBinding;

public class ShareFragment extends Fragment {
    private FragmentShareBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShareBinding.inflate(inflater, container, false);




        return binding.getRoot(); }
}