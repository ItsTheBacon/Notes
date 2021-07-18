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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.noteapp.model.NoteModel;
import com.example.noteapp.R;
import com.example.noteapp.adapter.NoteAdapter;
import com.example.noteapp.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.example.noteapp.R.menu.main;
import static com.example.noteapp.ui.form.NoteFragment.BUNDLE_KEY;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private NoteAdapter adapter = new NoteAdapter();
    private ArrayList<NoteModel> list = new ArrayList<>();
    private  boolean linear = true;


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

            NoteModel model = (NoteModel) result.getSerializable(BUNDLE_KEY);
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
        inflater.inflate(main, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
                binding.rvTaskHomeFragment.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                item.setIcon(R.drawable.ic_baseline_dashboard_24);
                linear = false;

            } else {
                binding.rvTaskHomeFragment.setLayoutManager( new LinearLayoutManager(getContext()));
                item.setIcon(R.drawable.ic_list_bulleted_24);
                linear = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}