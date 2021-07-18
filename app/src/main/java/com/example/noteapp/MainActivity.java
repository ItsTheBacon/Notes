package com.example.noteapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.noteapp.databinding.ActivityMainBinding;
import com.example.noteapp.utisl.PreferncesHelper;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 20;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Uri imageUri;
    private SharedPreferences sharedPreferences;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        onBoardingPrefence(navController);
        toolbarandfabvisiblyGone(navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        clickFabListner(navController);
        click_image_gallerty();
        saveToGallery();
        updateImage();
    }

    private void onBoardingPrefence(NavController navController) {
        PreferncesHelper preferncesHelper = new PreferncesHelper();
        preferncesHelper.init(this);
        if (!preferncesHelper.isShown()) {
            navController.navigate(R.id.onBoadFragment);
        }
    }

    private void toolbarandfabvisiblyGone(NavController navController) {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.noteFragment || destination.getId() == R.id.onBoadFragment) {
                binding.appBarMain.toolbar.setVisibility(View.GONE);
                binding.appBarMain.fab.setVisibility(View.GONE);
            } else {
                binding.appBarMain.toolbar.setVisibility(View.VISIBLE);
                binding.appBarMain.fab.setVisibility(View.VISIBLE);
            }
        });
    }

    private void saveToGallery() {
        sharedPreferences = getSharedPreferences("profilePicture", MODE_PRIVATE);
        if (!sharedPreferences.getString("dp", "").equals("")) {
            byte[] decodedString = Base64.decode(sharedPreferences.getString("dp", ""), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            image.setImageBitmap(decodedByte);
        }
    }

    private void click_image_gallerty() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        image = view.findViewById(R.id.din_image_gallery);
        image.setOnClickListener(v -> {
            Intent gallery = new Intent("android.intent.action.GET_CONTENT");
            gallery.addCategory("android.intent.category.OPENABLE");
            gallery.setType("image/*");
            MainActivity.this.startActivityForResult(gallery, 20);
        });
        image.setOnLongClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(image.getContext()).create();
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Нет", (dialog1, which) -> {

            });
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Да", (dialog12, which) -> Glide.with(MainActivity.this)
                    .load(imageUri)
                    .circleCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(image));
            dialog.show();
            return false;
        });
    }

    public void updateImage() {
        if (image.getImageAlpha() != R.drawable.placeholder) {
            Glide.with(MainActivity.this)
                    .load(image.getDrawable())
                    .circleCrop()
                    .into(image);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        imageUri = data.getData();

                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();
                        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                        sharedPreferences.edit().putString("dp", encodedImage).commit();
                        Glide.with(this)
                                .load(imageUri)
                                .circleCrop()
                                .into(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void clickFabListner(NavController navController) {
        binding.appBarMain.fab.setOnClickListener(v -> navController.navigate(R.id.action_nav_home_to_noteFragment));

    }
}