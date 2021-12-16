package com.toniel.githubuser.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

	@SerializedName("total_count")
	private int totalCount;

	@SerializedName("incomplete_results")
	private boolean incompleteResults;

	@SerializedName("items")
	private List<User> items;

	public int getTotalCount(){
		return totalCount;
	}

	public boolean isIncompleteResults(){
		return incompleteResults;
	}

	public List<User> getItems(){
		return items;
	}
}