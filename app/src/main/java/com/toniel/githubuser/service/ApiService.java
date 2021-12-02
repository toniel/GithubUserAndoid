package com.toniel.githubuser.service;

import com.toniel.githubuser.UserResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @GET("search/users")
    Call<UserResponse> getUsers(@Query("q") String username);
    @GET("search/users?q=toniel")
    Call<UserResponse> getSample();
}
