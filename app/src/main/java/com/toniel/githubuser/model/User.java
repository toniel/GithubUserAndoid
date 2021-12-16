package com.toniel.githubuser.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity()
public class User implements Parcelable {

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	private int id;
	@ColumnInfo(name = "login")
	@SerializedName("login")
	private String login;
	@ColumnInfo(name = "avatar_url")
	@SerializedName("avatar_url")
	private String avatarUrl;
	@ColumnInfo(name = "company")
	@SerializedName("company")
	private String company;
	@ColumnInfo(name = "public_repos")
	@SerializedName("public_repos")
	private int publicRepos;
	@ColumnInfo(name = "followers")
	@SerializedName("followers")
	private int followers;
	@ColumnInfo(name = "following")
	@SerializedName("following")
	private int following;
	@ColumnInfo(name = "name")
	@SerializedName("name")
	private String name;
	@ColumnInfo(name = "location")
	@SerializedName("location")
	private String location;

	public void setCompany(String company) {
		this.company = company;
	}

	public void setPublicRepos(int publicRepos) {
		this.publicRepos = publicRepos;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public void setFollowing(int following) {
		this.following = following;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public User() {
	}

	protected User(Parcel in) {
		id = in.readInt();
		login = in.readString();
		avatarUrl = in.readString();
		company = in.readString();
		publicRepos = in.readInt();
		followers = in.readInt();
		following = in.readInt();
		name = in.readString();
		location = in.readString();
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public String getCompany() {
		return company;
	}

	public int getPublicRepos() {
		return publicRepos;
	}

	public int getFollowers() {
		return followers;
	}

	public int getFollowing() {
		return following;
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(login);
		dest.writeString(avatarUrl);
		dest.writeString(company);
		dest.writeInt(publicRepos);
		dest.writeInt(followers);
		dest.writeInt(following);
		dest.writeString(name);
		dest.writeString(location);
	}
}