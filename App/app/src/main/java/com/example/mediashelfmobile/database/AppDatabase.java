// This file contains generated code for debugging
package com.example.mediashelfmobile.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, MediaItem.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppDao appDao();

}
