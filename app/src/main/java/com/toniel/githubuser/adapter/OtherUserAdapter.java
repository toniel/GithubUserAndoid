package com.toniel.githubuser.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.toniel.githubuser.OtherUserResponseItem;
import com.toniel.githubuser.R;

import java.util.List;

public class OtherUserAdapter extends RecyclerView.Adapter<OtherUserAdapter.ViewHolder> {

    private List<OtherUserResponseItem> otherUsers;

    public OtherUserAdapter(List<OtherUserResponseItem> otherUsers) {
        this.otherUsers = otherUsers;
    }

    @NonNull
    @Override
    public OtherUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull OtherUserAdapter.ViewHolder holder, int position) {
        holder.tvUsername.setText(otherUsers.get(position).getLogin());
//        holder.
        Glide.with(holder.imgAvatar.getContext())
                .load(otherUsers.get(position).getAvatarUrl())
                .circleCrop()
                .into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return otherUsers.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvUsername;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_gravatar);
            tvUsername = itemView.findViewById(R.id.tv_username);
//            tvName = itemView.findViewById(R.id.tv_name);

        }
    }
}
