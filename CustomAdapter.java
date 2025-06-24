package com.example.weatherapp;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private final ArrayList<DataModel> mData;
    private final RecyclerViewInterface mListener;

    public CustomAdapter(ArrayList<DataModel> data, RecyclerViewInterface listener) {
        mData = data;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titleTextView.setText(mData.get(position).getTitle());
        holder.descriptionTextView.setText(mData.get(position).getDescription());

        holder.shareButton.setOnClickListener( v -> mListener.onItemClick(position) );
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        Button shareButton;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_textview);
            descriptionTextView = itemView.findViewById(R.id.description_textview);
            shareButton = itemView.findViewById(R.id.share_button);
        }
    }
}
