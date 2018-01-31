package com.example.roomsample.room;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.roomsample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chiendh on 1/31/2018.
 */

public class UserHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvEmail)
    TextView tvEmail;

    public UserHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(User user) {
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
    }
}
