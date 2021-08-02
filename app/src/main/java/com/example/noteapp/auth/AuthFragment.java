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

import com.example.noteapp.databinding.FragmentAuthBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthFragment extends Fragment {
    private FragmentAuthBinding binding;
    private FirebaseAuth mAuth;

    public AuthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(getLayoutInflater(), container, false);
        mAuth = FirebaseAuth.getInstance();
        getInit();
        return binding.getRoot();
    }

    private void getInit() {
        binding.btnRegister.setOnClickListener(v -> {
            String adress = binding.emailEtAuth.getText().toString().trim();
            String password = binding.emailEtAuth.getText().toString().trim();
            if (TextUtils.isEmpty(adress) && TextUtils.isEmpty(password)) {
                binding.emailEtAuth.setError("error");
                binding.emailEtAuth.setFocusable(true);
                return;
            }
            creatUserWithEmailAndPassword(adress, password);
        });
    }

    public void creatUserWithEmailAndPassword(String adress, String password) {
        mAuth.createUserWithEmailAndPassword(adress, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(requireActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}