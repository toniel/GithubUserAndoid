package com.toniel.githubuser.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.toniel.githubuser.SettingPreferences;
import com.toniel.githubuser.databinding.ActivitySettingBinding;
import com.toniel.githubuser.viewmodel.SettingViewModel;
import com.toniel.githubuser.viewmodel.ViewModelFactory;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getSupportActionBar()!=null){
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Setting");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//        RxDataStore<Preferences> dataStore = new RxPreferenceDataStoreBuilder(this,"settings").build();
//        SettingPreferences preferences = SettingPreferences.getInstance(dataStore);

//        SettingViewModel settingViewModel = new ViewModelProvider(this,new ViewModelFactory(preferences)).get(SettingViewModel.class);
        SettingViewModel settingViewModel = obtainViewModel(SettingActivity.this);
        settingViewModel.getThemeSettings().observe(this,isDarkModeActive->{
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                binding.switchTheme.setChecked(true);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                binding.switchTheme.setChecked(false);
            }
        });




        binding.switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
           settingViewModel.saveThemeSetting(isChecked);
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static SettingViewModel obtainViewModel(AppCompatActivity activity){
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity,factory).get(SettingViewModel.class);
    }
}