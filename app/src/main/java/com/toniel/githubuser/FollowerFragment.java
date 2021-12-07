package com.toniel.githubuser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.toniel.githubuser.adapter.OtherUserAdapter;
import com.toniel.githubuser.config.ApiConfig;
import com.toniel.githubuser.databinding.FragmentFollowerBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowerFragment extends Fragment {

    public static final String EXTRA_USERNAME = "extra_username";
    public static final String TAG = FollowerFragment.class.getSimpleName();
    private User user;
    OtherUserAdapter userAdapter;
    private FragmentFollowerBinding binding;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getActivity().getIntent().getParcelableExtra("extra_user");



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
        Call<List<OtherUserResponseItem>> client = ApiConfig.getApiService().getFollowers(user.getLogin());
        client.enqueue(new Callback<List<OtherUserResponseItem>>() {
            @Override
            public void onResponse(Call<List<OtherUserResponseItem>> call, Response<List<OtherUserResponseItem>> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        userAdapter = new OtherUserAdapter(response.body());
                        binding.rvFollower.setAdapter(userAdapter);
                    }else{
                    }
                }
            }

            @Override
            public void onFailure(Call<List<OtherUserResponseItem>> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });

    }




}