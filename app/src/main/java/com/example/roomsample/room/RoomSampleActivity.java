package com.example.roomsample.room;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.roomsample.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Chiendh on 1/31/2018.
 */

public class RoomSampleActivity extends AppCompatActivity {

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
        adapter = new UserAdapter(this, users);
        rvUser.setAdapter(adapter);
    }

    private void getAllUsers() {
        UserDatabase.getInstance(this).getUserDao().getUsers()
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
        Completable.fromAction(() -> UserDatabase.getInstance(RoomSampleActivity.this).getUserDao().insertStudent(user))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d("CHIEN", "onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("CHIEN", "onError");
            }
        });
    }
}
