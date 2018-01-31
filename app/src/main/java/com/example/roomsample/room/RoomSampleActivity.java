package com.example.roomsample.room;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.example.roomsample.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chiendh on 1/31/2018.
 */

public class RoomSampleActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.rvUser)
    RecyclerView rvUser;

    private UserAdapter adapter;
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_sample);
        ButterKnife.bind(this);

        initRecyclerView();

        getAllUsers();
    }

    private void initRecyclerView() {
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this, users, this);
        rvUser.setAdapter(adapter);
    }

    private void getAllUsers() {
        UserDatabase.getInstance(this).getUserDao().getAllUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    this.users.addAll(users);
                    adapter.notifyDataSetChanged();
                });
    }

    @OnClick({R.id.btnDelete, R.id.btnAdd, R.id.btnUpdate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnDelete:
                break;

            case R.id.btnAdd:
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                addUser(new User(name, email));
                break;

            case R.id.btnUpdate:
                break;
        }
    }

    private void addUser(User user) {
        Observable.fromCallable(() -> UserDatabase.getInstance(RoomSampleActivity.this).getUserDao().insertStudent(user)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(id -> {
                    onAddUserSuccess(user, id);
                });
    }

    private void onAddUserSuccess(User user, Long id) {
        resetUI();
        user.setId(id);
        addUserToBottom(user);
    }

    private void resetUI() {
        etName.setText("");
        etEmail.setText("");
    }

    private void addUserToBottom(User user) {
        users.add(user);
        adapter.notifyDataSetChanged();
        rvUser.scrollToPosition(users.size()-1);
    }

    @Override
    public void onClicked(int position) {
        User user = users.get(position);
        etName.setText(user.getName());
        etEmail.setText(user.getEmail() );
    }
}
