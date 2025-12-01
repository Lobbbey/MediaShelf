package com.example.mediashelfmobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mediashelfmobile.database.MediaItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class to bind MediaItem data to the RecyclerView on the Dashboard.
 * Implements OnItemLongClickListener to enable editing items via a long press (CRUD Update).
 */
public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {

    // 1. Define the Listener Interface for long-press events
    public interface OnItemLongClickListener {
        void onItemLongClick(MediaItem item);
    }

    private List<MediaItem> items = new ArrayList<>();
    private final OnItemLongClickListener listener; // Reference to the Dashboard activity

    /**
     * Constructor modified to accept the listener implementation from the hosting Activity.
     * @param listener The Dashboard activity instance, which implements OnItemLongClickListener.
     */
    public MediaAdapter(OnItemLongClickListener listener) {
        this.listener = listener;
    }

    /**
     * Updates the list of items displayed in the RecyclerView.
     */
    public void setItems(List<MediaItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for a single item (item_media.xml)
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_media, parent, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MediaViewHolder holder, int position) {
        final MediaItem item = items.get(position); // 'final' is important for use in the lambda

        // Set item data based on the item_media.xml layout
        holder.title.setText(item.title);
        holder.type.setText(item.type);
        holder.platform.setText(item.platform);
        holder.ratingBar.setRating(item.rating);

        // 2. Attach the Long Click Listener to the entire row (itemView)
        holder.itemView.setOnLongClickListener(v -> {
            // Pass the specific MediaItem object back to the Dashboard activity
            listener.onItemLongClick(item);
            return true; // IMPORTANT: Return true to consume the long click event
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Inner class to hold the views for one row (ViewHolder).
     */
    static class MediaViewHolder extends RecyclerView.ViewHolder {
        TextView title, type, platform;
        RatingBar ratingBar;

        public MediaViewHolder(View itemView) {
            super(itemView);
            // Link views to the IDs defined in item_media.xml
            title = itemView.findViewById(R.id.text_title);
            type = itemView.findViewById(R.id.text_type);
            platform = itemView.findViewById(R.id.text_platform);
            ratingBar = itemView.findViewById(R.id.rating_display);
        }
    }
}