package com.toniel.githubuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import com.toniel.githubuser.adapter.UserAdapter;
import com.toniel.githubuser.config.ApiConfig;
import com.toniel.githubuser.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvUser.setLayoutManager(layoutManager);
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

                    getUsers(s);
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

    private void getUsers(String username){
        showLoading(true);
        Call<UserResponse> client = ApiConfig.getApiService().getUsers(username);
        client.enqueue(new Callback<UserResponse>() {
            @Override @NonNull
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                showLoading(false);
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        setUserData(response.body().getItems());
                    }
                }else{
                    showLoading(false);
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: gagal");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                showLoading(false);
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void setUserData(List<User> users){
        UserAdapter userAdapter = new UserAdapter(users);
        binding.rvUser.setAdapter(userAdapter);
        userAdapter.setOnItemClickCallback(data -> getDetailUser(data.getLogin()));

    }

    private void getDetailUser(String username) {
        showLoading(true);
        Call<User> client = ApiConfig.getApiService().getUserDetail(username);
        client.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                showLoading(false);
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        setUserDetail(response.body());
                    }
                }else{
                    showLoading(false);
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: gagal");
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showLoading(false);
                Log.e(TAG, t.getMessage());
            }
        });
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

}