package com.toniel.githubuser.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.toniel.githubuser.adapter.FavouriteUserAdapter;
import com.toniel.githubuser.databinding.ActivityFavouriteBinding;
import com.toniel.githubuser.viewmodel.FavouriteUserViewModel;
import com.toniel.githubuser.viewmodel.ViewModelFactory;

public class FavouriteActivity extends AppCompatActivity {
    private ActivityFavouriteBinding binding;
    private FavouriteUserAdapter favouriteUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavouriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar()!=null){
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Favourite Users");

        }

        showFavouriteUsers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showFavouriteUsers();

    }

    private static FavouriteUserViewModel obtainViewModel(AppCompatActivity activity){
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity,factory).get(FavouriteUserViewModel.class);
    }



   private void showFavouriteUsers(){
       FavouriteUserViewModel favouriteUserViewModel = obtainViewModel(FavouriteActivity.this);
       favouriteUserViewModel.getAllFavouriteUsers().observe(this,users -> {
           if (users.isEmpty()){
               Snackbar.make(FavouriteActivity.this,binding.getRoot(),"There is no favourite users",Snackbar.LENGTH_SHORT).show();
           }else{

               favouriteUserAdapter.setFavouriteUsers(users);
           }
       });

       favouriteUserAdapter = new FavouriteUserAdapter();

       LinearLayoutManager layoutManager = new LinearLayoutManager(this);
       binding.rvUser.setLayoutManager(layoutManager);
       binding.rvUser.setHasFixedSize(true);
       binding.rvUser.setAdapter(favouriteUserAdapter);

       favouriteUserAdapter.setOnItemClickCallback(user ->{
           Intent intent = new Intent(FavouriteActivity.this,DetailUserActivity.class);
           intent.putExtra(DetailUserActivity.EXTRA_USER,user);
           startActivity(intent);
       });
   }
}