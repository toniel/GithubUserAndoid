package com.toniel.githubuser.service;

import com.toniel.githubuser.OtherUserResponse;
import com.toniel.githubuser.OtherUserResponseItem;
import com.toniel.githubuser.User;
import com.toniel.githubuser.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @GET("search/users")
    Call<UserResponse> getUsers(@Query("q") String username);
    @GET("users/{username}")
    Call<User> getUserDetail(@Path("username") String username);
    @GET("users/{username}/followers")
    Call<List<OtherUserResponseItem>> getFollowers(@Path("username") String username);
    @GET("users/{username}/following")
    Call<List<OtherUserResponseItem>> getFollowing(@Path("username") String username);
}
