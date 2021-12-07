package com.toniel.githubuser;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OtherUserResponse{

	@SerializedName("OtherUserResponse")
	private List<OtherUserResponseItem> otherUserResponse;

	public List<OtherUserResponseItem> getOtherUserResponse(){
		return otherUserResponse;
	}
}