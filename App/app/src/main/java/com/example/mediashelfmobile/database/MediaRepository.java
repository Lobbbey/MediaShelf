// This file contains generated code to help with errors
package com.example.mediashelfmobile.database;

import android.content.Context;

import android.content.Context;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MediaRepository {

    private AppDao mAppDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    // Constructor
    public MediaRepository(Context context) {
        AppDatabase database = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "media_shelf_database")
                .allowMainThreadQueries()
                .build();
        mAppDao = database.AppDao();
    }

    // User API methods
    public void RegisterUser(String username, String password){
        executorService.execute(()->{
            User newUser = new User(username, password);
            mAppDao.insertUser(newUser);
        });
    }

    public User loginUser(String username, String password){
        return mAppDao.login(username, password);
    }

    public boolean checkUserExists(String username){
        return mAppDao.getUserByName(username) != null;
    }

    // Media API methods
    public void addMediaItem(MediaItem item){
        executorService.execute(() -> mAppDao.insertMediaItem(item));
    }

    public void updateMediaItem(MediaItem item){
        executorService.execute(() -> mAppDao.updateMediaItem(item));
    }

    public void deleteMediaItem(MediaItem item){
        executorService.execute(() -> mAppDao.deleteMediaItem(item));
    }

    public  List<MediaItem> getMediaByType(int userId, String type){
        return mAppDao.getAllMediaItemsByType(userId, type);
    }

    public List<MediaItem> getMediaBySearch(int userId, String searchTerm){
        String formattedSearch = "%" + searchTerm  + "%";
        return mAppDao.getMediaItemsBySearch(userId, formattedSearch);
    }

}
