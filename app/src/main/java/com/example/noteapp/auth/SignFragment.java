package com.example.noteapp.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.noteapp.R;
import com.example.noteapp.databinding.FragmentSignBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignFragment extends Fragment {

    FirebaseAuth mAuth;
    private FragmentSignBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignBinding.inflate(getLayoutInflater(), container, false);
        mAuth = FirebaseAuth.getInstance();
        setInit();
        return binding.getRoot();
    }

    private void setInit() {
        binding.buttonSign.setOnClickListener(v -> {
            String adress = binding.emailEtAuth.getText().toString().trim();
            String password = binding.emailEtAuth.getText().toString().trim();
            if (TextUtils.isEmpty(adress) && TextUtils.isEmpty(password)) {
                binding.emailEtAuth.setError("error");
                binding.emailEtAuth.setFocusable(true);
                return;
            }
            signInWithEmailAndPassword(adress, password);
        });

    }

    private void signInWithEmailAndPassword(String adress, String password) {
        mAuth.signInWithEmailAndPassword(adress, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.e("TAG", "SignFragmentsignInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(requireActivity(), "Sign in is SUCCESS.",
                                Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                        navController.navigate(R.id.nav_home);
                    } else {
                        Log.e("TAG", "SignFragmentsignInWithEmail:failure", task.getException());
                        Toast.makeText(requireActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
}