// This file contains generated code for debugging
package com.example.mediashelfmobile.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDao {
    // --- User Operations ---
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    User getUserByName(String username);

    // --- Media Operations ---
    @Insert
    void insertMediaItem(MediaItem item);

    @Update
    void updateMediaItem(MediaItem item);

    @Delete
    void deleteMediaItem(MediaItem item);

    @Query("SELECT * FROM media_items WHERE mediaId = :mediaId LIMIT 1")
    MediaItem getMediaItemById(int mediaId);

    // Get all items by type
    @Query("SELECT * FROM media_items WHERE user_creator_id = :userId AND type = :mediaType")
    List<MediaItem> getAllMediaItemsByType(int userId, String mediaType);

    @Query("SELECT * FROM media_items WHERE user_creator_id = :userId AND type = :mediaType AND title LIKE :searchQuery")
    List<MediaItem> getMediaItemsByTypeAndSearch(int userId, String mediaType, String searchQuery);
}