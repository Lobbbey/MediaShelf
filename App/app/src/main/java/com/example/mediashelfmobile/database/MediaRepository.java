package com.example.mediashelfmobile.database;

import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MediaRepository {

    private AppDao mAppDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public MediaRepository(Context context) {
        // Initializes the Room Database Builder
        AppDatabase database = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "media_shelf_database")
                .allowMainThreadQueries()
                .build();

        mAppDao = database.appDao();
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

    // --- Media CRUD & Retrieval Methods ---

    public void addMediaItem(MediaItem item){
        executorService.execute(() -> mAppDao.insertMediaItem(item));
    }

    public void updateMediaItem(MediaItem item){
        executorService.execute(() -> mAppDao.updateMediaItem(item));
    }

    public void deleteMediaItem(MediaItem item){
        executorService.execute(() -> mAppDao.deleteMediaItem(item));
    }

    /**
     * Gets a single MediaItem by its primary key (mediaId). Used for pre-filling the Edit screen.
     */
    public MediaItem getMediaItemById(int mediaId) {
        return mAppDao.getMediaItemById(mediaId);
    }

    /**
     * Filters results by a specific media type AND search term.
     */
    public List<MediaItem> getMediaByTypeAndSearch(int userId, String type, String searchTerm){
        // Adds SQL wildcards (%) to search anywhere in the title
        String formattedSearch = "%" + searchTerm  + "%";
        return mAppDao.getMediaItemsByTypeAndSearch(userId, type, formattedSearch);
    }
}