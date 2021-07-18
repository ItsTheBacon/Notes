package com.example.noteapp.ui.form;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.noteapp.R;
import com.example.noteapp.databinding.FragmentNoteBinding;
import com.example.noteapp.model.NoteModel;


public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;
    private NoteModel model;
    public static final String BUNDLE_KEY = "mod";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater, container, false);
        clickTxtDoneListener();
        return binding.getRoot();
    }

    private void clickTxtDoneListener() {

        binding.txtDoneNoteFragment.setOnClickListener(v -> {
            if (binding.etNoteFragment.getText().toString().trim().equalsIgnoreCase("")) {
                binding.etNoteFragment.setError("Input Title");
            } else {
                String title = binding.etNoteFragment.getText().toString();
                model = new NoteModel(title);
                Bundle bundle = new Bundle();
                bundle.putSerializable(BUNDLE_KEY, model);
                getParentFragmentManager().setFragmentResult("title", bundle);
                close();
            }
        });

    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigateUp();

    }
}