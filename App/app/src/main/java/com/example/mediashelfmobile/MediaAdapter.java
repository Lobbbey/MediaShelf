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

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MediaViewHolder> {

    public interface OnItemLongClickListener {
        void onItemLongClick(MediaItem item);
    }

    private List<MediaItem> items = new ArrayList<>();
    private final OnItemLongClickListener listener; // Reference to the Dashboard activity

    public MediaAdapter(OnItemLongClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<MediaItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_media, parent, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MediaViewHolder holder, int position) {
        final MediaItem item = items.get(position);

        holder.title.setText(item.title);
        holder.type.setText(item.type);
        holder.platform.setText(item.platform);
        holder.ratingBar.setRating(item.rating);

        holder.itemView.setOnLongClickListener(v -> {

            listener.onItemLongClick(item);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {
        TextView title, type, platform;
        RatingBar ratingBar;

        public MediaViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            type = itemView.findViewById(R.id.text_type);
            platform = itemView.findViewById(R.id.text_platform);
            ratingBar = itemView.findViewById(R.id.rating_display);
        }
    }
}