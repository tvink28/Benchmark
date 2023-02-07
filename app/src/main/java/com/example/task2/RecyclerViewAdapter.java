package com.example.task2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private final ArrayList<RecyclerData> newsArrayList;
    private final LayoutInflater inflater;

    public RecyclerViewAdapter(ArrayList<RecyclerData> newsArrayList, Context context) {
//        this.context = context;
        this.newsArrayList = newsArrayList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(newsArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
        }

        public void bind(RecyclerData recyclerData) {
            textView.setText(recyclerData.text);
        }
    }

}