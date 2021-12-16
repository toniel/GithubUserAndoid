package com.toniel.githubuser.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.toniel.githubuser.databinding.UserItemBinding;
import com.toniel.githubuser.helper.UserDiffCallback;
import com.toniel.githubuser.model.User;

import java.util.ArrayList;
import java.util.List;

public class FavouriteUserAdapter extends RecyclerView.Adapter<FavouriteUserAdapter.ViewHolder> {
    private final ArrayList<User> favouriteUsers = new ArrayList<>();
    public UserAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(UserAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

//    public FavouriteUserAdapter(List<User> favouriteUsers) {
//
//        this.favouriteUsers = favouriteUsers;
//
//    }

    public void setFavouriteUsers(List<User> favouriteUsers){
        final UserDiffCallback diffCallback = new UserDiffCallback(this.favouriteUsers,favouriteUsers);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.favouriteUsers.clear();
        this.favouriteUsers.addAll(favouriteUsers);
        diffResult.dispatchUpdatesTo(this);
    }

//



    @NonNull
    @Override
    public FavouriteUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserItemBinding binding = UserItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteUserAdapter.ViewHolder holder, int position) {
        holder.binding.tvUsername.setText(favouriteUsers.get(position).getLogin());
        Glide.with(holder.itemView.getContext())
                .load(favouriteUsers.get(position).getAvatarUrl())
                .circleCrop()
                .into(holder.binding.imgGravatar);
        holder.itemView.setOnClickListener(view -> {
            onItemClickCallback.onItemClicked(favouriteUsers.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return favouriteUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final UserItemBinding binding;
        public ViewHolder(UserItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }

    public interface OnItemClickCallback{
        void onItemClicked(User data);
    }
}
