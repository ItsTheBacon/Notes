package com.example.noteapp.utisl;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferncesHelper {
    private SharedPreferences sharedPreferences = null;


    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);

    }

    public void OnSaveOnboardState() {
        sharedPreferences.edit().putBoolean("IsShown", true).apply();


    }

    public boolean isShown() {
        return sharedPreferences.getBoolean("IsShown", false);
    }

}
