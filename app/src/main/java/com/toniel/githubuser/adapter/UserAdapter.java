package com.toniel.githubuser.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.toniel.githubuser.DetailUserActivity;
import com.toniel.githubuser.MainActivity;
import com.toniel.githubuser.R;
import com.toniel.githubuser.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> users;
    public OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.tvUsername.setText(users.get(position).getLogin());
        Glide.with(holder.itemView.getContext())
                .load(users.get(position).getAvatarUrl())
                .circleCrop()
                .into(holder.imgGravatar);
        holder.itemView.setOnClickListener(view -> {
           onItemClickCallback.onItemClicked(users.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGravatar;
        TextView tvUsername;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGravatar = itemView.findViewById(R.id.img_gravatar);
            tvUsername = itemView.findViewById(R.id.tv_username);

        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(User data);
    }
}
