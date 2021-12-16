package com.toniel.githubuser.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.toniel.githubuser.adapter.OtherUserAdapter;
import com.toniel.githubuser.config.ApiConfig;
import com.toniel.githubuser.databinding.FragmentFollowerBinding;
import com.toniel.githubuser.model.OtherUserResponseItem;
import com.toniel.githubuser.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowerFragment extends Fragment {

    public static final String EXTRA_USER = "extra_user";
    public static final String TAG = FollowerFragment.class.getSimpleName();
    private String username;
    OtherUserAdapter userAdapter;
    private FragmentFollowerBinding binding;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = getActivity().getIntent().getParcelableExtra(EXTRA_USER);
        username = user.getLogin();
        Log.d(TAG,username);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFollowerBinding.inflate(inflater,container,false);
        LinearLayoutManager layoutManager =  new LinearLayoutManager(getContext());
        binding.rvFollower.setLayoutManager(layoutManager);
        getFollowers();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    private void getFollowers(){
        showLoading(true);
        Call<List<OtherUserResponseItem>> client = ApiConfig.getApiService().getFollowers(username);
        client.enqueue(new Callback<List<OtherUserResponseItem>>() {
            @Override
            public void onResponse(Call<List<OtherUserResponseItem>> call, Response<List<OtherUserResponseItem>> response) {
                showLoading(false);
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        userAdapter = new OtherUserAdapter(response.body());
                        binding.rvFollower.setAdapter(userAdapter);
                    }else{
                        showMessage(username+" tidak memiliki follower");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OtherUserResponseItem>> call, Throwable t) {
                showLoading(false);
                showMessage(t.getMessage());
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

    private void showMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }




}