package com.toniel.githubuser.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.toniel.githubuser.R;
import com.toniel.githubuser.SettingPreferences;
import com.toniel.githubuser.adapter.UserAdapter;
import com.toniel.githubuser.databinding.ActivityMainBinding;
import com.toniel.githubuser.model.User;
import com.toniel.githubuser.viewmodel.MainViewModel;
import com.toniel.githubuser.viewmodel.SettingViewModel;
import com.toniel.githubuser.viewmodel.ViewModelFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        viewModel.isLoading().observe(this,aBoolean -> showLoading(aBoolean));

        viewModel.getUsers().observe(this,users -> setUserData(users));

        viewModel.getToastMessage().observe(this,message->{
            Snackbar.make(MainActivity.this,binding.getRoot(),message,Snackbar.LENGTH_SHORT).show();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvUser.setLayoutManager(layoutManager);
        SettingViewModel settingViewModel = obtainViewModel(MainActivity.this);
        settingViewModel.getThemeSettings().observe(this,isDarkModeActive->{
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_option,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager!=null){
            SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                    viewModel.getGithubUser(s);
                    return true;

                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.menu_settings){
            Intent intent = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);
        }else if(item.getItemId()==R.id.menu_favourite){
            Intent intent = new Intent(MainActivity.this,FavouriteActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUserData(List<User> users){
        UserAdapter userAdapter = new UserAdapter(users);
        binding.rvUser.setAdapter(userAdapter);
        userAdapter.setOnItemClickCallback(data -> setUserDetail(data));

    }



    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void setUserDetail(User user){
        Intent intent = new Intent(MainActivity.this,DetailUserActivity.class);
        intent.putExtra(DetailUserActivity.EXTRA_USER,user);
        startActivity(intent);
    }

    private static SettingViewModel obtainViewModel(AppCompatActivity activity){
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity,factory).get(SettingViewModel.class);
    }

}