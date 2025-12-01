package com.example.mediashelfmobile.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// 1. Annotate the class to be a Room database and declare the entities that belong in it.
@Database(entities = {User.class, MediaItem.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // 2. Define an abstract method that has zero arguments and returns an instance of the DAO class.
    public abstract AppDao appDao();

}
