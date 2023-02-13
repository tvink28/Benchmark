package com.example.task2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private final List<RecyclerData> newsArrayList = new ArrayList<>();

    public RecyclerViewAdapter() {}

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
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

        private final TextView textViewAction;
        private final TextView textViewType;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAction = itemView.findViewById(R.id.item_text_action);
            textViewType = itemView.findViewById(R.id.item_text_type);
        }

        public void bind(RecyclerData recyclerData) {
//            textView.setText(recyclerData.text);
            textViewAction.setText(recyclerData.action);
            textViewType.setText(recyclerData.type);
        }
    }

    public void setItems(Collection<RecyclerData> recyclerData) {
        newsArrayList.addAll(recyclerData);
        notifyDataSetChanged();
    }

    public void clearItem(Collection<RecyclerData> recyclerData) {
        newsArrayList.clear();
        notifyDataSetChanged();
    }
}