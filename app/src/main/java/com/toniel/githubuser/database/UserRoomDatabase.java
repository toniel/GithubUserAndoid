package com.toniel.githubuser.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.toniel.githubuser.model.User;

@Database(entities = {User.class},version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    private static volatile UserRoomDatabase INSTANCE;
    public static UserRoomDatabase getDatabase(final Context context){
        if (INSTANCE==null){
            synchronized (UserRoomDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),UserRoomDatabase.class,"github_db").build();
            }
        }
        return INSTANCE;
    }
}
