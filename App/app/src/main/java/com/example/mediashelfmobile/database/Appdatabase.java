package com.example.mediashelfmobile.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mediashelfmobile.database.User;
import com.example.mediashelfmobile.database.MediaItem;
import com.example.mediashelfmobile.database.AppDao;

@Database(entities = {User.class, MediaItem.class}, version = 1)
public abstract class Appdatabase extends RoomDatabase {
    public abstract AppDao appDao();
}