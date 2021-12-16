package com.toniel.githubuser.model;

import com.google.gson.annotations.SerializedName;

public class OtherUserResponseItem {


	@SerializedName("repos_url")
	private String reposUrl;

	@SerializedName("login")
	private String login;
	@SerializedName("avatar_url")
	private String avatarUrl;

	public String getReposUrl() {
		return reposUrl;
	}

	public String getLogin() {
		return login;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}
}