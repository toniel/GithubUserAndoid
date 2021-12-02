package com.toniel.githubuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import com.toniel.githubuser.adapter.UserAdapter;
import com.toniel.githubuser.config.ApiConfig;
import com.toniel.githubuser.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private ArrayList<User> listGithubUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvUser.setLayoutManager(layoutManager);
//        getSample();
//
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
//                    Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
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
        Call<UserResponse> client = ApiConfig.getApiService().getSample();
        client.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        setUserData(response.body().getItems());
                    }
                }else{
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: gagal");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSample(){
        Call<UserResponse> client = ApiConfig.getApiService().getSample();
        client.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() !=null){
//                        Toast.makeText(MainActivity.this, response.body().getItems().get(0).getLogin().toString(), Toast.LENGTH_SHORT).show();
                        setUserData(response.body().getItems());
                    }
                }else{
                    if (response.body() != null) {
                        Log.e(TAG, "onFailure: gagal");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUserData(List<User> users){
//        listGithubUser.addAll(users);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        binding.rvUser.setLayoutManager(layoutManager);
        UserAdapter userAdapter = new UserAdapter(users);
        binding.rvUser.setAdapter(userAdapter);

    }
}