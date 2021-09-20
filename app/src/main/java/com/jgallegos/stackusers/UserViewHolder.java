package com.jgallegos.stackusers;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivGravatar;
    private TextView tvUsername;
    private TextView tvReputation;
    private TextView tvBronzeBadges;
    private TextView tvSilverBadges;
    private TextView tvGoldBadges;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);

        ivGravatar = (ImageView) itemView.findViewById(R.id.ivGravatar);
        tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
        tvReputation = (TextView) itemView.findViewById(R.id.tvReputation);
        tvBronzeBadges = (TextView) itemView.findViewById(R.id.tvBronzeBadges);
        tvSilverBadges = (TextView) itemView.findViewById(R.id.tvSilverBadges);
        tvGoldBadges = (TextView) itemView.findViewById(R.id.tvGoldBadges);
    }

    public void bindData(final UserViewModel viewModel) {
        if(viewModel.getGravatarURL() != "")
            // Use glide libary to retrieve gravatarURL image or load from cache if already downloaded
            Glide.with(ivGravatar.getContext())
                    .load(viewModel.getGravatarURL())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivGravatar);
        else
            // No image to show, just show a gray box
            ivGravatar.setBackgroundColor(Color.DKGRAY);

        tvUsername.setText(viewModel.getUsername());
        tvReputation.setText(String.format("%,d", viewModel.getReputation())); // Format to add necessary commas
        tvBronzeBadges.setText(String.valueOf(viewModel.getBronzeBadges()));
        tvSilverBadges.setText(String.valueOf(viewModel.getSilverBadges()));
        tvGoldBadges.setText(String.valueOf(viewModel.getGoldBadges()));

    }
}
