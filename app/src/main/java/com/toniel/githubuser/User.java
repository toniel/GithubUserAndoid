package com.toniel.githubuser;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable{

	@SerializedName("login")
	private String login;
	@SerializedName("avatar_url")
	private String avatarUrl;
	@SerializedName("html_url")
	private String htmlUrl;
	@SerializedName("id")
	private int id;
	@SerializedName("gravatar_id")
	private String gravatarId;
	@SerializedName("node_id")
	private String nodeId;
	@SerializedName("organizations_url")
	private String organizationsUrl;
	@SerializedName("twitter_username")
	private String twitterUsername;
	@SerializedName("bio")
	private String bio;
	@SerializedName("blog")
	private String blog;
	@SerializedName("company")
	private String company;
	@SerializedName("public_repos")
	private int publicRepos;

	@SerializedName("followers")
	private int followers;

	@SerializedName("following")
	private int following;

	@SerializedName("name")
	private String name;

	@SerializedName("location")
	private String location;

	protected User(Parcel in) {
		login = in.readString();
		avatarUrl = in.readString();
		htmlUrl = in.readString();
		id = in.readInt();
		gravatarId = in.readString();
		nodeId = in.readString();
		organizationsUrl = in.readString();
		twitterUsername = in.readString();
		bio = in.readString();
		blog = in.readString();
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

	public String getLogin() {
		return login;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public int getId() {
		return id;
	}

	public String getGravatarId() {
		return gravatarId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public String getOrganizationsUrl() {
		return organizationsUrl;
	}

	public String getTwitterUsername() {
		return twitterUsername;
	}

	public String getBio() {
		return bio;
	}

	public String getBlog() {
		return blog;
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
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(login);
		parcel.writeString(avatarUrl);
		parcel.writeString(htmlUrl);
		parcel.writeInt(id);
		parcel.writeString(gravatarId);
		parcel.writeString(nodeId);
		parcel.writeString(organizationsUrl);
		parcel.writeString(twitterUsername);
		parcel.writeString(bio);
		parcel.writeString(blog);
		parcel.writeString(company);
		parcel.writeInt(publicRepos);
		parcel.writeInt(followers);
		parcel.writeInt(following);
		parcel.writeString(name);
		parcel.writeString(location);
	}
}