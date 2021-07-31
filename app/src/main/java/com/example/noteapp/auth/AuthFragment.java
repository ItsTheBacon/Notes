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
import com.example.noteapp.databinding.FragmentAuthBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class AuthFragment extends Fragment {

    FirebaseAuth mAuth;
    private FragmentAuthBinding binding;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(getLayoutInflater(), container, false);

        mAuth = FirebaseAuth.getInstance();
        getInit();
//        user_haveaccount();
        return binding.getRoot();
    }

    //    private void user_haveaccount() {
//        binding.loginAdress.setOnClickListener(v -> {
//            switch (v.getId()){
//                case R.id.login_adress:
//                    navController.navigate(R.id.signFragment);
//                    break;
//
//            }
//        });
//    }
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

    public void creatUserWithEmailAndPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.e("TAG", "AuthFragmentcreatUserWithEmailAndPassword:success");
                            NavController navController = Navigation.findNavController(AuthFragment.this.requireActivity(), R.id.nav_host_fragment_content_main);
                            navController.navigate(R.id.action_authFragment_to_signFragment);
                            Toast.makeText(AuthFragment.this.requireActivity(), "Authentication isSuccessful.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("TAG", "AuthFragmentcreatUserWithEmailAndPassword:failure", task.getException());
                            Toast.makeText(AuthFragment.this.requireActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
//        FirebaseUser user = mAuth.getCurrentUser()
    }
}