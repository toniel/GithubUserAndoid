package com.toniel.githubuser.activity;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.toniel.githubuser.R;
import com.toniel.githubuser.adapter.SectionsPagerAdapter;
import com.toniel.githubuser.config.ApiConfig;
import com.toniel.githubuser.databinding.ActivityDetailUserBinding;
import com.toniel.githubuser.model.User;
import com.toniel.githubuser.viewmodel.SettingViewModel;
import com.toniel.githubuser.viewmodel.UserDetailViewModel;
import com.toniel.githubuser.viewmodel.ViewModelFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserActivity extends AppCompatActivity {
    public static final String TAG = DetailUserActivity.class.getSimpleName();
    public static final String EXTRA_USER = "extra_user";
    private ActivityDetailUserBinding binding;
    private boolean favourite;

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_text_follower,
            R.string.tab_text_following
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        User user = getIntent().getParcelableExtra(EXTRA_USER);
        String username = user.getLogin();

        getDetailUser(username);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(sectionsPagerAdapter);

        UserDetailViewModel userDetailViewModel = obtainViewModel(DetailUserActivity.this);
        userDetailViewModel.currentUser(username).observe(this,user1->{
           if (user1 != null){
               if(user1.getLogin().equals(user.getLogin())){
                   favourite=true;
                   binding.btnFavourite.setBackgroundResource(R.drawable.ic_baseline_favorite_48);
               }else{
                   favourite=false;
                   binding.btnFavourite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_48);
               }
           }
        });

        binding.btnFavourite.setOnClickListener(v -> {
            favourite = !favourite;
            if (favourite){
                userDetailViewModel.insert(user);
                binding.btnFavourite.setBackgroundResource(R.drawable.ic_baseline_favorite_48);
                Snackbar.make(DetailUserActivity.this,binding.getRoot(),username +" is added to favourite",Snackbar.LENGTH_SHORT)
                        .setAction("Go to Favourite",v1 -> {
                            Intent intent = new Intent(DetailUserActivity.this,FavouriteActivity.class);
                            startActivity(intent);
                        })
                        .show();
            }else{
                userDetailViewModel.delete(user);
                binding.btnFavourite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_48);
                Snackbar.make(DetailUserActivity.this,binding.getRoot(),username +" is removed from favourite",Snackbar.LENGTH_SHORT)
                        .setAction("Go to Favourite",v1 -> {
                            Intent intent = new Intent(DetailUserActivity.this,FavouriteActivity.class);
                            startActivity(intent);
                        })
                        .show();
            }
        });


        TabLayout tabs = findViewById(R.id.tabs);
        new TabLayoutMediator(tabs,viewPager2,
                (tab, position) -> tab.setText(getResources().getString(TAB_TITLES[position]))
        ).attach();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setTitle(username);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

    private void setUser(User user){
        binding.tvUsername.setText(user.getLogin());
        binding.tvName.setText(user.getName());
        binding.tvCompany.setText(user.getCompany());
        binding.tvLocation.setText(user.getLocation());
        binding.tvFollower.setText(String.valueOf(user.getFollowers()));
        binding.tvFollowing.setText(String.valueOf(user.getFollowing()));
        binding.tvRepository.setText(String.valueOf(user.getPublicRepos()));
        Glide.with(this)
                .load(user.getAvatarUrl())
                .circleCrop()
                .into(binding.imageAvatar);
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
                        setUser(response.body());
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

    private static UserDetailViewModel obtainViewModel(AppCompatActivity activity){
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity,factory).get(UserDetailViewModel.class);
    }



}