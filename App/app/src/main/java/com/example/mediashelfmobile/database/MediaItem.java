package com.example.mediashelfmobile.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "media_items",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "uid",
                childColumns = "user_creator_id",
                onDelete = ForeignKey.CASCADE
        ))
public class MediaItem {
    @PrimaryKey(autoGenerate = true)
    public int mediaId;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "creator")
    public String creator;

    @ColumnInfo(name = "genre")
    public String genre;

    @ColumnInfo(name = "platform")
    public String platform;

    @ColumnInfo(name = "rating")
    public int rating;

    @ColumnInfo(name = "status")
    public String status;

    @ColumnInfo(name = "notes")
    public String notes;

    @ColumnInfo(name = "user_creator_id")
    public int userCreatorId;

    public MediaItem(String type, String title, String creator, String platform, String genre, int rating, String status, String notes, int userCreatorId) {
        this.type = type;
        this.title = title;
        this.creator = creator;
        this.platform = platform;
        this.genre = genre;
        this.rating = rating;
        this.status = status;
        this.notes = notes;
        this.userCreatorId = userCreatorId;
    }

}