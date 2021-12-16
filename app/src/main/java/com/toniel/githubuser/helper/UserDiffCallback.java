package com.toniel.githubuser.helper;

import androidx.recyclerview.widget.DiffUtil;

import com.toniel.githubuser.model.User;

import java.util.List;

public class UserDiffCallback extends DiffUtil.Callback {
    private final List<User> oldUser;
    private final List<User> newUser;

    public UserDiffCallback(List<User> oldUser, List<User> newUser) {
        this.oldUser = oldUser;
        this.newUser = newUser;
    }

    @Override
    public int getOldListSize() {
        return oldUser.size();
    }

    @Override
    public int getNewListSize() {
        return newUser.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldUser.get(oldItemPosition).getLogin()==newUser.get(newItemPosition).getLogin();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final User oldUser1 = oldUser.get(oldItemPosition);
        final User newUser1 = newUser.get(newItemPosition);
        return oldUser1.getLogin().equals(newUser1.getLogin());
    }
}
