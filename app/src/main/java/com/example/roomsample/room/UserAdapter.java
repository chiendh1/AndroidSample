package com.example.roomsample.room;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roomsample.R;

import java.util.List;

/**
 * Created by Chiendh on 1/31/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    private Context context;
    private List<User> users;
    private LayoutInflater inflater;

    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_user, parent, false);
        return new UserHolder(view);
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        holder.bindView(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
