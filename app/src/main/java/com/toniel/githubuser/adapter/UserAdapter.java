package com.toniel.githubuser.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.toniel.githubuser.R;
import com.toniel.githubuser.databinding.ActivityDetailUserBinding;
import com.toniel.githubuser.databinding.UserItemBinding;
import com.toniel.githubuser.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final List<User> users;
    public OnItemClickCallback onItemClickCallback;
    private ActivityDetailUserBinding binding;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserItemBinding binding = UserItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        holder.binding.tvUsername.setText(users.get(position).getLogin());
        Glide.with(holder.itemView.getContext())
                .load(users.get(position).getAvatarUrl())
                .circleCrop()
                .into(holder.binding.imgGravatar);
        holder.itemView.setOnClickListener(view -> {
           onItemClickCallback.onItemClicked(users.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        UserItemBinding binding;

        public ViewHolder(@NonNull UserItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(User data);
    }
}
