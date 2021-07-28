package com.example.noteapp.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.noteapp.R;
import com.example.noteapp.adapter.NoteAdapter;
import com.example.noteapp.databinding.FragmentHomeBinding;
import com.example.noteapp.model.NoteModel;
import com.example.noteapp.utisl.App;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.example.noteapp.R.menu.main;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding  binding;
    private NoteAdapter adapter = new NoteAdapter();
    private List<NoteModel> list = new ArrayList<>();
    private boolean linear = true;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecycler();
        et_search_title();
        getNotesFromDb();
        push();
    }


    private void getNotesFromDb() {
        getParentFragmentManager().setFragmentResultListener("notes", getViewLifecycleOwner(), (requestKey, result) -> {
            NoteModel model = (NoteModel) result.get("mod");
            if (model != null) {
                adapter.addText(model, 0);
            }
        });
        App.getInstance().noteDao().getAll().observe(requireActivity(), new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(List<NoteModel> noteModels) {
                adapter.setlist(noteModels, 0);
                list = noteModels;
            }
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    public void setupRecycler() {
        if (!linear) {
            binding.rvTaskHomeFragment.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        } else {
            binding.rvTaskHomeFragment.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        binding.rvTaskHomeFragment.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                App.getInstance().noteDao().delete(list.get(viewHolder.getAdapterPosition()));
                adapter.delete(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(binding.rvTaskHomeFragment);
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


    public void push() {
        adapter.setItemClickList((position, model) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("pos", position);
            bundle.putSerializable("mod", model);
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_nav_home_to_noteFragment, bundle);
            Log.e("TAG", "CLickItem: " + bundle);

        });
    }
}