package com.example.roomsample.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by Chiendh on 1/31/2018.
 */
@Dao
public interface UserDao {

    /**
     * When there is no user in the database and the query returns no rows, the Flowable will not emit, neither onNext, nor onError.
     When there is a user in the database, the Flowable will trigger onNext.
     Every time the user data is updated, the Flowable object will emit automatically, allowing you to update the UI based on the latest data.
     * @return
     */
    //Flowable or observable is used to emits Student model types of data and it emits whenever database is updated
    @Query("SELECT * FROM users")
    Flowable<List<User>> getUsers();

    /**
     * This Maybe class is used to emit only a single row data
     * When there is no user in the database and the query returns no rows, Maybe will complete.
     When there is a user in the database, Maybe will trigger onSuccess and it will complete.
     If the user is updated after Maybe was completed, nothing happens.
     */
    @Query("SELECT * FROM users WHERE id =:id")
    Maybe<User> getUserById(int id);


    @Insert
    void insertStudent(User user);

    @Query("DELETE FROM users")
    void deleteAllUsers();
}
