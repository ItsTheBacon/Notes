package com.example.noteapp.ui.form;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.noteapp.R;
import com.example.noteapp.databinding.FragmentNoteBinding;
import com.example.noteapp.model.NoteModel;
import com.example.noteapp.utisl.App;

import org.jetbrains.annotations.NotNull;


public class NoteFragment extends Fragment {

    private FragmentNoteBinding binding;
    NoteModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editData();

        clickTxtDoneListener();


    }

    private void clickTxtDoneListener() {

        binding.txtDoneNoteFragment.setOnClickListener(v -> {
            String title = binding.etNoteFragment.getText().toString();
            if (binding.etNoteFragment.getText().toString().trim().equalsIgnoreCase("")) {
                binding.etNoteFragment.setError("Input Title");
            }
            if (model == null) {
                model = new NoteModel(title);
                App.getInstance().noteDao().insertNote(model);
                Log.e("TAG", "clickTxtDoneListener: " + model.getTxtTitle());


            } else {
                model.setTxtTitle(title);
                App.getInstance().noteDao().update(model);
            }
            close();
        });
        binding.backToHomefragment.setOnClickListener(v -> {
            close();
        });
    }

    private void editData() {

        if (getArguments() != null) {
            model = (NoteModel) getArguments().getSerializable("mod");
            if (model != null) {
                binding.etNoteFragment.setText(model.getTxtTitle());
            }
        }
    }
//    void getDate(){
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MMMM:kk");
//        Date currentTime = Calendar.getInstance().getTime();
//        String times = dateFormat.format(currentTime);
//        binding.dateFromNotes.setText(times);
//    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigateUp();

    }
}