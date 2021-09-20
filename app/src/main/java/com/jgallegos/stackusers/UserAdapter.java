package com.jgallegos.stackusers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter {

    private List<UserViewModel> userModels = new ArrayList<>();

    public UserAdapter(final List<UserViewModel> userViewModels) {
        if(userViewModels != null) {
            this.userModels.addAll(userViewModels);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((UserViewHolder) holder).bindData(userModels.get(position));
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    @Override
    public int getItemViewType(final int position) { return R.layout.userlist_item; }

    public void addUser(UserViewModel user) {
        // Add new UserViewModel to our array
        this.userModels.add(user);

        // Tell our RecyclerView adapter there's new data
        this.notifyDataSetChanged();
    }

    public void addUser(String displayName, int bronzeBadgeCount, int silverBadgeCount, int goldBadgeCount, int reputation, String gravatarURL) {
        UserViewModel usr = new UserViewModel(displayName, bronzeBadgeCount, silverBadgeCount, goldBadgeCount, reputation, gravatarURL);
        this.addUser(usr);
    }
}
