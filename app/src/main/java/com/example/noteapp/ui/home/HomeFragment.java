package com.example.noteapp.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.noteapp.Model.NoteModel;
import com.example.noteapp.R;
import com.example.noteapp.adapter.NoteAdapter;
import com.example.noteapp.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    public static boolean linear = true;
    private FragmentHomeBinding binding;

    private NoteAdapter adapter;
    private ArrayList<NoteModel> list = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setupRecycler();
        getData();
        et_search_title();


        return binding.getRoot();
    }


    private void getData() {

        getParentFragmentManager().setFragmentResultListener("title", getViewLifecycleOwner(), (requestKey, result) -> {

            NoteModel model = (NoteModel) result.getSerializable("mod");
            list.add(model);
            adapter.addText(model);
        });
    }

    private void et_search_title() {
        binding.etSearchTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void filter(String text) {
        ArrayList<NoteModel> filteredList = new ArrayList<>();
        for (NoteModel item : list) {
            if (item.getTxtTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);

    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter(linear, HomeFragment.this);
    }

    private void setupRecycler() {
        if (!linear) {
            binding.rvTaskHomeFragment.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        } else {
            binding.rvTaskHomeFragment.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        binding.rvTaskHomeFragment.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_dahsboard) {
            if (linear) {
                item.setIcon(R.drawable.ic_baseline_dashboard_24);
                binding.rvTaskHomeFragment.setLayoutManager(new LinearLayoutManager(getContext()));
                return true;
            } else {
                item.setIcon(R.drawable.ic_list_bulleted_24);
                binding.rvTaskHomeFragment.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}